package com.example.studentmanagement.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import com.example.studentmanagement.R
import com.google.firebase.Firebase
import com.google.firebase.database.database


data class Item(val id: Int, val title: String, val description: String)

data class CardItem(val id: Int, val title: String, val imageUrl: Int)

//@Preview(device = "id:pixel_4_xl")
@Composable
fun HomeScreen(navController: NavHostController) {

    BottomNavigationScreen(navController)
//    CardWithBackgroundImageScreen(cardItems = items, navController)
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.background(Color.Gray),
                backgroundColor = Color.White,
                elevation = 10.dp,
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Class",
                            tint = if (selectedTab == 0) Color(0xFF565899) else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            "Class",
                            color = if (selectedTab == 0) Color(0xFF565899) else Color.Gray
                        )
                    },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Student",
                            tint = if (selectedTab == 1)Color(0xFF565899) else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            "Student",
                            color = if (selectedTab == 1) Color(0xFF565899) else Color.Gray
                        )
                    },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = if (selectedTab == 2) Color(0xFF565899) else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            "About",
                            color = if (selectedTab == 2) Color(0xFF565899) else Color.Gray
                        )
                    },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 50.dp)
        ) {
            // Content for the selected tab
            when (selectedTab) {
                0 -> {
                    ClassScreen(navController = navController)
                }
                1 -> {
                    StudentScreen(navController = navController)
                }
                2 -> {
                    AboutScreen(navController = navController)
                }
            }
        }
    }

}
