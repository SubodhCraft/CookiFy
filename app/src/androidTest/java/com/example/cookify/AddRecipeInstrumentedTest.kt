package com.example.cookify

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookify.view.AddRecipeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddRecipeInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<AddRecipeActivity>()

    @Test
    fun testAddRecipeUI() {
        composeRule.onNodeWithTag("recipeTitleInput").assertExists()
        composeRule.onNodeWithTag("recipeIngredientsInput").assertExists()
        composeRule.onNodeWithTag("recipeInstructionsInput").assertExists()
        composeRule.onNodeWithTag("publishRecipeButton").assertExists()
    }

    @Test
    fun testAddRecipeInput() {
        composeRule.onNodeWithTag("recipeTitleInput").performTextInput("Spiced Tea")
        composeRule.onNodeWithTag("recipeIngredientsInput").performTextInput("Tea leaves\nWater\nSpices")
        composeRule.onNodeWithTag("recipeInstructionsInput").performTextInput("Boil water\nAdd tea\nAdd spices")
        
        // Use performScrollTo to ensure the button is in view
        composeRule.onNodeWithTag("publishRecipeButton").performScrollTo().performClick()
    }
}
