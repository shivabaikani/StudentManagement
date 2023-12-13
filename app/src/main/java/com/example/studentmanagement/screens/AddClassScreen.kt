package com.example.studentmanagement.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddClassStudent(navController: NavHostController) {
    var classTitle by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var classTiming by remember { mutableStateOf("") }
    var roomNo by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column {
        Scaffold(
            content = {
                it.calculateTopPadding()
                // Your main content goes here
            },

            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Add Class", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
//                navController.popBackStack()

                        }) {
                            Icon(Icons.Filled.ArrowBack, null, tint = Color.White)

                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF565899),
                        titleContentColor = Color.White,
                    ),
                )
            },
            modifier = Modifier.height(80.dp)

        )


        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = classTitle,
                onValueChange = { classTitle = it },
                label = { Text(text = "Class Name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = section,
                onValueChange = { section = it },
                label = { Text(text = "Section") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = classTiming,
                onValueChange = { classTiming = it },
                label = { Text(text = "Class Timing") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = roomNo,
                onValueChange = { roomNo = it },
                label = { Text(text = "Room No.") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Description ") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black, // Text color
                    focusedBorderColor = Color.Blue, // Border color when focused
                    unfocusedBorderColor = Color.Gray, // Border color when not focused
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true

            )
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {

                    if (validateFields(classTitle, section, classTiming, roomNo, description)) {

                        val database = Firebase.database
                        val myRef =
                            database.getReference("classDb").child(Firebase.auth.currentUser!!.uid)
                        val key =
                            database.getReference("classDb").child(Firebase.auth.currentUser!!.uid)
                                .push().key


                        val addClassRequest =
                            AddClassRequest(key!!,classTitle, section, classTiming, roomNo, description)
                        myRef.child(key).setValue(addClassRequest).addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.popBackStack()

                            }

                        }

                    } else {
                        errorMessage = "Please fill in all fields."

                    }

                }, modifier = Modifier.padding(20.dp)

            ) {
                Text(text = "Create Class")
            }
            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it,
                    color = Color.Red
                )
            }
        }


    }


}


fun validateFields(
    classTitle: String,
    section: String,
    classTiming: String,
    roomNo: String,
    description: String
): Boolean {
    return classTitle.isNotBlank() &&
            section.isNotBlank() &&
            classTiming.isNotBlank() &&
            roomNo.isNotBlank() &&
            description.isNotBlank()
}