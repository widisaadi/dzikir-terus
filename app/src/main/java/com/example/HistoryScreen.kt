package com.example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDatabase
import com.example.data.DzikirHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    strings: Translations.AppStrings,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).historyDao() }
    val historyList by dao.getAllHistory().collectAsState(initial = emptyList())

    // Colors
    val bgColor = Color(0xFFFBF4E6)
    val headerGreen = Color(0xFF1B3D2B)
    val cardBg = Color(0xFFFDF8EE)
    val textDark = Color(0xFF3B5234)
    val textLight = Color(0xFF8B8A6A)
    val goldAccent = Color(0xFFE8C26E)

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
                    onClick = onBack,
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
                    text = strings.historyTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 2.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 20.dp)
                )
            }

            // ===== LIST HISTORY =====
            if (historyList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = strings.noHistory,
                        color = textLight,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(historyList) { history ->
                        HistoryCard(
                            history = history,
                            strings = strings,
                            cardBg = cardBg,
                            textDark = textDark,
                            textLight = textLight,
                            goldAccent = goldAccent
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryCard(
    history: DzikirHistory,
    strings: Translations.AppStrings,
    cardBg: Color,
    textDark: Color,
    textLight: Color,
    goldAccent: Color
) {
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val dateString = dateFormatter.format(Date(history.dateTimestamp))

    val readingName = dzikirReadings.find { it.id == history.readingId }?.nameIndonesia ?: history.readingId

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Date Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dateString,
                    fontSize = 12.sp,
                    color = textLight,
                    fontWeight = FontWeight.Medium
                )
                
                // Badge for Reading Type
                Box(
                    modifier = Modifier
                        .background(goldAccent.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = readingName,
                        fontSize = 10.sp,
                        color = textDark,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Total Count
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${history.totalCount}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = textDark
                    )
                    Text(
                        text = strings.totalCountsToday,
                        fontSize = 11.sp,
                        color = textLight
                    )
                }
                
                // Divider
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
                
                // Total Cycles
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${history.cyclesCompleted}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = textDark
                    )
                    Text(
                        text = strings.totalCyclesToday,
                        fontSize = 11.sp,
                        color = textLight
                    )
                }
            }
        }
    }
}
