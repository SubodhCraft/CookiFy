package com.example.cookify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val prepTime: String = "",
    val imageResId: Int = 0,
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val calories: Int = 0,
    val rating: Double = 0.0
) : Parcelable
