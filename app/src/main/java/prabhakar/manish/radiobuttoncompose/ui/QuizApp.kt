package prabhakar.manish.radiobuttoncompose.ui

import android.app.Activity
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import prabhakar.manish.radiobuttoncompose.ui.screens.QuestionScreen
import prabhakar.manish.radiobuttoncompose.ui.screens.ResultScreen
import prabhakar.manish.radiobuttoncompose.ui.theme.getQuestions

@Composable
fun QuizApp(activity: Activity) {
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }  // Index of the current question
    val questions = getQuestions()  // List of questions
    var correctAnswersCount by rememberSaveable { mutableStateOf(0) }  // Count of correct answers
    var showResult by rememberSaveable { mutableStateOf(false) }  // Show result screen when quiz ends

    // Track whether each question was answered correctly (-1 means unanswered)
    var answers by rememberSaveable { mutableStateOf(IntArray(questions.size) { -1 }) }

    // Store the questions that were answered correctly and incorrectly
    val correctAnswers = remember { mutableStateListOf<String>() }
    val incorrectAnswers = remember { mutableStateListOf<String>() }

    // Debugging: Check current question index
    println("Current question index: $currentQuestionIndex")

    fun restartQuiz() {
        currentQuestionIndex = 0
        correctAnswersCount = 0
        answers = IntArray(questions.size) { -1 }  // Reset answers
        correctAnswers.clear()  // Clear correct answers
        incorrectAnswers.clear()  // Clear incorrect answers
        showResult = false
    }

    fun quitQuiz() {
        activity.finish()
    }

    if (showResult) {
        ResultScreen(
            correctAnswers.toList(),
            incorrectAnswers.toList(),
            questions.size,
            { restartQuiz() },
            { quitQuiz() }  // Pass the correct answers
            // Pass the incorrect answers
        )
    } else {
        // Debug: Display current question number
        // Text(text = "Question ${currentQuestionIndex + 1} of ${questions.size}")

        QuestionScreen(
            question = questions[currentQuestionIndex],  // Display current question
            totalQuestions = questions.size,  // Total number of questions
            currentQuestionIndex = currentQuestionIndex,  // Current question index
            onAnswerSelected = { isCorrect ->
                // Store the question text to differentiate between correct and incorrect answers
                val currentQuestionText = questions[currentQuestionIndex].question // Assuming each question has a text field

                // Update the correct answer count
                if (answers[currentQuestionIndex] == -1) {
                    // First time answering this question
                    if (isCorrect) {
                        correctAnswersCount++
                        correctAnswers.add(currentQuestionText) // Add to correct answers
                    } else {
                        incorrectAnswers.add(currentQuestionText) // Add to incorrect answers
                    }
                } else {
                    // If changing from an incorrect to correct answer, increment
                    if (isCorrect && answers[currentQuestionIndex] == 0) {
                        correctAnswersCount++
                        incorrectAnswers.remove(currentQuestionText) // Remove from incorrect answers
                        correctAnswers.add(currentQuestionText) // Add to correct answers
                    }
                    // If changing from a correct to incorrect answer, decrement
                    if (!isCorrect && answers[currentQuestionIndex] == 1) {
                        correctAnswersCount--
                        correctAnswers.remove(currentQuestionText) // Remove from correct answers
                        incorrectAnswers.add(currentQuestionText) // Add to incorrect answers
                    }
                }
                // Store the user's answer (1 for correct, 0 for incorrect)
                answers[currentQuestionIndex] = if (isCorrect) 1 else 0

                // Move to next question if not the last one
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    showResult = true  // Show result when the last question is answered
                }
            },
            onNextQuestion = {
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex  // Move to the next question
                }
            },
            onPreviousQuestion = {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--  // Move to the previous question
                }
            },
            onQuitQuiz = { quitQuiz() }
        )
    }
}
