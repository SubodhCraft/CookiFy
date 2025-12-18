package com.example.cookify.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookify.R // Assuming R file is updated
import com.example.cookify.repository.UserRepoImpl
import com.example.cookify.viewmodel.UserViewModel

// Re-defining the colors for the Recipe App theme (ensure these are in your theme file)
val DarkGreen = Color(0xFF4CAF50) // Primary action color
val LightGrayBackground = Color(0xFFF5F5F5) // Background for text fields
val White = Color.White
val HintColor = Color.Black.copy(alpha = 0.5f)

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody() {

    val keyCo = LocalSoftwareKeyboardController.current

    var emailOrUsername by remember { mutableStateOf("") } // Renamed for clarity
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Removed the unused 'showDialog' state

    // Helper function for text field colors
    val inputColors = TextFieldDefaults.colors(
        unfocusedContainerColor = LightGrayBackground,
        focusedContainerColor = LightGrayBackground,
        focusedIndicatorColor = DarkGreen,
        unfocusedIndicatorColor = Color.Transparent
    )
    Box(modifier = Modifier.fillMaxSize()) {
        // --- BACKGROUND IMAGE ---
        Image(
            painter = painterResource(id = R.drawable.bgforlogin), // Replace with your image name
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f), // ADJUST OPACITY HERE (0.0 to 1.0)
            contentScale = ContentScale.Crop
        )
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(White)
                    .verticalScroll(rememberScrollState()) // Allow scrolling for smaller devices
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // --- Title ---
                Text(
                    "Welcome Back!",
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = DarkGreen,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // --- Subtitle (Modified for Recipe App) ---
                Text(
                    "Find your next delicious recipe in seconds.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = HintColor,
                        fontSize = 16.sp
                    )
                )

                // --- Social Media Cards ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
//                SocialMediaCard(
//                    Modifier.weight(1f).height(55.dp),
//                    R.drawable.face, // Ensure R.drawable.face is Facebook icon
//                    "Facebook"
//                )
                    Spacer(modifier = Modifier.width(16.dp))

//                SocialMediaCard(
//                    Modifier.weight(1f).height(55.dp),
//                    R.drawable.gmail, // Ensure R.drawable.gmail is Google icon
//                    "Google"
//                )
                }


                // --- OR Divider ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                HorizontalDivider(
//                    modifier = Modifier.weight(1f),
//                    color = LightGrayBackground
//                )
//
//                Text(
//                    " OR ",
//                    modifier = Modifier.padding(horizontal = 20.dp),
//                    color = HintColor
//                )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = LightGrayBackground
                    )
                }

                // --- 1. Email/Username Field ---
                OutlinedTextField(
                    value = emailOrUsername,
                    onValueChange = { data -> emailOrUsername = data },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text("Email or Username") },
                    colors = inputColors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                // --- 2. Password Field ---
                OutlinedTextField(
                    value = password,
                    onValueChange = { data -> password = data },
                    placeholder = { Text("Password") },
                    trailingIcon = {
                        IconButton(onClick = { visibility = !visibility }) {
                            // Assuming you have these drawable resources
                            Icon(
                                painter = painterResource(if (visibility) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                                contentDescription = "Toggle password visibility",
                                tint = DarkGreen
                            )
                        }
                    },
                    visualTransformation = if (!visibility) PasswordVisualTransformation() else VisualTransformation.None,
                    colors = inputColors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // --- Forgot Password Link ---
                Text(
                    "Forgot Password?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Navigate to Forgot Password screen
                            // NOTE: You must create ForgetPasswordActivity
                            val intent = Intent(context, ForgetPasswordActivity::class.java)
                            context.startActivity(intent)
                        },
                    style = TextStyle(
                        textAlign = TextAlign.End,
                        color = DarkGreen,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                // --- Log In Button ---
                Button(
                    onClick = {
                        keyCo?.hide()
                        // Basic validation check
                        if (emailOrUsername.isBlank() || password.isBlank()) {
                            Toast.makeText(
                                context,
                                "Please enter email/username and password.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            userViewModel.login(emailOrUsername, password) { success, message ->
                                if (success) {
                                    // NOTE: You must create DashboardActivity
                                    val intent = Intent(context, DashboardActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text(
                        "Log In",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // --- Don't have account link ---
                Text(
                    buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(SpanStyle(color = DarkGreen, fontWeight = FontWeight.Bold)) {
                            append("Sign up")
                        }
                    },
                    modifier = Modifier.clickable {
                        // Navigate to Registration Activity
                        // NOTE: You must create RegistrationActivity
                        val intent = Intent(context, RegistrationActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp)) // Final padding
            }
        }
    }


    @Composable
    fun SocialMediaCard(modifier: Modifier, image: Int, label: String) {
        Card(
            modifier = modifier
                .clickable { /* TODO: Implement social login logic */ },
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Assuming image resources (face/gmail) are updated and available
                Image(
                    painter = painterResource(image),
                    contentDescription = "$label login",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    label,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginBody()
}
