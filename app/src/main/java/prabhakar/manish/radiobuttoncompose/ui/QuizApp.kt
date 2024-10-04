package prabhakar.manish.radiobuttoncompose.ui

import androidx.compose.runtime.*
import prabhakar.manish.radiobuttoncompose.ui.screens.QuestionScreen
import prabhakar.manish.radiobuttoncompose.ui.screens.ResultScreen
import prabhakar.manish.radiobuttoncompose.ui.theme.getQuestions

@Composable
fun QuizApp() {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    val questions = getQuestions()
    var correctAnswers by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    fun restartQuiz() {
        currentQuestionIndex = 0
        correctAnswers = 0
        showResult = false
    }

    fun quitQuiz() {
        // Handle quit action, e.g., navigate to a different screen or close the app
        // For now, we'll simply reset the state
        restartQuiz()
        // You may want to implement navigation to a different screen here if applicable
    }

    if (showResult) {
        ResultScreen(
            correctAnswers = correctAnswers,
            totalQuestions = questions.size,
            onRestartQuiz = { restartQuiz() }, // Pass restart function
            onQuitQuiz = { quitQuiz() } // Pass quit function
        )
    } else {
        QuestionScreen(
            question = questions[currentQuestionIndex],
            totalQuestions = questions.size, // Pass total number of questions
            currentQuestionIndex = currentQuestionIndex, // Pass current question index
            onAnswerSelected = { isCorrect ->
                if (isCorrect) correctAnswers++
                // Move to the next question
                if (currentQuestionIndex == questions.size - 1) {
                    showResult = true
                } else {
                    currentQuestionIndex++
                }
            },
            onNextQuestion = {
                // This is called after answering to prepare for the next question
                // No additional logic needed here since it’s handled above
            },
            onQuitQuiz = { quitQuiz() } // Pass the quit action to the QuestionScreen
        )
    }
}