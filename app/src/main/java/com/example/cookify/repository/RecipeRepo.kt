package com.example.cookify.repository

import com.example.cookify.model.RecipeModel

interface RecipeRepo {
    fun addRecipe(recipe: RecipeModel, callback: (Boolean, String) -> Unit)
    fun getAllRecipes(callback: (Boolean, List<RecipeModel>?) -> Unit)
    fun getRecipeById(id: String, callback: (Boolean, RecipeModel?) -> Unit)
    fun updateRecipe(recipe: RecipeModel, callback: (Boolean, String) -> Unit)
    fun deleteRecipe(id: String, callback: (Boolean, String) -> Unit)
}
