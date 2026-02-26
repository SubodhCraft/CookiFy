package com.example.cookify

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookify.view.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginActivity>()

    @Test
    fun testLoginUIElementsVisible() {
        composeRule.onNodeWithTag("emailInput").assertExists()
        composeRule.onNodeWithTag("passwordInput").assertExists()
        composeRule.onNodeWithTag("loginButton").assertExists()
    }

    @Test
    fun testLoginInput() {
        composeRule.onNodeWithTag("emailInput").performTextInput("test@gmail.com")
        composeRule.onNodeWithTag("passwordInput").performTextInput("password123")
        composeRule.onNodeWithTag("loginButton").performClick()
    }
}
