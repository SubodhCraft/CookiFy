package com.example.cookify

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookify.model.RecipeModel
import com.example.cookify.view.RecipeDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentInstrumentedTest {

    private val testRecipe = RecipeModel(
        id = "test_recipe_id",
        title = "Instrumented Test Recipe",
        description = "Test Description",
        ingredients = listOf("Ingredient 1"),
        instructions = listOf("Step 1"),
        authorId = "test_user_id",
        authorName = "Test Author"
    )

    @get:Rule
    val composeRule = createEmptyComposeRule()

    private fun launchRecipeDetail() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            RecipeDetailActivity::class.java
        ).apply {
            putExtra("recipe", testRecipe)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        ActivityScenario.launch<RecipeDetailActivity>(intent)
    }

    // ---------- Test: Comment UI Elements Are Visible ----------
    @Test
    fun testCommentUIElementsVisible() {
        launchRecipeDetail()

        // Wait for layout to render
        composeRule.waitUntil(15000) {
            composeRule.onAllNodesWithTag("commentInput")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Verify comment input and post button exist
        composeRule.onNodeWithTag("commentInput").assertExists()
        composeRule.onNodeWithTag("postCommentButton").assertExists()

        // Verify the comments section title exists
        composeRule.onNodeWithText("Comments", substring = true).assertExists()
    }

    // ---------- Test: Comment Input Accepts Text ----------
    @Test
    fun testCommentInput() {
        launchRecipeDetail()

        composeRule.waitUntil(15000) {
            composeRule.onAllNodesWithTag("commentInput")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Type text and verify it's entered
        composeRule.onNodeWithTag("commentInput")
            .performTextInput("This is a test comment")

        composeRule.onNodeWithTag("commentInput")
            .assertTextContains("This is a test comment")
    }

    // ---------- Test: Post Comment Button Is Clickable ----------
    @Test
    fun testPostCommentButtonClickable() {
        launchRecipeDetail()

        composeRule.waitUntil(15000) {
            composeRule.onAllNodesWithTag("commentInput")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("commentInput")
            .performTextInput("Test comment to post")

        // Click post button (may trigger toast if not logged in, but should not crash)
        composeRule.onNodeWithTag("postCommentButton").performClick()
    }

    // ---------- Test: Empty Comment Section Shows Prompt ----------
    @Test
    fun testEmptyCommentSectionShowsPrompt() {
        launchRecipeDetail()

        composeRule.waitUntil(15000) {
            composeRule.onAllNodesWithTag("commentInput")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // For a test_recipe_id that has no comments, this text should be visible
        composeRule.onNodeWithText("No comments yet", substring = true)
            .assertExists()
    }
}
