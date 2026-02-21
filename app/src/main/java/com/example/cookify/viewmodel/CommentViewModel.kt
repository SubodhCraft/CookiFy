package com.example.cookify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookify.model.CommentModel
import com.example.cookify.repository.CommentRepo

class CommentViewModel(private val repo: CommentRepo) : ViewModel() {

    private val _comments = MutableLiveData<List<CommentModel>>()
    val comments: LiveData<List<CommentModel>> = _comments

    fun addComment(comment: CommentModel, callback: (Boolean, String) -> Unit) {
        repo.addComment(comment, callback)
    }

    fun fetchComments(recipeId: String) {
        repo.getCommentsByRecipe(recipeId) { success, data ->
            if (success && data != null) {
                _comments.postValue(data)
            }
        }
    }

    fun deleteComment(commentId: String, recipeId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteComment(commentId, recipeId, callback)
    }
}
