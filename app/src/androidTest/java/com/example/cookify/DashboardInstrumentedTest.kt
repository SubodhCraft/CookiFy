package com.example.cookify

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookify.view.DashboardActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<DashboardActivity>()

    @Test
    fun testNavigationVisible() {
        composeRule.onNodeWithTag("nav_Home", useUnmergedTree = true).assertExists()
        composeRule.onNodeWithTag("nav_Saved", useUnmergedTree = true).assertExists()
        composeRule.onNodeWithTag("nav_Search", useUnmergedTree = true).assertExists()
        composeRule.onNodeWithTag("nav_Profile", useUnmergedTree = true).assertExists()
    }

    @Test
    fun testAddRecipeFAB() {
        composeRule.onNodeWithTag("addRecipeFAB", useUnmergedTree = true).assertExists()
        composeRule.onNodeWithTag("addRecipeFAB", useUnmergedTree = true).performClick()
    }
}
