package com.example.cookify.repository

import com.example.cookify.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserRepoImpl : UserRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // --- CHANGE 1: Specify Database URL ---
    // If you see a "Default FirebaseApp is not initialized" or "Timeout",
    // paste your database URL inside the getInstance("") call.
    // Example: FirebaseDatabase.getInstance("https://your-project-id-default-rtdb.firebaseio.com/")
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://cookify-individual-default-rtdb.firebaseio.com/")
    private val ref: DatabaseReference = database.getReference("Users")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) callback(true, "Login success")
            else callback(false, it.exception?.message ?: "Login failed")
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) callback(true, "Reset link sent to $email")
            else callback(false, task.exception?.message ?: "Failed to send reset email")
        }
    }

    override fun register(email: String, password: String, callback: (Boolean, String, FirebaseUser?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "Registration success", task.result?.user)
            } else {
                callback(false, task.exception?.message ?: "Registration failed", null)
            }
        }
    }

    override fun addUserToDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        // --- CHANGE 2: Ensure Data Persistence ---
        // Setting a priority or using updateChildren can sometimes bypass local cache timeouts
        ref.child(userId).setValue(model).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "User data saved successfully")
            } else {
                callback(false, task.exception?.message ?: "Database Timeout/Error")
            }
        }
    }

    override fun getUserById(userId: String, callback: (Boolean, UserModel?) -> Unit) {
        // Use addListenerForSingleValueEvent for one-time fetch to avoid "hanging" listeners
        ref.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                callback(user != null, user)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(false, null)
            }
        })
    }

    override fun getAllUser(callback: (Boolean, List<UserModel>?) -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allUsers = snapshot.children.mapNotNull { it.getValue(UserModel::class.java) }
                callback(true, allUsers)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(false, null)
            }
        })
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun deleteUser(userId: String, callback: (Boolean, String) -> Unit) {
        ref.child(userId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) callback(true, "User deleted")
            else callback(false, it.exception?.message ?: "Delete failed")
        }
    }

    override fun updateProfile(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        ref.child(userId).updateChildren(model.toMap()).addOnCompleteListener {
            if (it.isSuccessful) callback(true, "Profile updated")
            else callback(false, it.exception?.message ?: "Update failed")
        }
    }
}