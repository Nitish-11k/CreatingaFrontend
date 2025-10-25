package com.example.neettoper.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes // <-- Import
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neettoper.ui.theme.NeetTextSecondary // <-- Import

@Composable
fun TopicCheckboxItem(
    topicName: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    // --- THIS IS NEW ---
    // Make it optional. It will only be used if notes exist.
    onNotesClicked: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // We make the main part clickable for the checkbox
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = topicName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal,
            color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f) // <-- This makes it take up available space
        )

        // --- THIS IS THE NEW PART ---
        // If the onNotesClicked lambda is provided, show the button
        if (onNotesClicked != null) {
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onNotesClicked) {
                Icon(
                    imageVector = Icons.Default.Notes,
                    contentDescription = "View Notes",
                    tint = NeetTextSecondary
                )
            }
        }
        // --- END OF NEW PART ---
    }
}