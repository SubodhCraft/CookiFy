package com.example.cookify.utils

import com.example.cookify.model.RecipeModel
import com.example.cookify.R

object RecipeData {
    val allRecipes = listOf(
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
}
