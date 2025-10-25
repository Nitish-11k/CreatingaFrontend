package com.example.neettoper.features.community

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate // <-- Import
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.neettoper.data.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskQuestionScreen(
    onNavigateBack: () -> Unit,
    // --- UPDATED ---
    onPostQuestion: (subject: String, title: String, photoUri: String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPhotoUri by remember { mutableStateOf<String?>(null) } // <-- NEW

    // State for the dropdown menu
    val subjects = MockData.subjects
    var selectedSubject by remember { mutableStateOf(subjects[0]) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ask a Question") },
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .safeDrawingPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subject Dropdown
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedSubject,
                    onValueChange = {},
                    label = { Text("Subject") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    subjects.forEach { subject ->
                        DropdownMenuItem(
                            text = { Text(subject) },
                            onClick = {
                                selectedSubject = subject
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Question Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Your Question (Title)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Optional Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Add more details (Optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            // --- THIS IS THE NEW PART ---
            OutlinedButton(
                onClick = {
                    // TODO: Add actual photo picker logic here
                    // For now, we'll just simulate a file selection
                    selectedPhotoUri = "image_file_name.jpg"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = "Upload Photo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Upload Photo of Question")
            }

            // Show confirmation if a photo is selected
            if (selectedPhotoUri != null) {
                Text(
                    text = "Photo selected: $selectedPhotoUri",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            // --- END OF NEW PART ---

            Spacer(modifier = Modifier.weight(1f))

            // Post Button
            Button(
                onClick = {
                    // --- UPDATED ---
                    onPostQuestion(selectedSubject, title, selectedPhotoUri)
                },
                enabled = title.isNotBlank(), // Only enable if there is a title
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Post Question")
            }
        }
    }
}