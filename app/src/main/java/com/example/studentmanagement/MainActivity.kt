package com.example.studentmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentmanagement.ui.theme.StudentManagementTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentManagementTheme {
                // A surface container using the 'background' color from the theme
                val splashDuration = 2000L // 2 seconds

                // Create a splash screen composable that transitions to the main screen
                var showSplash by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    delay(splashDuration)
                    showSplash = false
                }

                // When showSplash is true, display the splash screen, otherwise navigate to the main screen
                if (showSplash) {
                    SplashScreen()

                } else {
                    LoginScreen( )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentManagementTheme {
        Greeting("Android")
    }
}