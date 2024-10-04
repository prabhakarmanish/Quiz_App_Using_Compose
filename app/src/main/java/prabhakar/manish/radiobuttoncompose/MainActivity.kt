// File: MainActivity.kt
package prabhakar.manish.radiobuttoncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import prabhakar.manish.radiobuttoncompose.ui.QuizApp
import prabhakar.manish.radiobuttoncompose.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                QuizApp()
            }
        }
    }
}
