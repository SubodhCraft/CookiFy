package com.example.cookify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val prepTime: String = "",
    val imageUrl: String? = null,
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val calories: Int = 0,
    val rating: Double = 0.0,
    val authorId: String = "",
    val authorName: String = ""
) : Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "description" to description,
            "prepTime" to prepTime,
            "imageUrl" to imageUrl,
            "ingredients" to ingredients,
            "instructions" to instructions,
            "calories" to calories,
            "rating" to rating,
            "authorId" to authorId,
            "authorName" to authorName
        )
    }
}
