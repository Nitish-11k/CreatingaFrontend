package com.example.neettoper.features.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neettoper.ui.theme.NeetBorder
import com.example.neettoper.ui.theme.NeetTextSecondary

// Mock data class for an answer
data class Answer(
    val author: String,
    val text: String,
    val videoUrl: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubtDetailScreen(
    doubtId: String,
    onNavigateBack: () -> Unit
) {
    // --- Mock Data (you would fetch this based on doubtId) ---
    val mockQuestion = Doubt("d1", "Ananya S.", "Physics", "Why does the rotational inertia of a solid sphere differ from a hollow one?", 3)
    val mockAnswers = listOf(
        Answer("Rohan V. (Master)", "It's all about mass distribution! In a solid sphere, the mass is distributed throughout. In a hollow one, all the mass is at the radius 'R'. The formula I = Σ(m*r^2) shows that mass farther from the axis contributes more to inertia. So, the hollow sphere has greater inertia.", null),
        Answer("Priya K.", "Think of an ice skater. When they pull their arms in (like a solid sphere), they spin faster. When their arms are out (like a hollow sphere), they spin slower. More inertia = harder to spin.", null),
        Answer("Mentor (Dropper)", "Great answers above. I made a quick 2-min video explaining the derivation, check it out.", "http://example.com/video.mp4")
    )
    // --------------------------------------------------------

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(mockQuestion.subject) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        // --- THIS IS NEW: The "Add Answer" box at the bottom ---
        bottomBar = {
            AddAnswerSection()
        }
    ) { innerPadding ->
        LazyColumn(
            // Use contentPadding to avoid the top and bottom bars
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // --- 1. The Original Question ---
            item {
                QuestionHeader(doubt = mockQuestion)
            }

            // --- 2. The List of Answers ---
            item {
                Text(
                    text = "${mockAnswers.size} Answers",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(mockAnswers) { answer ->
                AnswerCard(answer = answer)
            }
        }
    }
}

@Composable
private fun QuestionHeader(doubt: Doubt) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Posted by ${doubt.author}",
            style = MaterialTheme.typography.bodySmall,
            color = NeetTextSecondary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = doubt.title,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun AnswerCard(answer: Answer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, NeetBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Answer by ${answer.author}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = answer.text,
                style = MaterialTheme.typography.bodyLarge
            )
            // --- This is where you would show the video ---
            if (answer.videoUrl != null) {
                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = "Video",
                        tint = NeetTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Video attached",
                        style = MaterialTheme.typography.bodySmall,
                        color = NeetTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun AddAnswerSection() {
    var answerText by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp, // Adds a shadow
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .safeDrawingPadding() // Ensures it doesn't go under the system nav bar
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = answerText,
                onValueChange = { answerText = it },
                label = { Text("Write your answer...") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // --- THE "ADD VIDEO" BUTTON ---
                IconButton(onClick = { /* TODO: Add video picker logic */ }) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Attach Video",
                        tint = NeetTextSecondary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO: Add post answer logic */ },
                    enabled = answerText.isNotBlank()
                ) {
                    Text("Post")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Post Answer",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}