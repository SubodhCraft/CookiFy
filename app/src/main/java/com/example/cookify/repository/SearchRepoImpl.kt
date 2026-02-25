package com.example.cookify.repository

import com.example.cookify.model.RecipeModel
import com.google.firebase.database.*

class SearchRepoImpl : SearchRepo {
    // Reference to the "Recipes" node in Firebase
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://cookify-individual-default-rtdb.firebaseio.com/")
    private val ref: DatabaseReference = database.getReference("Recipes")

    override fun searchRecipes(
        query: String,
        onResult: (List<RecipeModel>) -> Unit,
        onError: (String) -> Unit
    ) {
        // Fetch all recipes and filter locally for better matching (not just prefix)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val recipes = snapshot.children.mapNotNull { child ->
                        try {
                            child.getValue(RecipeModel::class.java)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    
                    // Filter based on query
                    val filtered = if (query.isEmpty()) {
                        recipes
                    } else {
                        recipes.filter { 
                            it.title.contains(query, ignoreCase = true) || 
                            it.description.contains(query, ignoreCase = true)
                        }
                    }
                    onResult(filtered)
                } catch (e: Exception) {
                    onError(e.message ?: "Search failed")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        })
    }
}