package com.example.cookify

import com.example.cookify.model.RecipeModel
import com.example.cookify.repository.FavoriteRepo
import com.example.cookify.viewmodel.FavoriteViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class FavoriteUnitTest {

    @Test
    fun toggleFavorite_success_test() {
        val repo = mock<FavoriteRepo>()
        val viewModel = FavoriteViewModel(repo)
        
        val testUserId = "user123"
        val testRecipe = RecipeModel(
            id = "1",
            title = "Test Recipe",
            description = "Test Description",
            ingredients = listOf("Test Ingredients"),
            instructions = listOf("Test Instructions"),
            imageUrl = "http://test.com/image.jpg",
            authorId = "user123",
            authorName = "Test User"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Toggled favorite")
            null
        }.`when`(repo).toggleFavorite(eq(testUserId), eq(testRecipe), any())

        var successResult = false
        var messageResult = ""

        viewModel.toggleFavorite(testUserId, testRecipe) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Toggled favorite", messageResult)
        verify(repo).toggleFavorite(eq(testUserId), eq(testRecipe), any())
    }
}
