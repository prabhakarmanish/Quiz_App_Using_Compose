package prabhakar.manish.radiobuttoncompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun ResultScreen(
    correctAnswers: List<String>, // List of correct answers
    incorrectAnswers: List<String>, // List of incorrect answers
    totalQuestions: Int,
    onRestartQuiz: () -> Unit,
    onQuitQuiz: () -> Unit // Callback for quitting
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ), // Background with rounded corners
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Completed!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "You answered ${correctAnswers.size} out of $totalQuestions correctly.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Show a smiley or sad face based on performance
        val smiley = if (correctAnswers.size > totalQuestions / 2) "😊" else "😞"
        Text(
            text = smiley,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize * 2, // Make it bigger
            modifier = Modifier.padding(16.dp)
        )

        // Progress indicator based on performance
        LinearProgressIndicator(
            progress = correctAnswers.size.toFloat() / totalQuestions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // Display correct answers
        if (correctAnswers.isNotEmpty() || incorrectAnswers.isNotEmpty()) {
            Text(
                text = "Results:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            // Wrap answers in a LazyColumn for scrolling
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                // Display correct answers
                if (correctAnswers.isNotEmpty()) {
                    item {
                        Text(
                            text = "Correct Answers:",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    correctAnswers.forEach { answer ->
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFB2FFB2)) // Light green color
                            ) {
                                Text(
                                    text = answer,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }

                // Display incorrect answers
                if (incorrectAnswers.isNotEmpty()) {
                    item {
                        Text(
                            text = "Incorrect Answers:",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    incorrectAnswers.forEach { answer ->
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFB2B2)) // Light red color
                            ) {
                                Text(
                                    text = answer,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Red // Set text color to red for incorrect answers
                                )
                            }
                        }
                    }
                }
            }
        }

        // Restart Quiz Button
        Button(
            onClick = { onRestartQuiz() },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp) // Rounded corners for button
        ) {
            Text(
                text = "Restart Quiz",
                style = MaterialTheme.typography.titleMedium // Keep the style consistent
            )
        }
    }
}
