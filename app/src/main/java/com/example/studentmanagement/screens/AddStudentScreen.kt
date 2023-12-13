package com.example.studentmanagement.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


enum class Gender {
    Male,
    Female,
    Other
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudent(navController: NavHostController) {
    var selectedGender by remember { mutableStateOf(Gender.Male) }

    var studentName by remember { mutableStateOf("") }
    var fatherName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var classTiltle by remember { mutableStateOf("") }
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
                        Text(text = "Add Student", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = studentName,
                onValueChange = { studentName = it },
                label = { Text(text = "Student Name") },
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
                value = fatherName,
                onValueChange = { fatherName = it },
                label = { Text(text = "Father Name") },
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
                value = dob,
                onValueChange = { dob = it },
                label = { Text(text = "Date Of Birth") },
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            ) {
                Text(
                    text = "Gender",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                RadioButton(

                    selected = selectedGender == Gender.Male,
                    onClick = { selectedGender = Gender.Male },

                    )
                Text(
                    text = "Male",
                    fontSize = 15.sp,
                    color = Color.Black
                )

                RadioButton(

                    selected = selectedGender == Gender.Female,
                    onClick = { selectedGender = Gender.Female }
                )
                Text(
                    text = "Female",
                    fontSize = 15.sp,
                    color = Color.Black
                )


            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = mobile,
                onValueChange = { mobile = it },
                label = { Text(text = "Mobile") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
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
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
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
                value = address,
                onValueChange = { address = it },
                label = { Text(text = "Address ") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black, // Text color
                    focusedBorderColor = Color.Blue, // Border color when focused
                    unfocusedBorderColor = Color.Gray, // Border color when not focused
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.None,
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
            SpinnerDesigne()

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (validateFieldsStudent(
                            studentName,
                            fatherName,
                            dob,
                            selectedGender.name,
                            mobile,
                            email,
                            address
                        )
                    ) {

                        val database = Firebase.database
                        val myRef =
                            database.getReference("studentDb")
                                .child(Firebase.auth.currentUser!!.uid)

                        val key =
                            database.getReference("studentDb")
                                .child(Firebase.auth.currentUser!!.uid).push().key
                        val addClassRequest =
                            AddStudentModel(key!!,
                                studentName,
                                fatherName,
                                dob,
                                selectedGender.name,
                                mobile,
                                email,
                                address
                            )
                        myRef.child(key!!).setValue(addClassRequest).addOnCompleteListener {
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


//    Column(
//        modifier = Modifier
//            .padding(20.dp)
//            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        OutlinedTextField(
//            value = classTitle,
//            onValueChange = { classTitle = it },
//            label = { Text(text = "Class Name") },
//            modifier = Modifier.fillMaxWidth()
//
//        )
//
//        OutlinedTextField(
//            value = classTitle,
//            onValueChange = { classTitle = it },
//            label = { Text(text = "Section") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = classTitle,
//            onValueChange = { classTitle = it },
//            label = { Text(text = "Class Timing") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = classTitle,
//            onValueChange = { classTitle = it },
//            label = { Text(text = "Room No.") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = classTitle,
//            onValueChange = { classTitle = it },
//            label = { Text(text = "Description ") },
//            modifier = Modifier.fillMaxWidth(),
//            maxLines = 5,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                textColor = Color.Black, // Text color
//                focusedBorderColor = Color.Blue, // Border color when focused
//                unfocusedBorderColor = Color.Gray, // Border color when not focused
//            ),
//        )
//
//
//        Button(
//            onClick = { /*TODO*/ }, modifier = Modifier.padding(20.dp)
//
//        ) {
//            Text(text = "Create Class")
//        }
//
//    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerDesigne() {
    var classItems by remember { mutableStateOf<List<AddClassRequest>>(emptyList()) }
    LaunchedEffect(true) { // Use a constant value for LaunchedEffect to ensure it runs only once
        val database = Firebase.database
        val myRef =
            database.getReference("classDb").child(Firebase.auth.currentUser!!.uid)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(AddClassRequest::class.java)
                }
                classItems = itemList
//                            classItems= emptyList()
//                            for (data in snapshot.children) {
//                                classItems +=  data.getValue(AddClassRequest::class.java)!!
//                                Log.d("class data", data.toString())
//                            }
                Log.d("class size", classItems.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancellation, if needed
            }
        }

        myRef.addValueEventListener(valueEventListener)

        // Remove the listener when the composable is disposed
//                    onDispose {
//                        myRef.removeEventListener(valueEventListener)
//                    }
    }

    var mExpanded by remember { mutableStateOf(false) }
    val mCities = listOf("BCA", "MCA", "MBA", "BBA")
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column() {

        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text("Class") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            },


        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.background(Color.Gray)


        ) {
            classItems.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label.classTitle
                    mExpanded = false
                }, text = {
                    Text(text = label.classTitle)
                })
            }
        }
    }
}


data class AddStudentModel(
    var id: String ="",
    var studentName: String ="",
    var fatherName: String ="",
    var dob: String ="",
    var gender: String ="",
    var mobile: String ="",
    var email: String ="",
    var classTitle: String =""
)


fun validateFieldsStudent(
    studentName: String,
    fatherName: String,
    dob: String,
    gender: String,
    mobile: String,
    email: String,
    classTitle: String
): Boolean {
    return studentName.isNotBlank() &&
            fatherName.isNotBlank() &&
            dob.isNotBlank() &&
            gender.isNotBlank() &&
            mobile.isNotBlank()
    email.isNotBlank()
    classTitle.isNotBlank()
}