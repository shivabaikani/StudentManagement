package com.example.studentmanagement.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
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

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showSystemUi = true, heightDp = 500)
@Composable
fun ClassScreen(navController: NavHostController) {
    val showDialog = remember { mutableStateOf(false) }
    var itemStudent = remember { mutableStateOf(AddClassRequest()) }

    if (showDialog.value) {
        ClassDailog(itemStudent.value) {
            showDialog.value = false
        }
    }
    Column {
        TopAppBar(
            title = {
                Text(text = "Class", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },


            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF565899),
                titleContentColor = Color.White,
            )
        )


        Scaffold(
            content = {
                it.calculateTopPadding()
                var classList = mutableListOf<AddClassRequest>()
                var classItems by remember { mutableStateOf<List<AddClassRequest>>(emptyList()) }
//                classItems= getData()
                val database = Firebase.database
                val myRef =
                    database.getReference("classDb").child(Firebase.auth.currentUser!!.uid)


                Log.d("class data ", classList.size.toString())
                // Your main content goes here
//                classList.add(AddClassRequest("BCA", "b", "9:30", "12", ""))
//                classList.add(AddClassRequest("BCA", "b", "9:30", "12", ""))
//                classList.add(AddClassRequest("BCA", "b", "9:30", "12", ""))
//                classList.add(AddClassRequest("BCA", "b", "9:30", "12", ""))
//                classList.add(AddClassRequest("BCA", "b", "9:30", "12", ""))
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

                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(classItems) { item ->

                        ClassCategory(item){
                            showDialog.value = true
                            itemStudent.value = item
                        }


                    }

                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_class_screen")
                        // Handle FAB click here

                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            },

            )

    }
}

@Preview
@Composable
fun PreviewClassDailog() {
    ClassDailog(item = AddClassRequest()) {
        
    }
}

@Composable
fun ClassDailog(item: AddClassRequest, onClick: () -> Unit) {
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
                            painter = painterResource(id = R.drawable.stmng_png),
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
                            text = item.classTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,


                            )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Section :" + item.section,
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
                                    val database = Firebase.database
                                    val myRef =
                                        database.getReference("classDb").child(Firebase.auth.currentUser!!.uid).child(item.id).removeValue()

                                    onClick.invoke()

                                },
                                modifier = Modifier
                                    .weight(0.2f)
                                    .padding(5.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFFF33324))
                            ) {
                                Text(text = "Delete")
                            }

                         


                        }

                        // Class Name


                        // Date of Birth
                        Text(
                            text = "Total Student:",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp)
                        )

                        Text(
                            text = "Class Time: "+item.classTiming,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassCategory(item: AddClassRequest,onClickStudent: () -> Unit) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(8.dp), onClick = {
            onClickStudent.invoke()


        }

    ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.stmng_png),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF2196F3), CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(5.dp))
            Column(modifier = Modifier.weight(0.4f)) {
                Text(text = "Class:" + item.classTitle)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Timing:" + item.classTiming)
            }
            Column(modifier = Modifier.weight(0.4f)) {
                Text(text = "Student :")
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Room No:" + item.roomNo)
            }
        }

    }
}

@Preview
@Composable
fun PreviewClassCategory() {
    ClassCategory(item = AddClassRequest("1","bca", "b", "9.30 ", "2","computer")){
    }
    
}
data class AddClassRequest(
    var id: String = "",
    var classTitle: String = "",
    var section: String = "",
    var classTiming: String = "",
    var roomNo: String = "",
    var description: String = ""
)

