package com.example.neettoper.features.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.neettoper.data.MockData // <-- Import MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    // Lambda now passes both subjects
    onPlanCreated: (favoriteSubject: String, dangerousSubject: String) -> Unit
) {
    // Get subjects from MockData
    val subjects = MockData.subjects

    var favoriteSubject by remember { mutableStateOf(subjects[0]) }
    var favoriteExpanded by remember { mutableStateOf(false) }

    var dangerousSubject by remember { mutableStateOf(subjects[0]) }
    var dangerousExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to NeetToper!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Let's build your personal study plan. To start, tell us about your subjects.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Favorite Subject Dropdown
        Text(
            text = "What is your FAVORITE subject?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = favoriteExpanded,
            onExpandedChange = { favoriteExpanded = !favoriteExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = favoriteSubject,
                onValueChange = {},
                label = { Text("My Strength") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = favoriteExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = favoriteExpanded,
                onDismissRequest = { favoriteExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                subjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject) },
                        onClick = {
                            favoriteSubject = subject
                            favoriteExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Most Dangerous Subject Dropdown
        Text(
            text = "What is your MOST DANGEROUS subject?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = dangerousExpanded,
            onExpandedChange = { dangerousExpanded = !dangerousExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = dangerousSubject,
                onValueChange = {},
                label = { Text("My Weakness") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dangerousExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = dangerousExpanded,
                onDismissRequest = { dangerousExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                subjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject) },
                        onClick = {
                            dangerousSubject = subject
                            dangerousExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Get My Plan Button
        Button(
            onClick = {
                // Pass the selected subjects to the navigator
                onPlanCreated(favoriteSubject, dangerousSubject)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            // Disable button if subjects are the same
            enabled = favoriteSubject != dangerousSubject
        ) {
            Text("Continue")
        }

        if (favoriteSubject == dangerousSubject) {
            Text(
                text = "Favorite and Dangerous subjects cannot be the same.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}