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
import com.example.cookify.ui.theme.LightGreen
import com.example.cookify.ui.theme.DarkGreen
import com.example.cookify.ui.theme.White

// --- Shared Colors (If needed) ---
private val GreyText = Color.Black.copy(alpha = 0.6f)
// --- Data Structures ---
data class NavItem(val label: String, val icon: Int)

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

// Dummy screen composables for navigation tabs
// SearchScreen is now in its own file

@Composable 
fun FavoritesScreen() = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { 
    Text("My Favorites", fontSize = 24.sp, color = DarkGreen) 
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
fun DashboardBody() {
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
                2 -> FavoritesScreen()
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
    val recipes = listOf(
        RecipeModel(
            id = 1, 
            title = "Classic Veggie Burger", 
            description = "A juicy, protein-packed vegetarian delight that satisfies even meat lovers.", 
            prepTime = "30 min", 
            imageResId = R.drawable.burger,
            ingredients = listOf("Black beans", "Quinoa", "Breadcrumbs", "Spices", "Burger Potato Bun"),
            instructions = listOf("Mash beans and mix with cooked quinoa.", "Add spices and breadcrumbs.", "Form patties and grill for 5 mins each side.", "Assemble burger with toppings."),
            calories = 350,
            rating = 4.5
        ),
        RecipeModel(
            id = 2, 
            title = "Spicy Chicken Stir-Fry", 
            description = "Quick and savory stir-fry with a chili kick, perfect for a weeknight dinner.", 
            prepTime = "20 min", 
            imageResId = R.drawable.stirfry,
            ingredients = listOf("Chicken breast", "Bell peppers", "Soy sauce", "Chili flakes", "Rice"),
            instructions = listOf("Cut chicken into bite-sized pieces.", "Stir-fry chicken until golden.", "Add vegetables and sauce.", "Serve over steamed rice."),
            calories = 420,
            rating = 4.7
        ),
        RecipeModel(
            id = 3, 
            title = "Homemade Pizza Margherita", 
            description = "Simple, fresh, and perfect for beginners. The classic Italian taste.", 
            prepTime = "45 min", 
            imageResId = R.drawable.pizza,
            ingredients = listOf("Pizza dough", "Tomato sauce", "Mozzarella cheese", "Fresh basil", "Olive oil"),
            instructions = listOf("Roll out the dough.", "Spread tomato sauce evenly.", "Top with cheese and bake at 400°F for 15 mins.", "Garnish with fresh basil."),
            calories = 600,
            rating = 4.8
        )
    )

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

@Composable
fun RecipeCard(recipe: RecipeModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(recipe.imageResId),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = recipe.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe.description,
                        fontSize = 14.sp,
                        color = GreyText,
                        maxLines = 3
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_schedule_24),
                        contentDescription = "Preparation Time",
                        tint = LightGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = recipe.prepTime,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = GreyText
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardBody()
}