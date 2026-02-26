package com.example.cookify

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cookify.model.CommentModel
import com.example.cookify.repository.CommentRepo
import com.example.cookify.viewmodel.CommentViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CommentUnitTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addComment_success_test() {
        val repo = mock<CommentRepo>()
        val viewModel = CommentViewModel(repo)
        
        val testComment = CommentModel(
            commentId = "c1",
            recipeId = "r1",
            userId = "u1",
            userName = "User 1",
            content = "Great recipe!",
            timestamp = System.currentTimeMillis()
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Comment added")
            null
        }.`when`(repo).addComment(eq(testComment), any())

        var successResult = false
        var messageResult = ""

        viewModel.addComment(testComment) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Comment added", messageResult)
        verify(repo).addComment(eq(testComment), any())
    }

    @Test
    fun deleteComment_success_test() {
        val repo = mock<CommentRepo>()
        val viewModel = CommentViewModel(repo)
        val commentId = "c1"
        val recipeId = "r1"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Comment deleted")
            null
        }.`when`(repo).deleteComment(eq(commentId), eq(recipeId), any())

        var successResult = false
        var messageResult = ""

        viewModel.deleteComment(commentId, recipeId) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Comment deleted", messageResult)
        verify(repo).deleteComment(eq(commentId), eq(recipeId), any())
    }

    @Test
    fun updateComment_success_test() {
        val repo = mock<CommentRepo>()
        val viewModel = CommentViewModel(repo)
        
        val testComment = CommentModel(
            commentId = "c1",
            recipeId = "r1",
            userId = "u1",
            userName = "User 1",
            content = "Updated content",
            timestamp = System.currentTimeMillis()
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Comment updated")
            null
        }.`when`(repo).updateComment(eq(testComment), any())

        var successResult = false
        var messageResult = ""

        viewModel.updateComment(testComment) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Comment updated", messageResult)
        verify(repo).updateComment(eq(testComment), any())
    }
}
