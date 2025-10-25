package com.example.neettoper.features.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.neettoper.ui.theme.NeetBorder
import com.example.neettoper.ui.theme.NeetTextSecondary

// Mock data class for a doubt
data class Doubt(
    val id: String,
    val author: String,
    val subject: String,
    val title: String,
    val answers: Int
)

@Composable
fun CommunityFeedScreen(
    onDoubtClicked: (String) -> Unit // Passes the Doubt ID
) {
    // Mock data for the feed
    val doubts = listOf(
        Doubt("d1", "Ananya S.", "Physics", "Why does the rotational inertia of a solid sphere differ from a hollow one?", 3),
        Doubt("d2", "Rohan V.", "Chemistry", "I'm confused about hybridization. How does sp3 work in methane?", 1),
        Doubt("d3", "Priya K.", "Biology", "What is the key difference between Mitosis and Meiosis II?", 5),
        Doubt("d4", "Karan B.", "Physics", "Can someone explain Lenz's Law with a simple example?", 0)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(), // Adds padding for status bar
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Community Doubts",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(doubts) { doubt ->
            DoubtCard(
                doubt = doubt,
                onClick = { onDoubtClicked(doubt.id) }
            )
        }

        item {
            // Add spacer at the bottom so it doesn't hide behind nav bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DoubtCard(
    doubt: Doubt,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, NeetBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // --- Card Header: Author and Subject ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Posted by ${doubt.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeetTextSecondary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = doubt.subject.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Card Body: Doubt Title ---
            Text(
                text = doubt.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- Card Footer: Answer Count ---
            Text(
                text = if (doubt.answers == 0) "Be the first to answer"
                else if (doubt.answers == 1) "1 Answer"
                else "${doubt.answers} Answers",
                style = MaterialTheme.typography.bodySmall,
                color = if (doubt.answers == 0) NeetTextSecondary else MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}