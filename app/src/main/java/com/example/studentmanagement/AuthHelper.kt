package com.example.studentmanagement

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthHelper {
    private val auth: FirebaseAuth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun signInWithEmailAndPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    suspend fun signUpWithEmailAndPassword(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signOut() = auth.signOut()
}
