package com.example.studentmanagement

import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.studentmanagement.ui.theme.StudentManagementTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        setContent {
            StudentManagementTheme {
                // A surface container using the 'background' color from the theme
                App()

            }
        }
    }

    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CALL_PHONE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.CALL_PHONE)
        } else {
            println("Permission isGranted")
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        when (it) {
            true -> {
                println("Permission has been granted by user")
            }

            false -> {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                //show your custom dialog and naviage to Permission seetings
            }
        }
    }
}