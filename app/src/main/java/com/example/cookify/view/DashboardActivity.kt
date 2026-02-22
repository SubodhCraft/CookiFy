package com.example.cookify.view

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookify.R
import com.example.cookify.model.RecipeModel
import com.example.cookify.components.RecipeCard
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookify.repository.FavoriteRepoImpl
import com.example.cookify.viewmodel.FavoriteViewModel
import com.example.cookify.viewmodel.FavoriteViewModelFactory
import com.example.cookify.ui.theme.LightGreen
import com.example.cookify.ui.theme.DarkGreen
import com.example.cookify.ui.theme.White

// --- Shared Colors (If needed) ---
private val GreyText = Color.Black.copy(alpha = 0.6f)
// --- Data Structures ---
data class NavItem(val label: String, val icon: Int)

class DashboardActivity : ComponentActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repo = com.example.cookify.repository.FavoriteRepoImpl()
        val factory = com.example.cookify.viewmodel.FavoriteViewModelFactory(repo)
        favoriteViewModel = androidx.lifecycle.ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        setContent {
            DashboardBody(favoriteViewModel)
        }
    }
}

// Dummy screen composables for navigation tabs
// SearchScreen is now in its own file

@Composable 
fun FavoritesScreen(viewModel: FavoriteViewModel) {
    val context = LocalContext.current
    val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val favorites by viewModel.favorites.observeAsState(emptyList())

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            viewModel.fetchFavorites(currentUser.uid)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEBE9))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Favorite Recipes",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkGreen,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("No favorites yet", color = Color.Gray)
            }
        } else {
            favorites.forEach { recipe ->
                RecipeCard(recipe = recipe, onClick = {
                    val intent = Intent(context, RecipeDetailActivity::class.java)
                    intent.putExtra("recipe", recipe)
                    context.startActivity(intent)
                })
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable 
fun ProfileScreen() {
    val context = LocalContext.current
    val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Image Placeholder
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(DarkGreen, shape = androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_person_24),
                contentDescription = "Profile",
                tint = White,
                modifier = Modifier.size(50.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User Email
        Text(
            text = currentUser?.email ?: "Guest User",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Verification Status
        if (currentUser != null && !currentUser.isEmailVerified) {
            Text(
                text = "Email not verified",
                fontSize = 14.sp,
                color = Color.Red
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Options Cards
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Account Information",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "User ID: ${currentUser?.uid?.take(12) ?: "N/A"}...",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Logout Button
        Button (
            onClick = {
                // Sign out from Firebase
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                
                // Navigate back to Login and clear the back stack
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Logout",
                tint = White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "CookiFy v1.0",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBody(
    favoriteViewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModelFactory(FavoriteRepoImpl())
    )
) {
    val context = LocalContext.current

    // Updated navigation items for a recipe app
    val listNav = listOf(
        NavItem(label = "Home", icon = R.drawable.outline_home_24),
        NavItem(label = "Search", icon = R.drawable.outline_search_24),
        NavItem(label = "Favorites", icon = R.drawable.outline_favorite_24),
        NavItem(label = "Profile", icon = R.drawable.outline_person_24)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { TopAppBarContent(context) },
        bottomBar = { BottomNavBar(listNav, selectedIndex) { index -> selectedIndex = index } }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when(selectedIndex){
                0 -> HomeScreenContent()
                1 -> SearchScreen()
                2 -> FavoritesScreen(favoriteViewModel)
                3 -> ProfileScreen()
                else -> HomeScreenContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(context: android.content.Context) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkGreen,
            titleContentColor = White,
            actionIconContentColor = White,
        ),
        title = { Text("Recipe Finder", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
        navigationIcon = {
            IconButton(onClick = {
                (context as? ComponentActivity)?.finish()
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
fun BottomNavBar(listNav: List<NavItem>, selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = White,
        contentColor = DarkGreen
    ) {
        listNav.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(item.label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                },
                onClick = { onItemSelected(index) },
                selected = selectedIndex == index,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = White,
                    selectedTextColor = DarkGreen,
                    indicatorColor = LightGreen 
                )
            )
        }
    }
}

@Composable
fun HomeScreenContent() {
    val context = LocalContext.current

    // Predefined Recipe Data using RecipeModel
    val recipes = com.example.cookify.utils.RecipeData.allRecipes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEBE9))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Today's Featured Recipes",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkGreen,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        recipes.forEach { recipe ->
            RecipeCard(recipe = recipe, onClick = {
                val intent = Intent(context, RecipeDetailActivity::class.java)
                intent.putExtra("recipe", recipe)
                context.startActivity(intent)
            })
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardBody()
}