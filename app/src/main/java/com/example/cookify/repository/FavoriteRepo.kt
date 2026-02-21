package com.example.cookify.repository

import com.example.cookify.model.RecipeModel

interface FavoriteRepo {
    fun toggleFavorite(userId: String, recipe: RecipeModel, callback: (Boolean, String) -> Unit)
    fun getFavorites(userId: String, callback: (Boolean, List<RecipeModel>?) -> Unit)
    fun isFavorite(userId: String, recipeId: Int, callback: (Boolean) -> Unit)
}
