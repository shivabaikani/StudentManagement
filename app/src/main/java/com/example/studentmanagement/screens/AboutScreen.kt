package com.example.studentmanagement.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newswave.DestinationScreen
import com.example.studentmanagement.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    var userInfo = remember { mutableStateOf(UserDataModel()) }
    LaunchedEffect(true) { // Use a constant value for LaunchedEffect to ensure it runs only once
        val database = Firebase.database
        val myRef =
            database.getReference("teacherDb").child(Firebase.auth.currentUser!!.uid)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userInfo.value= snapshot.getValue(UserDataModel::class.java)!!
                Log.d("student size", userInfo.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancellation, if needed
            }
        }

        myRef.addValueEventListener(valueEventListener)

    }
    Column {
        TopAppBar(
            title = {
                Text(text = "About", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },

            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF565899),
                titleContentColor = Color.White,
            )
        )


        Scaffold(
            content = {
                it.calculateTopPadding()
                Column {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .wrapContentSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            // Top Bar
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary)
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Spacer(modifier = Modifier.height(10.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.student_icon),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(8.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color(0xFFFFC107), CircleShape),

                                    )

                                Text(
                                    text = userInfo.value.fullName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(10.dp)


                                )
                                Text(
                                    text = userInfo.value.email,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(10.dp)


                                )
                                Text(
                                    text = userInfo.value.mobileNo,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(10.dp)


                                )


                            }

                            // Bottom Content
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(5.dp)
                                    .weight(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                // Buttons
                                Spacer(modifier = Modifier.height(18.dp))
                                Text(
                                    text = "About Application",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary,


                                    )

                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "This Project Student Management provides us a simple interface for maintenance of Student information. it can be used by educational institutes or colleges to maintain the records of student easily. ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF1A1B1B),
                                    modifier = Modifier.padding(10.dp),
                                    textAlign = TextAlign.Center

                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        FirebaseAuth.getInstance().signOut()

                                        navController.navigate(DestinationScreen.LoginScreenDest.route){
                                            popUpTo(DestinationScreen.LoginScreenDest.route){
                                                saveState = false
                                                inclusive = false
                                            }
                                            restoreState = false
                                            launchSingleTop = true
                                        }

                                    },
                                    modifier = Modifier
                                        .padding(15.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFFF33324))
                                ) {
                                    Text(text = "Logout")
                                }
                                // Class Name


                                // Date of Birth


                            }
                        }
                    }
                }
            })

    }

}

