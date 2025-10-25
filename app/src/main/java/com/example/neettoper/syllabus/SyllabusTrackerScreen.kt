package com.example.neettoper.features.syllabus

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.neettoper.common.composables.TopicCheckboxItem
import com.example.neettoper.data.DropperNote // <-- Import
import com.example.neettoper.data.MockData
import com.example.neettoper.ui.theme.NeetSurface
import com.example.neettoper.ui.theme.NeetTextSecondary
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class) // <-- Add ExperimentalMaterial3Api
@Composable
fun SyllabusTrackerScreen() {
    // This state would eventually be saved in a database or ViewModel
    var completedTopics by remember { mutableStateOf(setOf<String>()) }
    val allSubjects = MockData.topicsBySubject.keys.toList()

    // --- NEW STATE for Bottom Sheet ---
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var selectedNote by remember { mutableStateOf<DropperNote?>(null) }
    // ----------------------------------

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding() // Adds padding for status bar
        ) {
            item {
                Text(
                    text = "Syllabus Tracker",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            allSubjects.forEach { subject ->
                // --- Sticky Header for the Subject ---
                stickyHeader {
                    SubjectHeader(subject)
                }

                // --- List of Topics for that Subject ---
                val topics = MockData.topicsBySubject[subject] ?: emptyList()
                items(topics) { topic ->
                    // --- THIS IS UPDATED ---
                    // Check if a note exists for this topic
                    val note = MockData.notesByTopic[topic]

                    TopicCheckboxItem(
                        topicName = topic,
                        isChecked = topic in completedTopics,
                        onCheckedChange = {
                            completedTopics = if (topic in completedTopics) {
                                completedTopics - topic
                            } else {
                                completedTopics + topic
                            }
                        },
                        // Pass the lambda if a note exists
                        onNotesClicked = if (note != null) {
                            {
                                selectedNote = note
                                scope.launch { sheetState.show() }
                            }
                        } else {
                            null // No note, so pass null
                        }
                    )
                    // --- END OF UPDATE ---
                }
            }

            item {
                // Add spacer at the bottom so it doesn't hide behind nav bar
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // --- NEW BOTTOM SHEET COMPOSABLE ---
        if (sheetState.isVisible && selectedNote != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch { sheetState.hide() }
                    selectedNote = null
                },
                sheetState = sheetState,
            ) {
                // This is the content inside the sheet
                DropperNoteSheetContent(note = selectedNote!!)
            }
        }
        // ------------------------------------
    }
}

@Composable
private fun SubjectHeader(subject: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface) // Use Surface color
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = subject,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// --- NEW COMPOSABLE for the Bottom Sheet Content ---
@Composable
private fun DropperNoteSheetContent(note: DropperNote) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .safeDrawingPadding() // Ensures content is not hidden by nav bars
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Header
        Text(
            text = note.topic,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Notes from ${note.author}",
            style = MaterialTheme.typography.titleSmall,
            color = NeetTextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Note
        Text(
            text = "Dropper Notes",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = note.note,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Tip
        Text(
            text = "Pro Tip 💡",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = note.tip,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
        )
    }
}