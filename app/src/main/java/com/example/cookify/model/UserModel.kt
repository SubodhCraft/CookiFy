package com.example.cookify.model

data class UserModel(
    val userId: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dob: String = "",
    val contact: String = "",
    val username: String = "",
    val profileImageUrl: String? = null
){
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName,
            "dob" to dob,
            "contact" to contact,
            "username" to username,
            "profileImageUrl" to profileImageUrl
        )
    }
}
