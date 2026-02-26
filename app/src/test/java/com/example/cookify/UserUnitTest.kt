package com.example.cookify

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cookify.repository.UserRepo
import com.example.cookify.viewmodel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UserUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Test
    fun login_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val testEmail = "test@gmail.com"
        val testPass = "password123"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Login success")
            null
        }.`when`(repo).login(eq(testEmail), eq(testPass), any())

        var successResult = false
        var messageResult = ""

        viewModel.login(testEmail, testPass) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Login success", messageResult)
        verify(repo).login(eq(testEmail), eq(testPass), any())
    }

    @Test
    fun forgetPassword_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val testEmail = "test@gmail.com"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Reset link sent")
            null
        }.`when`(repo).forgetPassword(eq(testEmail), any())

        var successResult = false
        var messageResult = ""

        viewModel.forgetPassword(testEmail) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Reset link sent", messageResult)
        verify(repo).forgetPassword(eq(testEmail), any())
    }
}
