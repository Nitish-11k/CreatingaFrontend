package com.example.neettoper.features.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neettoper.ui.theme.NeetBorder
import com.example.neettoper.ui.theme.NeetTextSecondary

@Composable
fun ProfileScreen(
    onLogoutClicked: () -> Unit
) {
    // --- Mock User Data ---
    val userName = "Ananya S."
    val userEmail = "ananya.s@example.com"
    val studentType = "NEET Dropper"
    val topicsMastered = 42
    val doubtsAnswered = 8
    // ----------------------

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(), // Adds padding for status bar
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        // --- 1. User Info Header ---
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, NeetBorder, CircleShape),
                tint = NeetTextSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyLarge,
                color = NeetTextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            SuggestionChip(
                onClick = {},
                label = { Text(studentType) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider()
        }

        // --- 2. User Stats ---
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Your Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Topics Mastered",
                    count = topicsMastered.toString(),
                    icon = Icons.Default.Checklist,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Doubts Answered",
                    count = doubtsAnswered.toString(),
                    icon = Icons.Default.QuestionAnswer,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- 3. Settings List ---
        item {
            Text(
                text = "Account",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }
        item {
            SettingsItem(
                text = "Edit Profile",
                icon = Icons.Default.Edit,
                onClick = { /* TODO */ }
            )
        }
        item {
            SettingsItem(
                text = "Notifications",
                icon = Icons.Default.Notifications,
                onClick = { /* TODO */ }
            )
        }
        item {
            SettingsItem(
                text = "Log Out",
                icon = Icons.Default.Logout,
                onClick = onLogoutClicked, // <-- This is the important part
                isDestructive = true // Make it red
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    count: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, NeetBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = count,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = NeetTextSecondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val contentColor = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

    ListItem(
        headlineContent = {
            Text(
                text = text,
                color = contentColor,
                fontWeight = FontWeight.Medium
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}