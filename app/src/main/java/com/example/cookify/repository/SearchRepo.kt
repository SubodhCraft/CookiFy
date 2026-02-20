package com.example.cookify.repository

import com.example.cookify.model.RecipeSearch

interface SearchRepo {

        fun searchRecipes(
            query: String,
            onResult: (List<RecipeSearch>) -> Unit,
            onError: (String) -> Unit
        )
}