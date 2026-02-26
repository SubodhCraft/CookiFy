package com.example.cookify

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cookify.model.RecipeModel
import com.example.cookify.repository.RecipeRepo
import com.example.cookify.viewmodel.RecipeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RecipeUnitTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addRecipe_success_test() {
        val repo = mock<RecipeRepo>()
        val viewModel = RecipeViewModel(repo)
        
        val testRecipe = RecipeModel(
            id = "1",
            title = "Test Recipe",
            description = "Test Description",
            ingredients = listOf("Ingredient 1", "Ingredient 2"),
            instructions = listOf("Step 1", "Step 2"),
            imageUrl = "http://test.com/image.jpg",
            authorId = "user123",
            authorName = "Test User"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Recipe added successfully")
            null
        }.`when`(repo).addRecipe(eq(testRecipe), any())

        var successResult = false
        var messageResult = ""

        viewModel.addRecipe(testRecipe) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Recipe added successfully", messageResult)
        verify(repo).addRecipe(eq(testRecipe), any())
    }

    @Test
    fun deleteRecipe_success_test() {
        val repo = mock<RecipeRepo>()
        val viewModel = RecipeViewModel(repo)
        val recipeId = "1"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Recipe deleted")
            null
        }.`when`(repo).deleteRecipe(eq(recipeId), any())

        var successResult = false
        var messageResult = ""

        viewModel.deleteRecipe(recipeId) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Recipe deleted", messageResult)
        verify(repo).deleteRecipe(eq(recipeId), any())
    }

    @Test
    fun updateRecipe_success_test() {
        val repo = mock<RecipeRepo>()
        val viewModel = RecipeViewModel(repo)
        
        val updatedRecipe = RecipeModel(
            id = "1",
            title = "Updated Recipe",
            description = "Updated Description",
            ingredients = listOf("Ingredient 1", "Updated Ingredient"),
            instructions = listOf("Updated Step 1", "Step 2"),
            imageUrl = "http://test.com/updated.jpg",
            authorId = "user123",
            authorName = "Test User"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Recipe updated successfully")
            null
        }.`when`(repo).updateRecipe(eq(updatedRecipe), any())

        var successResult = false
        var messageResult = ""

        viewModel.updateRecipe(updatedRecipe) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Recipe updated successfully", messageResult)
        verify(repo).updateRecipe(eq(updatedRecipe), any())
    }
}
