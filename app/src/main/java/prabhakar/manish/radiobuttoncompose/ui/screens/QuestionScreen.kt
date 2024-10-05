package prabhakar.manish.radiobuttoncompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import prabhakar.manish.radiobuttoncompose.ui.theme.Question

@Composable
fun QuestionScreen(
    question: Question,
    totalQuestions: Int,
    currentQuestionIndex: Int,
    onAnswerSelected: (Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onQuitQuiz: () -> Unit
) {
    var selectedOptionIndex by rememberSaveable { mutableStateOf(-1) }
    var showQuitDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(question) {
        selectedOptionIndex = -1
    }

    // Create a scroll state
    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .padding(15.dp, 50.dp, 15.dp, 50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxHeight(0.9f)) {
            // Make the column scrollable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color(0xFFF0F0F0)) // Light grey background
                    .verticalScroll(scrollState), // Enable vertical scrolling
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Enhanced Material Progress Bar
                LinearProgressIndicator(
                    progress = (currentQuestionIndex + 1) / totalQuestions.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // Slightly taller for visibility
                        .clip(MaterialTheme.shapes.small)
                        .padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.primary, // Progress color
                    trackColor = MaterialTheme.colorScheme.surfaceVariant // Background color
                )

                // Progress Text with larger font for readability
                Text(
                    text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )

                // Title Text
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 12.dp, start = 3.dp, end = 3.dp)
                )

                // Options
                question.options.forEachIndexed { index, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOptionIndex = index
                            }
                            .padding(15.dp)
                    ) {
                        RadioButton(
                            selected = selectedOptionIndex == index,
                            onClick = null, // Disable click on radio button itself
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                        )
                    }
                }

                // Row for Previous and Next buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Button
                    Button(
                        onClick = { onPreviousQuestion() },
                        enabled = currentQuestionIndex > 0,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Previous"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Previous")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Next Button
                    Button(
                        onClick = {
                            if (selectedOptionIndex != -1) {
                                val isCorrect = selectedOptionIndex == question.correctAnswer
                                onAnswerSelected(isCorrect)
                                onNextQuestion()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Next"
                        )
                    }
                }
            }

            // Top Right Exit Icon
            IconButton(
                onClick = { showQuitDialog = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Exit Quiz",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

    // Quit Confirmation Dialog
    if (showQuitDialog) {
        ConfirmationDialog(
            onConfirm = {
                onQuitQuiz()
                showQuitDialog = false
            },
            onDismiss = {
                showQuitDialog = false
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
            Button(onClick = onConfirm) {
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