package com.example.cookify

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookify.view.ForgetPasswordActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForgetPasswordInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ForgetPasswordActivity>()

    @Test
    fun testForgetPasswordUI() {
        composeRule.onNodeWithTag("forgetEmailInput").assertExists()
        composeRule.onNodeWithTag("sendResetLinkButton").assertExists()
    }

    @Test
    fun testForgetPasswordInput() {
        composeRule.onNodeWithTag("forgetEmailInput").performTextInput("test@gmail.com")
        composeRule.onNodeWithTag("sendResetLinkButton").performClick()
    }
}
