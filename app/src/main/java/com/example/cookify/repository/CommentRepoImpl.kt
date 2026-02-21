package com.example.cookify.repository

import com.example.cookify.model.CommentModel
import com.google.firebase.database.*

class CommentRepoImpl : CommentRepo {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://cookify-individual-default-rtdb.firebaseio.com/")
    private val ref: DatabaseReference = database.getReference("Comments")

    override fun addComment(comment: CommentModel, callback: (Boolean, String) -> Unit) {
        val commentId = ref.child(comment.recipeId).push().key ?: return
        comment.commentId = commentId
        ref.child(comment.recipeId).child(commentId).setValue(comment).addOnCompleteListener {
            if (it.isSuccessful) callback(true, "Comment added")
            else callback(false, it.exception?.message ?: "Failed to add comment")
        }
    }

    override fun getCommentsByRecipe(recipeId: String, callback: (Boolean, List<CommentModel>?) -> Unit) {
        ref.child(recipeId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val comments = snapshot.children.mapNotNull { it.getValue(CommentModel::class.java) }
                    .sortedByDescending { it.timestamp }
                callback(true, comments)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, null)
            }
        })
    }

    override fun deleteComment(commentId: String, recipeId: String, callback: (Boolean, String) -> Unit) {
        ref.child(recipeId).child(commentId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) callback(true, "Comment deleted")
            else callback(false, it.exception?.message ?: "Failed to delete comment")
        }
    }
}
