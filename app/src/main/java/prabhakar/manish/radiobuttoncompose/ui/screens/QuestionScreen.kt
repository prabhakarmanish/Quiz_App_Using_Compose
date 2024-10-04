package prabhakar.manish.radiobuttoncompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import prabhakar.manish.radiobuttoncompose.ui.theme.Question

@Composable
fun QuestionScreen(
    question: Question,
    totalQuestions: Int,  // Total number of questions
    currentQuestionIndex: Int, // Current question index
    onAnswerSelected: (Boolean) -> Unit,
    onNextQuestion: () -> Unit, // Callback to load the next question
    onQuitQuiz: () -> Unit // Callback for quitting the quiz
) {
    var selectedOptionIndex by remember { mutableStateOf(-1) }
    var showQuitDialog by remember { mutableStateOf(false) } // State for the quit dialog

    // Reset selection for new questions
    LaunchedEffect(question) {
        selectedOptionIndex = -1 // Reset to unselected when the question changes
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp) // Slightly reduced padding
        )

        question.options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { // Make the entire row clickable
                        selectedOptionIndex = index
                    }
                    .padding(15.dp) // Padding inside the row for aesthetics
            ) {
                RadioButton(
                    selected = selectedOptionIndex == index,
                    onClick = null, // Set to null to avoid handling click separately
                    modifier = Modifier.size(20.dp) // Adjusted radio button size
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 8.dp) // Slightly reduced spacing for readability
                        .weight(1f) // Allow text to occupy available space
                )
            }
        }

        Button(
            onClick = {
                if (selectedOptionIndex != -1) { // Ensure an option is selected
                    val isCorrect = selectedOptionIndex == question.correctAnswer
                    onAnswerSelected(isCorrect)
                    onNextQuestion() // Load the next question after answering
                }
            },
            modifier = Modifier
                .padding(top = 16.dp) // Moderate padding
                .fillMaxWidth()
                .height(48.dp), // Slightly reduced button height
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = MaterialTheme.shapes.medium // Use default shape style
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.titleMedium // Updated style
            )
        }

        // Quit Button
        Button(
            onClick = { showQuitDialog = true },
            modifier = Modifier
                .padding(top = 12.dp) // Moderate padding
                .fillMaxWidth()
                .height(48.dp), // Slightly reduced button height
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error // Optional: Use error color
            ),
            shape = MaterialTheme.shapes.medium // Use default shape style
        ) {
            Text(
                text = "Quit Quiz",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    // Confirmation Dialog
    if (showQuitDialog) {
        ConfirmationDialog(
            onConfirm = {
                onQuitQuiz() // Call the quit function
                showQuitDialog = false // Dismiss the dialog
            },
            onDismiss = {
                showQuitDialog = false // Just dismiss the dialog
            }
        )
    }
}

// Confirmation Dialog Component
@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Quit Quiz") },
        text = { Text("Are you sure you want to quit the quiz?") },
        confirmButton = {
            Button(onClick = {
                onConfirm()
            }) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}
