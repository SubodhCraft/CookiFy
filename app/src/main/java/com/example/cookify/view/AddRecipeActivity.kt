package com.example.cookify.view

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cookify.R
import com.example.cookify.model.RecipeModel
import com.example.cookify.repository.RecipeRepoImpl
import com.example.cookify.repository.UserRepoImpl
import com.example.cookify.ui.theme.DarkGreen
import com.example.cookify.ui.theme.White
import com.example.cookify.viewmodel.RecipeViewModel
import com.example.cookify.viewmodel.RecipeViewModelFactory
import com.example.cookify.viewmodel.UserViewModel
import com.example.cookify.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AddRecipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeToEdit = intent.getParcelableExtra<RecipeModel>("recipeToEdit")
        setContent {
            AddRecipeScreen(recipeToEdit = recipeToEdit) { finish() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(RecipeRepoImpl())),
    userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepoImpl())),
    recipeToEdit: RecipeModel? = null,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userData by userViewModel.users.observeAsState()

    var title by remember { mutableStateOf(recipeToEdit?.title ?: "") }
    var description by remember { mutableStateOf(recipeToEdit?.description ?: "") }
    var prepTime by remember { mutableStateOf(recipeToEdit?.prepTime ?: "") }
    var calories by remember { mutableStateOf(recipeToEdit?.calories?.toString() ?: "") }
    var ingredients by remember { mutableStateOf(recipeToEdit?.ingredients?.joinToString("\n") ?: "") }
    var instructions by remember { mutableStateOf(recipeToEdit?.instructions?.joinToString("\n") ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var existingImageUrl by remember { mutableStateOf(recipeToEdit?.imageUrl) }
    var isUploading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> 
        imageUri = uri
        existingImageUrl = null // New image selected
    }

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { userViewModel.getUserById(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (recipeToEdit == null) "Create New Recipe" else "Edit Recipe", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image Picker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (existingImageUrl != null) {
                    AsyncImage(
                        model = existingImageUrl,
                        contentDescription = "Existing Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(R.drawable.outline_camera_alt_24), contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                        Text("Tap to change photo", color = Color.Gray)
                    }
                }
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Title") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Quick Description") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                maxLines = 3
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = prepTime,
                    onValueChange = { prepTime = it },
                    label = { Text("Prep Time (e.g. 30 min)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text("Calories") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients (one per line)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3
            )

            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructions (one per line)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3
            )

            Button(
                onClick = {
                    if (title.isBlank() || ingredients.isBlank() || instructions.isBlank()) {
                        Toast.makeText(context, "Please fill in all mandatory fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    isUploading = true
                    val authorName = userData?.username ?: "Anonymous"
                    
                    val saveAction = { url: String? ->
                        saveOrUpdateRecipe(
                            recipeToEdit, title, description, prepTime, calories, ingredients, instructions,
                            url ?: existingImageUrl, currentUser?.uid ?: "", authorName, recipeViewModel, context, onBack
                        )
                    }

                    if (imageUri != null) {
                        userViewModel.uploadImage(context, imageUri!!) { success, imageUrl ->
                            if (success && imageUrl != null) {
                                saveAction(imageUrl)
                            } else {
                                isUploading = false
                                Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        saveAction(null)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                enabled = !isUploading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
            ) {
                if (isUploading) CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp))
                else Text(if (recipeToEdit == null) "Publish Recipe" else "Update Recipe", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun saveOrUpdateRecipe(
    recipeToEdit: RecipeModel?,
    title: String, desc: String, prep: String, cal: String, ing: String, inst: String,
    url: String?, authId: String, authName: String, viewModel: RecipeViewModel, context: android.content.Context, onBack: () -> Unit
) {
    val recipe = RecipeModel(
        id = recipeToEdit?.id ?: "",
        title = title,
        description = desc,
        prepTime = prep,
        calories = cal.toIntOrNull() ?: 0,
        imageUrl = url,
        ingredients = ing.lines().filter { it.isNotBlank() },
        instructions = inst.lines().filter { it.isNotBlank() },
        authorId = authId,
        authorName = authName,
        rating = recipeToEdit?.rating ?: 5.0
    )

    if (recipeToEdit == null) {
        viewModel.addRecipe(recipe) { success, msg ->
            if (success) {
                Toast.makeText(context, "Recipe Published!", Toast.LENGTH_SHORT).show()
                onBack()
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        viewModel.updateRecipe(recipe) { success, msg ->
            if (success) {
                Toast.makeText(context, "Recipe Updated!", Toast.LENGTH_SHORT).show()
                onBack()
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
