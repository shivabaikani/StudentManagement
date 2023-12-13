package com.example.studentmanagement

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newswave.DestinationScreen
import com.example.studentmanagement.screens.AddClassStudent
import com.example.studentmanagement.screens.AddStudent
import com.example.studentmanagement.screens.ClassScreen
import com.example.studentmanagement.screens.HomeScreen
import com.example.studentmanagement.screens.LoginScreen
import com.example.studentmanagement.screens.SignUp
import com.example.studentmanagement.screens.SplashScreen
import com.example.studentmanagement.screens.StudentScreen

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreenDest.route
    ) {
        composable(route = DestinationScreen.SplashScreenDest.route) {
            SplashScreen(navController = navController)
        }

        composable(route = DestinationScreen.LoginScreenDest.route) {

            LoginScreen(navController = navController)
        }

        composable(route = DestinationScreen.SignupScreenDest.route) {

            SignUp(navController = navController)
        }

        composable(route = DestinationScreen.HomeScreenDest.route) {

            HomeScreen(navController)
        }
        composable(route = DestinationScreen.ClassScreenDest.route) {

            ClassScreen(navController = navController)
        }
        composable(route = DestinationScreen.AddClassScreenDest.route) {

            AddClassStudent(navController)
        }
        composable(route = DestinationScreen.StudentScreenDest.route) {

            StudentScreen(navController = navController)
        }
        composable(route = DestinationScreen.AddStudentScreenDest.route) {

            AddStudent(navController = navController)
        }
    }
}