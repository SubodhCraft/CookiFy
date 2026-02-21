package com.example.cookify.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
//import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookify.model.RecipeModel
import com.example.cookify.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookify.repository.FavoriteRepoImpl
import com.example.cookify.viewmodel.FavoriteViewModel
import com.example.cookify.viewmodel.FavoriteViewModelFactory
import com.example.cookify.ui.theme.DarkGreen 

@OptIn(ExperimentalMaterial3Api::class)
class RecipeDetailActivity : ComponentActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repo = com.example.cookify.repository.FavoriteRepoImpl()
        val factory = com.example.cookify.viewmodel.FavoriteViewModelFactory(repo)
        favoriteViewModel = androidx.lifecycle.ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        val recipe = intent.getParcelableExtra<RecipeModel>("recipe")
        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser

        if (recipe != null && currentUser != null) {
            favoriteViewModel.checkIfFavorite(currentUser.uid, recipe.id)
        }

        setContent {
            if (recipe != null) {
                RecipeDetailScreen(recipe, favoriteViewModel) { finish() }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error loading recipe details")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: RecipeModel,
    viewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModelFactory(FavoriteRepoImpl())
    ),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val isFavorite by viewModel.isFavorite.observeAsState(false)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { 
                    Text(
                        recipe.title, 
                        maxLines = 1, 
                        fontSize = 20.sp, 
                        fontWeight = FontWeight.Bold 
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (currentUser != null) {
                            viewModel.toggleFavorite(currentUser.uid, recipe) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Please login to favorite recipes", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(
                            painter = painterResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.outline_favorite_border_24),
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.White
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image
            item {
                Image(
                    painter = painterResource(id = recipe.imageResId),
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Quick Stats Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickStat(icon = R.drawable.outline_timer_24, label = recipe.prepTime)
                    QuickStat(icon = R.drawable.baseline_star_24, label = "${recipe.rating} Rating")
                    // You can add calories if you want, using a generic icon or text
                    Text(
                        text = "${recipe.calories} kcal",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Description
            item {
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
                Text(
                    text = recipe.description,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    lineHeight = 22.sp
                )
            }

            // Ingredients
            item {
                Text(
                    text = "Ingredients",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
            }
            itemsIndexed(recipe.ingredients) { index, ingredient ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(DarkGreen, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = ingredient, fontSize = 15.sp)
                }
            }

            // Instructions
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Instructions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
            }
            itemsIndexed(recipe.instructions) { index, step ->
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "${index + 1}.",
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen,
                        modifier = Modifier.width(24.dp)
                    )
                    Text(
                        text = step,
                        fontSize = 15.sp,
                        color = Color.Black,
                        lineHeight = 22.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            item {
                 Spacer(modifier = Modifier.height(100.dp)) // heavy bottom padding
            }
        }
    }
}

@Composable
fun QuickStat(icon: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(icon), contentDescription = null, tint = DarkGreen, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontWeight = FontWeight.SemiBold)
    }
}

// Temporary Color definition if not imported correctly
// val DarkGreen = Color(0xFF4CAF50) 
