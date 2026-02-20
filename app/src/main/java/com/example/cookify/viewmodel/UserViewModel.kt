package com.example.cookify.viewmodel

import android.util.Log // <-- Add this import
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookify.model.UserModel
import com.example.cookify.repository.UserRepo
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repo: UserRepo) : ViewModel() {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }

    // --- START: CORRECTED REGISTER FUNCTION ---
    fun register(
        email: String,
        password: String,
        // The callback should accept a FirebaseUser? object
        callback: (Boolean, String, FirebaseUser?) -> Unit
    ) {
        // This will now pass the correct callback to your repository
        repo.register(email, password) { success, message, firebaseUser ->
            if (!success) {
                // Add logging to see the actual error from the repository
                Log.e("UserViewModel", "Registration failed: $message")
            }
            // Pass the result directly to the UI
            callback(success, message, firebaseUser)
        }
    }
    // --- END: CORRECTED REGISTER FUNCTION ---

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, model, callback)
    }

    private val _users = MutableLiveData<UserModel?>()
    val users: MutableLiveData<UserModel?>
        get() = _users

    private val _allUsers = MutableLiveData<List<UserModel>?>()
    val allUsers: MutableLiveData<List<UserModel>?>
        get() = _allUsers

    fun getUserById(userId: String) {
        repo.getUserById(userId) { success, user ->
            if (success) {
                _users.postValue(user)
            }
        }
    }

    fun getAllUser() {
        repo.getAllUser { success, data ->
            if (success) {
                _allUsers.postValue(data)
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUser()
    }

    fun deleteUser(userId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteUser(userId, callback)
    }

    fun updateProfile(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        repo.updateProfile(userId, model, callback)
    }
}
