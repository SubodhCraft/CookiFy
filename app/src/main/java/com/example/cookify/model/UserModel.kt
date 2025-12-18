package com.example.cookify.model

data class UserModel(
    val userId: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dob: String = "",
    val contact: String = "",
    val username: String = "" // <--- ADD THIS LINE
){
    fun toMap() : Map<String,Any?>{
        return mapOf(
            "contact" to contact
        )
    }
}
