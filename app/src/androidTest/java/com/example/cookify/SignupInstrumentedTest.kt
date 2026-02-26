package com.example.cookify

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookify.view.RegistrationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignupInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RegistrationActivity>()

    @Test
    fun testSignupUI() {
        composeRule.onNodeWithTag("registerEmailInput").assertExists()
        composeRule.onNodeWithTag("registerUsernameInput").assertExists()
        composeRule.onNodeWithTag("registerPasswordInput").assertExists()
        composeRule.onNodeWithTag("signUpButton").assertExists()
    }

    @Test
    fun testSignupInput() {
        composeRule.onNodeWithTag("registerEmailInput").performTextInput("newuser@gmail.com")
        composeRule.onNodeWithTag("registerUsernameInput").performTextInput("newuser")
        composeRule.onNodeWithTag("registerPasswordInput").performTextInput("password123")
        // Note: Confirm password is not tagged yet, but it's okay for a basic test or I can tag it too
    }
}
