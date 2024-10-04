package prabhakar.manish.radiobuttoncompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    correctAnswers: Int,
    totalQuestions: Int,
    onRestartQuiz: () -> Unit,
    onQuitQuiz: () -> Unit // Callback for quitting
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)), // Background with rounded corners
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Completed!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "You answered $correctAnswers out of $totalQuestions correctly.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Show a smiley or sad face based on performance
        val smiley = if (correctAnswers > totalQuestions / 2) "😊" else "😞"
        Text(
            text = smiley,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize * 2, // Make it bigger
            modifier = Modifier.padding(16.dp)
        )

        // Progress indicator based on performance
        LinearProgressIndicator(
            progress = correctAnswers.toFloat() / totalQuestions,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

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

        Spacer(modifier = Modifier.height(8.dp)) // Add some space between buttons

//        // Quit Quiz Button
//        Button(
//            onClick = { onQuitQuiz() },
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error), // Use error color for quit
//            shape = RoundedCornerShape(8.dp) // Rounded corners for button
//        ) {
//            Text(
//                text = "Quit Quiz",
//                style = MaterialTheme.typography.titleMedium // Keep the style consistent
//            )
//        }
    }
}
