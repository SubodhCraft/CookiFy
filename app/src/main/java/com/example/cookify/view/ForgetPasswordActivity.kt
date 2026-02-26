package com.example.cookify.view

import androidx.compose.ui.platform.testTag

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookify.repository.UserRepoImpl
import com.example.cookify.viewmodel.UserViewModel


class ForgetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForgetBody()
        }
    }
}

@Composable
fun ForgetBody() {
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val context = LocalContext.current
    val activity = context as? Activity
    var email by remember { mutableStateOf("") }

    // Text field styling consistent with Register/Login
    val inputColors = TextFieldDefaults.colors(
        unfocusedContainerColor = LightGrayBackground,
        focusedContainerColor = LightGrayBackground,
        focusedIndicatorColor = DarkGreen,
        unfocusedIndicatorColor = Color.Transparent
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Header Section ---
            Text(
                "Reset Password",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = DarkGreen,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Enter your registered email address. We will send a secure link to your Gmail to reset your password.",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = HintColor,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // --- Input Field ---
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                placeholder = { Text("Email Address") },
                colors = inputColors,
                modifier = Modifier.fillMaxWidth().testTag("forgetEmailInput"),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(30.dp))

            // --- Action Button ---
            Button(
                onClick = {
                    if (email.isBlank()) {
                        Toast.makeText(context, "Please enter your email address.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Triggers Firebase sendPasswordResetEmail via ViewModel/Repo
                    userViewModel.forgetPassword(email) { success, message ->
                        if (success) {
                            // REAL WORLD GMAIL LINK SENT
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                            // Navigate back to LoginActivity
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)

                            // Close this activity so the user is now on the Login page
                            activity?.finish()

                        } else {
                            // Display Firebase Error (e.g. User not found)
                            Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .testTag("sendResetLinkButton"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen
                )
            ) {
                Text(
                    "Send Reset Link",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Back to Login Link ---
            Text(
                text = "Back to Login",
                color = DarkGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable() {
                    activity?.finish() // Simply closing this activity returns user to Login
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForgetPassword() {
    ForgetBody()
}