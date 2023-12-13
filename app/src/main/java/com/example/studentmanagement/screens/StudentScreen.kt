package com.example.studentmanagement.screens


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.studentmanagement.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(navController: NavHostController) {


    val showDialog = remember { mutableStateOf(false) }
    var itemStudent = remember { mutableStateOf(AddStudentModel()) }

    if (showDialog.value) {
        StudentDailog(itemStudent.value) {
            showDialog.value = false
        }
    }

    Column {
        TopAppBar(
            title = {
                Text(text = "Student", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },

            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF565899),
                titleContentColor = Color.White,
            )
        )


        Scaffold(
            content = {
                it.calculateTopPadding()

                var classItems by remember { mutableStateOf<List<AddStudentModel>>(emptyList()) }


                LaunchedEffect(true) { // Use a constant value for LaunchedEffect to ensure it runs only once
                    val database = Firebase.database
                    val myRef =
                        database.getReference("studentDb").child(Firebase.auth.currentUser!!.uid)

                    val valueEventListener = object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val itemList = snapshot.children.mapNotNull { dataSnapshot ->
                                dataSnapshot.getValue(AddStudentModel::class.java)
                            }
                            classItems = itemList
                            Log.d("student size", classItems.size.toString())
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

                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(Modifier.padding(5.dp)) {
                    items(classItems) { item ->

                        StudentItem(
                            item
                        )
                        {
                            showDialog.value = true
                            itemStudent.value = item
                        }


                    }

                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_student_screen")
                        // Handle FAB click here
//                        showDialog.value = true

                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            },

            )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentItem(item: AddStudentModel, onClickStudent: () -> Unit) {
    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(8.dp), onClick = {
            onClickStudent.invoke()


        },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F3))

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.student_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFFFFC107), CircleShape),

                )
            Spacer(modifier = Modifier.height(5.dp))
            Column(modifier = Modifier.weight(0.4f)) {
                Text(
                    text = item.studentName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(
                        0xFFF2F2F5
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.classTitle,
                    fontWeight = FontWeight.Thin,
                    fontSize = 15.sp,
                    color = Color(
                        0xFFF1F1F1
                    )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.phone_call),
                contentDescription = "",
                modifier = Modifier
                    .weight(0.2f)
                    .size(40.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_CALL);
                        intent.data = Uri.parse("tel:${item.mobile}")
                        context.startActivity(intent)
                    },


                )


        }

    }
}

//@Preview
@Composable
fun PreviewStudent() {
//    StudentItem("Ashok", "bca", "9887275527") {}

}


@Composable
fun StudentDailog(item: AddStudentModel, onClick: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { }, content = {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
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
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                            .height(130.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.student_icon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(8.dp)
                                .clip(CircleShape)
                                .align(Alignment.Center)
                                .border(2.dp, Color(0xFFFFC107), CircleShape),

                            )

                        // Close Button
                        IconButton(
                            onClick = { onClick.invoke() },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        // Dummy TextView

                    }

                    // Bottom Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Buttons
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.studentName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,


                            )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Class :" + item.classTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier

                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {

                            Button(
                                onClick = {

                                    val intent = Intent(Intent.ACTION_CALL);
                                    intent.data = Uri.parse("tel:${item.mobile}")
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .weight(0.2f)
                                    .padding(5.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF6CDC2D))
                            ) {
                                Text(text = "Call")
                            }

                            Button(
                                onClick = {


                                    val database = Firebase.database
                                    val myRef =
                                        database.getReference("studentDb").child(Firebase.auth.currentUser!!.uid).child(item.id).removeValue()

                                    onClick.invoke()
                                },
                                modifier = Modifier
                                    .weight(0.2f)
                                    .padding(5.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFFF33324))
                            ) {
                                Text(text = "Delete")
                            }

                            Button(
                                onClick = {
                                    val sendIntent = Intent(Intent.ACTION_VIEW)
                                    sendIntent.data = Uri.parse("sms:")
                                    sendIntent.putExtra("address", item.mobile);
                                    context.startActivity(sendIntent);
                                },
                                modifier = Modifier
                                    .weight(0.2f)
                                    .padding(5.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
                            ) {
                                Text(text = "Message")
                            }


                        }

                        // Class Name


                        // Date of Birth
                        Text(
                            text = "Date of Birth",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp)
                        )

                        Text(
                            text = item.dob,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp)
                        )
                    }
                }
            }
        }
    })

}

@Preview
@Composable
fun CardViewComposePreview() {
    var item = AddStudentModel()
    MaterialTheme(
        typography = Typography(),
//        contentColor = MaterialTheme.colorScheme.onBackground,
        shapes = Shapes(),
        content = {
            StudentDailog(item) {}
        }
    )
}
