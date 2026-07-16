package com.example

import kotlin.math.cos
import kotlin.math.sin
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.data.AppDatabase
import com.example.data.DzikirHistory
import java.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip
import android.media.AudioManager
import android.media.ToneGenerator
import android.view.WindowManager

val IslamicArchShape = object : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val w = size.width
            val h = size.height
            moveTo(w / 2, 0f)
            quadraticBezierTo(w * 0.45f, h * 0.1f, w * 0.3f, h * 0.15f)
            quadraticBezierTo(0f, h * 0.2f, 0f, h * 0.35f)
            lineTo(0f, h)
            lineTo(w, h)
            lineTo(w, h * 0.35f)
            quadraticBezierTo(w, h * 0.2f, w * 0.7f, h * 0.15f)
            quadraticBezierTo(w * 0.55f, h * 0.1f, w / 2, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun DzikirCounterScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("dzikir_prefs", Context.MODE_PRIVATE)
    
    val todayStart = getStartOfDayTimestamp()
    val lastSavedDate = sharedPreferences.getLong("last_saved_date", todayStart)
    
    val isNewDay = lastSavedDate != todayStart
    val initialTotalDzikir = if (isNewDay) 0 else sharedPreferences.getInt("total_dzikir", 0)
    val initialCurrentCycle = if (isNewDay) 0 else sharedPreferences.getInt("current_cycle", 0)

    // State
    var totalDzikir by remember { mutableStateOf(initialTotalDzikir) }
    var currentCycle by remember { mutableStateOf(initialCurrentCycle) }
    var targetCycle by remember { mutableStateOf(sharedPreferences.getInt("target_cycle", 33)) }
    var selectedReadingId by remember { mutableStateOf(sharedPreferences.getString("selected_reading", "subhanallah") ?: "subhanallah") }

    // If it's a new day, immediately save the new state so we don't accidentally load old data later
    if (isNewDay) {
        LaunchedEffect(Unit) {
            sharedPreferences.edit()
                .putInt("total_dzikir", 0)
                .putInt("current_cycle", 0)
                .putLong("last_saved_date", todayStart)
                .apply()
        }
    }

    // Resolve the current reading from the list defined in SettingsScreen
    val currentReading = dzikirReadings.find { it.id == selectedReadingId } ?: dzikirReadings[0]

    val scope = rememberCoroutineScope()
    val dao = remember { AppDatabase.getDatabase(context).historyDao() }

    // Auto-save function
    fun saveState(isTap: Boolean = false, isFullCycle: Boolean = false) {
        val today = getStartOfDayTimestamp()
        sharedPreferences.edit()
            .putInt("total_dzikir", totalDzikir)
            .putInt("current_cycle", currentCycle)
            .putLong("last_saved_date", today)
            .apply()
            
        if (isTap) {
            val reading = selectedReadingId
            scope.launch(Dispatchers.IO) {
                var history = dao.getHistoryByDateAndReading(today, reading)
                if (history != null) {
                    history = history.copy(
                        totalCount = history.totalCount + 1,
                        cyclesCompleted = if (isFullCycle) history.cyclesCompleted + 1 else history.cyclesCompleted
                    )
                    dao.updateHistory(history)
                } else {
                    dao.insertHistory(
                        DzikirHistory(
                            dateTimestamp = today,
                            readingId = reading,
                            totalCount = 1,
                            cyclesCompleted = if (isFullCycle) 1 else 0
                        )
                    )
                }
            }
        }
    }

    // Progress Animation
    val progressAnim = remember { androidx.compose.animation.core.Animatable(if (currentCycle == 0) 0f else currentCycle.toFloat() / targetCycle.toFloat()) }
    LaunchedEffect(currentCycle) {
        val target = if (currentCycle == 0) 0f else currentCycle.toFloat() / targetCycle.toFloat()
        if (currentCycle == 1 && progressAnim.value > 0.5f) {
            progressAnim.snapTo(0f)
            progressAnim.animateTo(target, tween(durationMillis = 300))
        } else if (currentCycle == 0) {
            progressAnim.snapTo(0f)
        } else {
            progressAnim.animateTo(target, tween(durationMillis = 300))
        }
    }

    // Light Theme Palette based on uploaded image
    val colorBackground = Color(0xFFFBF4E6) // Light cream
    val colorArch = Color(0xFFFDF8EE) // Slightly lighter for arch
    val colorText = Color(0xFF3B5234) // Dark green
    val colorTextLight = Color(0xFF8B8A6A) // Light text
    val colorPrimary = Color(0xFFF2A33A) // Orange/Gold
    val colorCircleTrack = Color(0xFFEAE3D4) // Track
    val colorSurface = Color(0xFFF0EAE0) // Buttons & pills
    val colorPattern = Color(0xFFE8DFC8)

    val interactionSource = remember { MutableInteractionSource() }
    
    // Experience settings
    var vibrationEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("vibration_enabled", true)) }
    var vibrationMaxEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("vibration_max_enabled", true)) }
    var soundEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("sound_enabled", false)) }
    var keepScreenOn by remember { mutableStateOf(sharedPreferences.getBoolean("keep_screen_on", true)) }
    var selectedLanguage by remember { mutableStateOf(sharedPreferences.getString("selected_language", "id") ?: "id") }
    
    val strings = Translations.getStrings(selectedLanguage)

    // Keep screen on
    val activity = context as? android.app.Activity
    LaunchedEffect(keepScreenOn) {
        if (keepScreenOn) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    // Sound helper
    val toneGenerator = remember {
        try { ToneGenerator(AudioManager.STREAM_MUSIC, 60) } catch (e: Exception) { null }
    }

    // Dialog State
    var showResetDialog by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var showHistory by remember { mutableStateOf(false) }

    // Refresh settings when returning from SettingsScreen
    if (!showSettings) {
        LaunchedEffect(showSettings) {
            targetCycle = sharedPreferences.getInt("target_cycle", 33)
            selectedReadingId = sharedPreferences.getString("selected_reading", "subhanallah") ?: "subhanallah"
            vibrationEnabled = sharedPreferences.getBoolean("vibration_enabled", true)
            vibrationMaxEnabled = sharedPreferences.getBoolean("vibration_max_enabled", true)
            soundEnabled = sharedPreferences.getBoolean("sound_enabled", false)
            keepScreenOn = sharedPreferences.getBoolean("keep_screen_on", true)
            selectedLanguage = sharedPreferences.getString("selected_language", "id") ?: "id"
            // If currentCycle exceeds new target, reset it
            if (currentCycle > targetCycle) {
                currentCycle = 0
                saveState(isTap = false, isFullCycle = false)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null 
            ) {
                val todayStart = getStartOfDayTimestamp()
                val lastSavedDate = sharedPreferences.getLong("last_saved_date", todayStart)
                if (lastSavedDate != 0L && lastSavedDate != todayStart) {
                    // Midnight crossed while app was open
                    totalDzikir = 0
                    currentCycle = 0
                }

                totalDzikir++
                if (currentCycle < targetCycle) {
                    currentCycle++
                    if (vibrationEnabled) vibrate(context, 50)
                    if (soundEnabled) toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 50)
                    saveState(isTap = true, isFullCycle = false)
                } else {
                    currentCycle = 1
                    if (vibrationMaxEnabled) vibrate(context, 300) else if (vibrationEnabled) vibrate(context, 50)
                    if (soundEnabled) toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 150)
                    saveState(isTap = true, isFullCycle = true)
                }
            }
    ) {
        // Main Image Background
        Image(
            painter = painterResource(id = com.silunjuk.dzikirterus.R.drawable.main_bg),
            contentDescription = "Main Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            
            // HEADER 
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 110.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentReading.nameArab,
                    fontSize = if (currentReading.nameArab.length > 20) 36.sp else 56.sp,
                    color = colorText,
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                // Divider with small star
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 12.dp)
                        .width(180.dp)
                ) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(colorTextLight.copy(alpha = 0.3f)))
                    
                    Canvas(modifier = Modifier.padding(horizontal = 8.dp).size(8.dp)) {
                        val path = Path().apply {
                            moveTo(size.width / 2, 0f)
                            lineTo(size.width, size.height / 2)
                            lineTo(size.width / 2, size.height)
                            lineTo(0f, size.height / 2)
                            close()
                        }
                        drawPath(path, color = colorPrimary.copy(alpha = 0.8f), style = Stroke(0.8.dp.toPx()))
                    }

                    Box(modifier = Modifier.weight(1f).height(1.dp).background(colorTextLight.copy(alpha = 0.3f)))
                }

                Text(
                    text = currentReading.nameIndonesia.uppercase(),
                    fontSize = 12.sp,
                    color = colorTextLight,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 4.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // MAIN AREA
            Column(
                modifier = Modifier.weight(1f).padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // CENTER CIRCLE
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(340.dp)
                        .padding(16.dp)
                ) {
                    // Background Track & Progress
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val strokeW = 4.dp.toPx()
                        // Draw background track (Dark olive green, slightly broken at top)
                        drawArc(
                            color = Color(0xFF809481),
                            startAngle = -85f,
                            sweepAngle = 350f,
                            useCenter = false,
                            style = Stroke(width = strokeW, cap = StrokeCap.Round)
                        )

                        // Draw animated progress (Golden orange)
                        val currentSweep = progressAnim.value * 350f
                        drawArc(
                            color = Color(0xFFFABF3A),
                            startAngle = -85f, 
                            sweepAngle = currentSweep, 
                            useCenter = false,
                            style = Stroke(width = strokeW, cap = StrokeCap.Round)
                        )

                        // Draw the golden knob at the end of progress
                        if (currentSweep > 0f) {
                            val radius = size.width / 2
                            val angleRad = Math.toRadians((-85f + currentSweep).toDouble())
                            val knobX = center.x + (radius * cos(angleRad)).toFloat()
                            val knobY = center.y + (radius * sin(angleRad)).toFloat()
                            
                            // A slightly darker base for the knob to give 3D feel
                            drawCircle(
                                color = Color(0xFFC08B2D),
                                radius = 7.dp.toPx(),
                                center = Offset(knobX, knobY + 1.dp.toPx())
                            )
                            // The bright gold knob
                            drawCircle(
                                color = Color(0xFFFFD46F),
                                radius = 6.dp.toPx(),
                                center = Offset(knobX, knobY)
                            )
                        }
                    }

                    // INNER TEXT CIRCLE
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp) // Margin from the track
                            .shadow(8.dp, CircleShape)
                            .background(Color(0xFF1B3D2B), CircleShape) // Dark forest green
                            .border(3.dp, Color(0xFFE8C26E), CircleShape), // Golden border
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = strings.totalDzikir.uppercase().replace(":", ""),
                                fontSize = 14.sp,
                                color = Color(0xFFD1E0A3),
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 3.sp
                            )
                            
                            Text(
                                text = "$totalDzikir",
                                fontSize = 90.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFCE182),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            
                            // Golden Divider with diamond
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Box(modifier = Modifier.width(40.dp).height(1.dp).background(Color(0xFFE8C26E).copy(alpha=0.6f)))
                                Canvas(modifier = Modifier.padding(horizontal = 8.dp).size(8.dp)) {
                                    val path = Path().apply {
                                        moveTo(size.width / 2, 0f)
                                        lineTo(size.width, size.height / 2)
                                        lineTo(size.width / 2, size.height)
                                        lineTo(0f, size.height / 2)
                                        close()
                                    }
                                    drawPath(path, color = Color(0xFFE8C26E), style = Stroke(1.5.dp.toPx()))
                                }
                                Box(modifier = Modifier.width(40.dp).height(1.dp).background(Color(0xFFE8C26E).copy(alpha=0.6f)))
                            }
                            
                            // Cycle indicator badge
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color(0xFFD1E0A3), RoundedCornerShape(50))
                                    .padding(horizontal = 24.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "$currentCycle / $targetCycle",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFD1E0A3)
                                )
                            }
                        }
                    }
                }

                // Ketuk indicator
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = strings.tapToCount,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1B3D2B),
                        letterSpacing = 2.sp
                    )
                }
            }

            // BOTTOM MENU
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .padding(start = 40.dp, end = 40.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset Button
                IconButton(
                    onClick = { showResetDialog = true },
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(4.dp, CircleShape)
                        .background(Color(0xFFF3E7D3), CircleShape)
                        .border(1.5.dp, Color(0xFFE8C26E), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = Color(0xFF1B3D2B),
                        modifier = Modifier.size(28.dp)
                    )
                }

                // History Button
                IconButton(
                    onClick = { showHistory = true },
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(4.dp, CircleShape)
                        .background(Color(0xFFF3E7D3), CircleShape)
                        .border(1.5.dp, Color(0xFFE8C26E), CircleShape)
                ) {
                    Text(
                        text = "History",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B3D2B)
                    )
                }

                // Settings Button
                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(4.dp, CircleShape)
                        .background(Color(0xFFF3E7D3), CircleShape)
                        .border(1.5.dp, Color(0xFFE8C26E), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF1B3D2B),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        
        // Reset Confirmation Dialog
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text(text = strings.resetTitle) },
                text = { Text(text = strings.resetMessage) },
                containerColor = colorArch,
                titleContentColor = colorText,
                textContentColor = colorText,
                confirmButton = {
                    TextButton(onClick = { 
                        totalDzikir = 0
                        currentCycle = 0
                        saveState(isTap = false, isFullCycle = false)
                        showResetDialog = false
                    }) {
                        Text(strings.yesReset, color = colorText, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text(strings.cancel, color = colorTextLight)
                    }
                }
            )
        }
    }

    // Settings Screen Overlay
    if (showSettings) {
        BackHandler { showSettings = false }
        SettingsScreen(
            onBack = { showSettings = false },
            modifier = Modifier.fillMaxSize()
        )
    }

    // History Screen Overlay
    if (showHistory) {
        BackHandler { showHistory = false }
        HistoryScreen(
            strings = strings,
            onBack = { showHistory = false },
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun vibrate(context: Context, duration: Long, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(duration)
            }
        }
    } catch (e: Exception) {
        // Handle devices without vibrator or missing permission
    }
}

fun getStartOfDayTimestamp(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}
