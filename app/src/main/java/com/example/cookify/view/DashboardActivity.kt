package com.example.cookify.view
//
//import android.app.Activity
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import com.example.cookify.ui.theme.Blue
//import com.example.cookify.ui.theme.White
//import com.example.cookify.HomeScreen
//import com.example.cookify.MoreScreen
//import com.example.cookify.NotificationScreen
//import com.example.cookify.R
//import com.example.cookify.SearchScreen
//
//class DashboardActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            DashboardBody()
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DashboardBody() {
//
//    val context = LocalContext.current
//    val activity = context as Activity
//
//    val email = activity.intent.getStringExtra("email")
//    val password = activity.intent.getStringExtra("password")
//
//    data class NavItem(val label: String, val icon: Int)
//
//    var selectedIndex by remember { mutableStateOf(0) }
//
//    var listNav = listOf(
//        NavItem(
//            label = "Home",
//            icon = R.drawable.baseline_home_24,
//        ),
//        NavItem(
//            label = "Search",
//            icon = R.drawable.baseline_search_24,
//        ),
//        NavItem(
//            label = "Notification",
//            icon = R.drawable.baseline_notifications_none_24,
//        ),
//        NavItem(
//            label = "More",
//            icon = R.drawable.baseline_view_module_24,
//        ),
//
//        )
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Blue,
//                    actionIconContentColor = White,
//                    titleContentColor = White,
//                    navigationIconContentColor = White
//                ),
//                title = { Text("Dashboard") },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        activity.finish()
//                    }) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_arrow_back_24),
//                            contentDescription = null
//                        )
//                    }
//                },
//
//                actions = {
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_more_horiz_24),
//                            contentDescription = null
//                        )
//                    }
//
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_more_horiz_24),
//                            contentDescription = null
//                        )
//                    }
//
//
//                }
//            )
//        },
//        bottomBar = {
//            NavigationBar {
//                listNav.forEachIndexed { index,item->
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                painter = painterResource(item.icon),
//                                contentDescription = null
//                            )
//                        },
//                        label = {
//                            Text(item.label)
//                        },
//                        onClick = {
//                            selectedIndex = index
//                        },
//                        selected = selectedIndex == index
//                    )
//                }
//            }
//        }
//    ) { padding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            when(selectedIndex){
//                0-> HomeScreen()
//                1-> SearchScreen()
//                2-> NotificationScreen()
//                3-> MoreScreen()
//                else -> HomeScreen()
//            }
//        }
//    }
//}


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
import com.example.cookify.R // Assuming R file is updated
import com.example.cookify.ui.theme.LightGreen

// --- Recipe Finder Theme Colors (Consistent with Login/Forget) --- )
val GreyText = Color.Black.copy(alpha = 0.6f)

// --- Data Structures ---
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val prepTime: String,
    val imageResId: Int
)

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
@Composable fun SearchScreen() = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Search Recipes", fontSize = 24.sp, color = DarkGreen) }
@Composable fun FavoritesScreen() = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("My Favorites", fontSize = 24.sp, color = DarkGreen) }
@Composable fun ProfileScreen() = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Profile Settings", fontSize = 24.sp, color = DarkGreen) }
// Note: We changed "Notification" and "More" to "Favorites" and "Profile" for a typical app flow.

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

// --- Top App Bar Composable ---
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
                // Return to Login Screen (or previous activity)
                (context as? ComponentActivity)?.finish()
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back to Login"
                )
            }
        },
        actions = {
            // Placeholder for 'Add Recipe' action (if users can submit recipes)
//            IconButton(onClick = {
//                // NOTE: You must create AddRecipeActivity
//                Toast.makeText(context, "Adding a new recipe...", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, AddRecipeActivity::class.java)
//                context.startActivity(intent)
//            }) {
//                // Assuming R.drawable.baseline_add_24 exists
//                Icon(
//                    painter = painterResource(R.drawable.baseline_add_24),
//                    contentDescription = "Add Recipe"
//                )
//            }
        }
    )
}

// --- Bottom Navigation Bar Composable ---
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
                    indicatorColor = LightGreen // Green background for selected item
                )
            )
        }
    }
}

// --- Home Screen Content (Recipes) ---
@Composable
fun HomeScreenContent() {
    val context = LocalContext.current

    // Predefined Recipe Data
    val recipes = listOf(
        Recipe(1, "Classic Veggie Burger", "A juicy, protein-packed vegetarian delight.", "30 min", R.drawable.burger),
        Recipe(2, "Spicy Chicken Stir-Fry", "Quick and savory stir-fry with a chili kick.", "20 min", R.drawable.stirfry),
        Recipe(3, "Homemade Pizza Margherita", "Simple, fresh, and perfect for beginners.", "45 min", R.drawable.pizza)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEBE9)) // Soft background for the list
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
                Toast.makeText(context, "Clicked: ${recipe.title}", Toast.LENGTH_SHORT).show()
                // TODO: Navigate to RecipeDetailActivity
            })
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

// --- Recipe Card Composable ---
@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
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
            // Image Section
            Image(
                painter = painterResource(recipe.imageResId), // Ensure these drawables exist
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            // Content Section
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
                    // Time Icon (Assuming R.drawable.outline_schedule_24 exists)
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