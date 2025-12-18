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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.cookify.R
import com.example.cookify.model.UserModel
//import com.example.cookify.model.UserModel
import com.example.cookify.repository.UserRepoImpl
import com.example.cookify.ui.theme.DarkGreen
import com.example.cookify.ui.theme.LightGrayBackground
import com.example.cookify.ui.theme.White
import com.example.cookify.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterBody()
        }
    }
}

@Composable
fun RegisterBody() {
    // --- State Management ---
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var terms by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as? Activity
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }

    // --- UI Styling ---
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
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // --- Header ---
                Text(
                    "Create Your Account",
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = DarkGreen,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    "Join our community of chefs!",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- Form Fields ---
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text("Email Address") },
                    colors = inputColors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("Username") },
                    colors = inputColors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painter = painterResource(if (passwordVisibility) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                                contentDescription = null,
                                tint = DarkGreen
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = inputColors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { Text("Confirm Password") },
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        }) {
                            Icon(
                                painter = painterResource(if (confirmPasswordVisibility) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                                contentDescription = null,
                                tint = DarkGreen
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = inputColors,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // --- Terms and Conditions ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = terms,
                        onCheckedChange = { terms = it },
                        colors = CheckboxDefaults.colors(checkedColor = DarkGreen)
                    )
                    Text("I agree to the Terms & Conditions", fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Sign Up Button with Real Gmail Logic ---
                Button(
                    onClick = {
                        when {
                            email.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please fill all fields.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            password != confirmPassword -> {
                                Toast.makeText(
                                    context,
                                    "Passwords do not match.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            !terms -> {
                                Toast.makeText(
                                    context,
                                    "Please agree to the Terms & Conditions.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                userViewModel.register(
                                    email,
                                    password
                                ) { success, message, userId ->
                                    if (success) {
                                        val model = UserModel(
                                            userId = userId,
                                            email = email,
                                            username = username,
                                            firstName = "", lastName = "", dob = "", contact = ""
                                        )
                                        userViewModel.addUserToDatabase(
                                            userId,
                                            model
                                        ) { dbSuccess, dbMessage ->
                                            if (dbSuccess) {
                                                // --- SEND REAL VERIFICATION EMAIL ---
                                                val firebaseUser =
                                                    FirebaseAuth.getInstance().currentUser
                                                firebaseUser?.sendEmailVerification()
                                                    ?.addOnCompleteListener { task ->
                                                        if (task.isSuccessful) {
                                                            Toast.makeText(
                                                                context,
                                                                "Verification email sent to $email",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                        }
                                                    }

                                                Toast.makeText(
                                                    context,
                                                    "Registration Successful!",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Navigate to Login
                                                val intent =
                                                    Intent(context, LoginActivity::class.java)
                                                context.startActivity(intent)
                                                activity?.finish()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    dbMessage,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = White)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Footer Navigation ---
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(SpanStyle(color = DarkGreen, fontWeight = FontWeight.Bold)) {
                            append("Sign In")
                        }
                    },
                    modifier = Modifier.clickable {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        activity?.finish()
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister(){
    RegisterBody()
}
