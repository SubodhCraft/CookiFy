package com.example.cookify.repository

import com.example.cookify.model.CommentModel

interface CommentRepo {
    fun addComment(comment: CommentModel, callback: (Boolean, String) -> Unit)
    fun getCommentsByRecipe(recipeId: String, callback: (Boolean, List<CommentModel>?) -> Unit)
    fun deleteComment(commentId: String, recipeId: String, callback: (Boolean, String) -> Unit)
    fun updateComment(comment: CommentModel, callback: (Boolean, String) -> Unit)
}
