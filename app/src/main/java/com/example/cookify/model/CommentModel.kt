package com.example.cookify.model

data class CommentModel(
    var commentId: String = "",
    val userId: String = "",
    val userName: String = "",
    val recipeId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
