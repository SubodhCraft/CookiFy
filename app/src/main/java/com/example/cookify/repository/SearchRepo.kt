package com.example.cookify.repository

import com.example.cookify.model.RecipeModel

interface SearchRepo {
    fun searchRecipes(
        query: String,
        onResult: (List<RecipeModel>) -> Unit,
        onError: (String) -> Unit
    )
}