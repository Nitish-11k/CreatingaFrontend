package com.example.neettoper.features.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neettoper.ui.theme.NeetBorder
import com.example.neettoper.ui.theme.NeetPrimary
import com.example.neettoper.ui.theme.NeetSurface
import com.example.neettoper.ui.theme.NeetTextSecondary

@Composable
fun DashboardScreen() {
    // --- Mock Data ---
    // This data will come from your onboarding and user's activity
    val effortData = mapOf(
        "Physics" to 2.5f,
        "Chemistry" to 4.0f,
        "Biology" to 3.0f
    )
    val weakSubject = "Physics"
    val weakestTopic = "System of Particles & Rotational Motion"
    // ---------------------

    // Use LazyColumn for scrollable dashboard content
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Welcome Card ---
        item {
            Text(
                text = "Welcome back, Student!", // You can personalize this later
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // --- Weak Areas Card ---
        item {
            WeakAreasCard(
                subject = weakSubject,
                topic = weakestTopic
            )
        }

        // --- Effort Charts Card ---
        item {
            EffortChartsCard(
                data = effortData
            )
        }
    }
}

@Composable
fun WeakAreasCard(subject: String, topic: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = NeetSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Focus Area",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Your Focus Area",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Weakest Subject
            Text(
                text = "WEAKEST SUBJECT",
                style = MaterialTheme.typography.labelSmall,
                color = NeetTextSecondary
            )
            Text(
                text = subject,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Weakest Topic
            Text(
                text = "TOPIC TO MASTER",
                style = MaterialTheme.typography.labelSmall,
                color = NeetTextSecondary
            )
            Text(
                text = topic,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Call to action
            Text(
                text = "Your 4-2-2 plan is designed to give this topic extra attention. Let's master it!",
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun EffortChartsCard(data: Map<String, Float>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, NeetBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Your Effort Distribution",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            // --- 1. Pie Chart ---
            Text(
                text = "Study Hours This Week",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            SimplePieChart(data = data)

            Spacer(modifier = Modifier.height(32.dp))
            Divider()
            Spacer(modifier = Modifier.height(24.dp))

            // --- 2. Bar Chart ---
            Text(
                text = "Topic Progress (Mock)",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Mock data for the bar chart
            SimpleBarChart(data = mapOf(
                "Wk 1" to 2f,
                "Wk 2" to 3f,
                "Wk 3" to 2.5f,
                "Wk 4" to 4f
            ))
        }
    }
}

@Composable
fun SimplePieChart(data: Map<String, Float>) {
    val total = data.values.sum()
    val slices = data.entries.toList()
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        NeetBorder,
        NeetTextSecondary
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The Pie Chart itself
        Canvas(
            modifier = Modifier
                .size(150.dp)
        ) {
            var startAngle = -90f // Start from the top
            slices.forEachIndexed { index, slice ->
                val sweepAngle = (slice.value / total) * 360f
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(width = size.width, height = size.height)
                )
                startAngle += sweepAngle
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // The Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            slices.forEachIndexed { index, slice ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(colors[index % colors.size], CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = slice.key, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun SimpleBarChart(data: Map<String, Float>) {
    val maxValue = data.values.maxOrNull() ?: 1f
    val labels = data.keys.toList()
    val values = data.values.toList()
    val barColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        // The Bar Chart
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes up all space except for labels
        ) {
            val barCount = values.size
            val barWidth = (size.width / barCount) * 0.6f // 60% of available space
            val barSpacing = (size.width / barCount) * 0.4f
            val spacingStart = barSpacing / 2

            values.forEachIndexed { index, value ->
                val barHeight = (value / maxValue) * size.height
                drawRect(
                    color = barColor,
                    topLeft = Offset(
                        x = spacingStart + index * (barWidth + barSpacing),
                        y = size.height - barHeight
                    ),
                    size = Size(barWidth, barHeight)
                )
            }
        }

        // The X-Axis Labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            labels.forEach { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}