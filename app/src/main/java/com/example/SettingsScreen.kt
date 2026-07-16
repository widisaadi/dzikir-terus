package com.example

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class for dzikir reading options
data class DzikirReading(
    val id: String,
    val nameIndonesia: String,
    val nameArab: String,
    val meaning: String
)

// Available dzikir readings
val dzikirReadings = listOf(
    DzikirReading("subhanallah", "Subhanallah", "سُبْحَانَ اللَّهِ", "Maha Suci Allah"),
    DzikirReading("alhamdulillah", "Alhamdulillah", "الْحَمْدُ لِلَّهِ", "Segala Puji Bagi Allah"),
    DzikirReading("allahuakbar", "Allahuakbar", "اللَّهُ أَكْبَرُ", "Allah Maha Besar"),
    DzikirReading("lailahaillallah", "La ilaha illallah", "لَا إِلَٰهَ إِلَّا اللَّهُ", "Tiada Tuhan Selain Allah"),
    DzikirReading("astaghfirullah", "Astaghfirullah", "أَسْتَغْفِرُ اللَّهَ", "Aku Mohon Ampun kepada Allah"),
    DzikirReading("lahaulawala", "La haula wala quwwata illa billah", "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ", "Tiada Daya dan Kekuatan Kecuali dengan Allah"),
    DzikirReading("subhanallahiwabihamdihi", "Subhanallahi wabihamdihi", "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ", "Maha Suci Allah dan Segala Puji Bagi-Nya"),
    DzikirReading("subhanallahilazhim", "Subhanallahil 'azhim", "سُبْحَانَ اللَّهِ الْعَظِيمِ", "Maha Suci Allah Yang Maha Agung"),
    DzikirReading("hasbunallah", "Hasbunallahu wa ni'mal wakil", "حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ", "Cukuplah Allah Bagi Kami dan Sebaik-baik Pelindung")
)

// Available target cycle options
val targetOptions = listOf(33, 34, 99, 100)

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("dzikir_prefs", Context.MODE_PRIVATE)

    // Settings state
    var selectedReading by remember { mutableStateOf(prefs.getString("selected_reading", "subhanallah") ?: "subhanallah") }
    var selectedTarget by remember { mutableStateOf(prefs.getInt("target_cycle", 33)) }
    var selectedTheme by remember { mutableStateOf(prefs.getInt("selected_theme", 0)) }
    var vibrationEnabled by remember { mutableStateOf(prefs.getBoolean("vibration_enabled", true)) }
    var vibrationMaxEnabled by remember { mutableStateOf(prefs.getBoolean("vibration_max_enabled", true)) }
    var soundEnabled by remember { mutableStateOf(prefs.getBoolean("sound_enabled", false)) }
    var keepScreenOn by remember { mutableStateOf(prefs.getBoolean("keep_screen_on", true)) }
    var reminderEnabled by remember { mutableStateOf(prefs.getBoolean("reminder_enabled", false)) }
    var reminderTime by remember { mutableStateOf(prefs.getString("reminder_time", "05:30") ?: "05:30") }
    var selectedLanguage by remember { mutableStateOf(prefs.getString("selected_language", "id") ?: "id") }

    // Dropdown states
    var showReadingDropdown by remember { mutableStateOf(false) }
    var showTargetDropdown by remember { mutableStateOf(false) }
    var showLanguageDropdown by remember { mutableStateOf(false) }
    
    val strings = Translations.getStrings(selectedLanguage)

    // Save helper
    fun saveSettings() {
        prefs.edit()
            .putString("selected_reading", selectedReading)
            .putInt("target_cycle", selectedTarget)
            .putInt("selected_theme", selectedTheme)
            .putBoolean("vibration_enabled", vibrationEnabled)
            .putBoolean("vibration_max_enabled", vibrationMaxEnabled)
            .putBoolean("sound_enabled", soundEnabled)
            .putBoolean("keep_screen_on", keepScreenOn)
            .putBoolean("reminder_enabled", reminderEnabled)
            .putString("reminder_time", reminderTime)
            .putString("selected_language", selectedLanguage)
            .apply()
    }

    // Colors
    val bgColor = Color(0xFFFBF4E6)
    val headerGreen = Color(0xFF1B3D2B)
    val cardBg = Color(0xFFFDF8EE)
    val textDark = Color(0xFF3B5234)
    val textLight = Color(0xFF8B8A6A)
    val goldAccent = Color(0xFFE8C26E)
    val toggleOn = Color(0xFF3B5234)
    val toggleOff = Color(0xFFD9D5CC)
    val iconBg = Color(0xFFF0EAE0)

    val currentReadingName = dzikirReadings.find { it.id == selectedReading }?.nameIndonesia ?: "Subhanallah"

    Box(modifier = modifier.fillMaxSize().background(bgColor)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ===== HEADER =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(IslamicArchShape)
                    .background(headerGreen)
            ) {
                // Subtle decorative pattern overlay
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val patternColor = Color(0xFF2A5A3F).copy(alpha = 0.3f)
                    // Draw subtle arch lines
                    for (i in 0..4) {
                        val yOffset = 20f + i * 25f
                        drawArc(
                            color = patternColor,
                            startAngle = 180f,
                            sweepAngle = 180f,
                            useCenter = false,
                            topLeft = androidx.compose.ui.geometry.Offset(size.width * 0.1f, yOffset),
                            size = androidx.compose.ui.geometry.Size(size.width * 0.8f, size.height * 0.4f),
                            style = Stroke(width = 0.5f)
                        )
                    }
                }

                // Back button
                IconButton(
                    onClick = {
                        saveSettings()
                        onBack()
                    },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 40.dp)
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Title
                Text(
                    text = strings.settingsTitle,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 4.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 20.dp)
                )
            }

            // ===== SCROLLABLE CONTENT =====
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ── BACAAN DZIKIR ──
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(iconBg, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📖", fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        // Text
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.dzikirReading,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textDark,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = strings.dzikirReadingDesc,
                                fontSize = 11.sp,
                                color = textLight
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Dropdown Button
                        Box {
                            OutlinedButton(
                                onClick = { showReadingDropdown = true },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, goldAccent),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = currentReadingName,
                                    fontSize = 12.sp,
                                    color = textDark,
                                    maxLines = 1
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = textDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showReadingDropdown,
                                onDismissRequest = { showReadingDropdown = false },
                                modifier = Modifier.background(cardBg)
                            ) {
                                dzikirReadings.forEach { reading ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(
                                                    text = reading.nameIndonesia,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = textDark
                                                )
                                                Text(
                                                    text = reading.nameArab,
                                                    fontSize = 16.sp,
                                                    color = textLight
                                                )
                                            }
                                        },
                                        onClick = {
                                            selectedReading = reading.id
                                            showReadingDropdown = false
                                            saveSettings()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // ── TARGET PUTARAN ──
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(iconBg, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔄", fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.cycleTarget,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textDark,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = strings.cycleTargetDesc,
                                fontSize = 11.sp,
                                color = textLight
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Box {
                            OutlinedButton(
                                onClick = { showTargetDropdown = true },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, goldAccent),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "$selectedTarget",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = textDark
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = textDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showTargetDropdown,
                                onDismissRequest = { showTargetDropdown = false },
                                modifier = Modifier.background(cardBg)
                            ) {
                                targetOptions.forEach { target ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = "$target",
                                                fontSize = 14.sp,
                                                fontWeight = if (target == selectedTarget) FontWeight.Bold else FontWeight.Normal,
                                                color = textDark
                                            )
                                        },
                                        onClick = {
                                            selectedTarget = target
                                            showTargetDropdown = false
                                            saveSettings()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // ── TEMA ──
                SettingsCard {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(iconBg, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🎨", fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = strings.themeColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textDark,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = strings.themeColorDesc,
                                    fontSize = 11.sp,
                                    color = textLight
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Theme Grid — 4 columns x 2 rows
                        val themeColors = listOf(
                            ThemePreview(Color(0xFF1B3D2B), Color(0xFFE8C26E), Color(0xFF2A5A3F)), // Green gold (current)
                            ThemePreview(Color(0xFF1B3D2B), Color(0xFFE8C26E), Color(0xFFFFFFFF)), // White green
                            ThemePreview(Color(0xFFF5F0E6), Color(0xFFC9B98A), Color(0xFFFDF8EE)), // Cream
                            ThemePreview(Color(0xFF1A1A2E), Color(0xFFE8C26E), Color(0xFF16213E)), // Dark navy
                            ThemePreview(Color(0xFF0A2647), Color(0xFFE8C26E), Color(0xFF144272)), // Deep blue
                            ThemePreview(Color(0xFF4A1942), Color(0xFFE8C26E), Color(0xFF6B2D6B)), // Purple
                            ThemePreview(Color(0xFF5C3D1E), Color(0xFFE8C26E), Color(0xFF7A5A3D)), // Brown
                            ThemePreview(Color(0xFFF5E6CA), Color(0xFFD4B896), Color(0xFFFDF3E7))  // Light beige
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            for (row in 0..1) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    for (col in 0..3) {
                                        val index = row * 4 + col
                                        val theme = themeColors[index]
                                        val isSelected = selectedTheme == index
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .aspectRatio(0.75f)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(theme.bgColor)
                                                .then(
                                                    if (isSelected) Modifier.border(
                                                        2.dp,
                                                        goldAccent,
                                                        RoundedCornerShape(12.dp)
                                                    ) else Modifier.border(
                                                        1.dp,
                                                        Color.Gray.copy(alpha = 0.2f),
                                                        RoundedCornerShape(12.dp)
                                                    )
                                                )
                                                .clickable {
                                                    selectedTheme = index
                                                    saveSettings()
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            // Mini preview of circle
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .background(theme.circleColor, CircleShape)
                                                    .border(
                                                        2.dp,
                                                        theme.borderColor,
                                                        CircleShape
                                                    )
                                            )

                                            // Checkmark for selected
                                            if (isSelected) {
                                                Box(
                                                    modifier = Modifier
                                                        .align(Alignment.TopEnd)
                                                        .padding(6.dp)
                                                        .size(20.dp)
                                                        .background(headerGreen, CircleShape),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text("✓", fontSize = 11.sp, color = Color.White)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // ── SECTION: PENGALAMAN ──
                SectionHeader(icon = "⚙️", title = strings.experienceSection)

                // Getar Setiap Ketukan
                SettingsCard {
                    SettingsToggleRow(
                        icon = "📳",
                        title = strings.vibration,
                        subtitle = strings.vibrationDesc,
                        checked = vibrationEnabled,
                        onCheckedChange = {
                            vibrationEnabled = it
                            saveSettings()
                        },
                        textDark = textDark,
                        textLight = textLight,
                        iconBg = iconBg,
                        toggleOn = toggleOn,
                        toggleOff = toggleOff
                    )
                }

                // Getar Putaran Maksimal
                SettingsCard {
                    SettingsToggleRow(
                        icon = "🔔",
                        title = strings.vibrationMax,
                        subtitle = strings.vibrationMaxDesc,
                        checked = vibrationMaxEnabled,
                        onCheckedChange = {
                            vibrationMaxEnabled = it
                            saveSettings()
                        },
                        textDark = textDark,
                        textLight = textLight,
                        iconBg = iconBg,
                        toggleOn = toggleOn,
                        toggleOff = toggleOff
                    )
                }

                // Suara Klik
                SettingsCard {
                    SettingsToggleRow(
                        icon = "🔊",
                        title = strings.clickSound,
                        subtitle = strings.clickSoundDesc,
                        checked = soundEnabled,
                        onCheckedChange = {
                            soundEnabled = it
                            saveSettings()
                        },
                        textDark = textDark,
                        textLight = textLight,
                        iconBg = iconBg,
                        toggleOn = toggleOn,
                        toggleOff = toggleOff
                    )
                }

                // Layar Tetap Nyala
                SettingsCard {
                    SettingsToggleRow(
                        icon = "☀️",
                        title = strings.keepScreenOn,
                        subtitle = strings.keepScreenOnDesc,
                        checked = keepScreenOn,
                        onCheckedChange = {
                            keepScreenOn = it
                            saveSettings()
                        },
                        textDark = textDark,
                        textLight = textLight,
                        iconBg = iconBg,
                        toggleOn = toggleOn,
                        toggleOff = toggleOff
                    )
                }

                // ── SECTION: PENGINGAT DZIKIR ──
                SectionHeader(icon = "🌙", title = strings.dailyReminder)

                // Pengingat Harian
                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(iconBg, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔔", fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.dailyReminder,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textDark,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = strings.dailyReminderDesc,
                                fontSize = 11.sp,
                                color = textLight
                            )
                        }

                        // Toggle
                        Switch(
                            checked = reminderEnabled,
                            onCheckedChange = {
                                if (it && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                                    if (androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                                        val intent = android.content.Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                            putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, context.packageName)
                                        }
                                        try {
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            // Fallback to app details
                                            val fallbackIntent = android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                                data = android.net.Uri.fromParts("package", context.packageName, null)
                                            }
                                            try { context.startActivity(fallbackIntent) } catch (e2: Exception) {}
                                        }
                                    }
                                }
                                reminderEnabled = it
                                saveSettings()
                                if (it) {
                                    ReminderScheduler.scheduleReminder(context, reminderTime)
                                } else {
                                    ReminderScheduler.cancelReminder(context)
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = toggleOn,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = toggleOff,
                                uncheckedBorderColor = Color.Transparent,
                                checkedBorderColor = Color.Transparent
                            )
                        )

                        if (reminderEnabled) {
                            Spacer(modifier = Modifier.width(8.dp))

                            // Time picker button
                            val timeParts = reminderTime.split(":")
                            val currentHour = timeParts.getOrNull(0)?.toIntOrNull() ?: 5
                            val currentMinute = timeParts.getOrNull(1)?.toIntOrNull() ?: 30

                            OutlinedButton(
                                onClick = {
                                    android.app.TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minute ->
                                            reminderTime = String.format("%02d:%02d", hourOfDay, minute)
                                            saveSettings()
                                            ReminderScheduler.scheduleReminder(context, reminderTime)
                                        },
                                        currentHour,
                                        currentMinute,
                                        true // 24-hour format
                                    ).show()
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, goldAccent),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("🕐", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = reminderTime,
                                    fontSize = 12.sp,
                                    color = textDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // ── SECTION: BAHASA ──
                SectionHeader(icon = "🌐", title = strings.languageSection)

                SettingsCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(iconBg, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🌐", fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.appLanguage,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textDark,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = strings.appLanguageDesc,
                                fontSize = 11.sp,
                                color = textLight
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Box {
                            val languageOptions = listOf(
                                "id" to "Indonesia",
                                "en" to "English",
                                "tl" to "Filipino",
                                "zh" to "中文简体"
                            )
                            val currentLangLabel = languageOptions.find { it.first == selectedLanguage }?.second ?: "Indonesia"

                            OutlinedButton(
                                onClick = { showLanguageDropdown = true },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, goldAccent),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = currentLangLabel,
                                    fontSize = 12.sp,
                                    color = textDark,
                                    maxLines = 1
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = textDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showLanguageDropdown,
                                onDismissRequest = { showLanguageDropdown = false },
                                modifier = Modifier.background(cardBg)
                            ) {
                                languageOptions.forEach { (code, label) ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = label,
                                                fontSize = 14.sp,
                                                fontWeight = if (code == selectedLanguage) FontWeight.Bold else FontWeight.Normal,
                                                color = textDark
                                            )
                                        },
                                        onClick = {
                                            selectedLanguage = code
                                            showLanguageDropdown = false
                                            saveSettings()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom spacing
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ===== REUSABLE COMPONENTS =====

data class ThemePreview(
    val circleColor: Color,
    val borderColor: Color,
    val bgColor: Color
)

@Composable
fun SettingsCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF8EE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(0.5.dp, Color(0xFFE8DFC8))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun SectionHeader(icon: String, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    ) {
        Text(icon, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3B5234),
            letterSpacing = 2.sp
        )
    }
}

@Composable
fun SettingsToggleRow(
    icon: String,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textDark: Color,
    textLight: Color,
    iconBg: Color,
    toggleOn: Color,
    toggleOff: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(iconBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textDark,
                letterSpacing = 1.sp
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = textLight
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = toggleOn,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = toggleOff,
                uncheckedBorderColor = Color.Transparent,
                checkedBorderColor = Color.Transparent
            )
        )
    }
}
