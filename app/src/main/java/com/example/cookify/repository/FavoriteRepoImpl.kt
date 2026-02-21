package com.example.cookify.repository

import com.example.cookify.model.RecipeModel
import com.google.firebase.database.*

class FavoriteRepoImpl : FavoriteRepo {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://cookify-individual-default-rtdb.firebaseio.com/")
    private val ref: DatabaseReference = database.getReference("Favorites")

    override fun toggleFavorite(userId: String, recipe: RecipeModel, callback: (Boolean, String) -> Unit) {
        val favRef = ref.child(userId).child(recipe.id.toString())
        favRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Remove if already favorite
                    favRef.removeValue().addOnCompleteListener {
                        if (it.isSuccessful) callback(true, "Removed from favorites")
                        else callback(false, it.exception?.message ?: "Failed to remove")
                    }
                } else {
                    // Add to favorites
                    favRef.setValue(recipe).addOnCompleteListener {
                        if (it.isSuccessful) callback(true, "Added to favorites")
                        else callback(false, it.exception?.message ?: "Failed to add")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message)
            }
        })
    }

    override fun getFavorites(userId: String, callback: (Boolean, List<RecipeModel>?) -> Unit) {
        ref.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favorites = snapshot.children.mapNotNull { it.getValue(RecipeModel::class.java) }
                callback(true, favorites)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, null)
            }
        })
    }

    override fun isFavorite(userId: String, recipeId: Int, callback: (Boolean) -> Unit) {
        ref.child(userId).child(recipeId.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }
}
