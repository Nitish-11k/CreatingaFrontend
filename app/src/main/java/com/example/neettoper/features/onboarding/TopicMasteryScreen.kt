package com.example.neettoper.features.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import com.example.neettoper.common.composables.TopicCheckboxItem
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.neettoper.data.MockData

@Composable
fun TopicMasteryScreen(
    subjectName: String,
    onContinueClicked: (Set<String>) -> Unit
) {
    // Get the list of topics for the passed subject, or an empty list if not found
    val topics = MockData.topicsBySubject[subjectName] ?: emptyList()

    // This set will hold the topics the user checks
    var selectedTopics by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding() // Automatically adds padding for status bar (top) and nav bar (bottom)
            .padding(horizontal = 16.dp), // We only need to add horizontal padding ourselves
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Strengths in $subjectName",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp) // Added a little extra top padding
        )
        Text(
            text = "Check all the topics you have already mastered. This will help us build your plan.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Scrollable list of topics
        LazyColumn(
            modifier = Modifier.weight(1f) // Takes up all available space
        ) {
            items(topics) { topic ->
                TopicCheckboxItem(
                    topicName = topic,
                    isChecked = topic in selectedTopics,
                    onCheckedChange = {
                        // Add or remove the topic from the set
                        selectedTopics = if (topic in selectedTopics) {
                            selectedTopics - topic
                        } else {
                            selectedTopics + topic
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Continue Button
        Button(
            onClick = {
                // Pass the set of selected topics to the navigation
                onContinueClicked(selectedTopics)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Give button a little breathing room from bottom
    }
}

//@Composable
//private fun TopicCheckboxItem(
//    topicName: String,
//    isChecked: Boolean,
//    onCheckedChange: (Boolean) -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onCheckedChange(!isChecked) } // Make the whole row clickable
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Checkbox(
//            checked = isChecked,
//            onCheckedChange = onCheckedChange,
//            colors = CheckboxDefaults.colors(
//                checkedColor = MaterialTheme.colorScheme.primary
//            )
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(
//            text = topicName,
//            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal,
//            color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
//        )
//    }
//}