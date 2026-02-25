package com.example.cookify.repository

import com.example.cookify.model.RecipeModel
import com.google.firebase.database.*

class RecipeRepoImpl : RecipeRepo {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://cookify-individual-default-rtdb.firebaseio.com/")
    private val ref: DatabaseReference = database.getReference("Recipes")

    override fun addRecipe(recipe: RecipeModel, callback: (Boolean, String) -> Unit) {
        val id = ref.push().key ?: return callback(false, "Failed to generate ID")
        recipe.id = id
        ref.child(id).setValue(recipe).addOnCompleteListener {
            if (it.isSuccessful) callback(true, "Recipe added successfully")
            else callback(false, it.exception?.message ?: "Failed to add recipe")
        }
    }

    override fun getAllRecipes(callback: (Boolean, List<RecipeModel>?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipes = snapshot.children.mapNotNull { it.getValue(RecipeModel::class.java) }
                callback(true, recipes)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, null)
            }
        })
    }

    override fun getRecipeById(id: String, callback: (Boolean, RecipeModel?) -> Unit) {
        ref.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(true, snapshot.getValue(RecipeModel::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, null)
            }
        })
    }

    override fun updateRecipe(recipe: RecipeModel, callback: (Boolean, String) -> Unit) {
        ref.child(recipe.id).updateChildren(recipe.toMap()).addOnCompleteListener {
            if (it.isSuccessful) callback(true, "Recipe updated")
            else callback(false, it.exception?.message ?: "Update failed")
        }
    }

    override fun deleteRecipe(id: String, callback: (Boolean, String) -> Unit) {
        // 1. Delete from master Recipes node
        ref.child(id).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 2. Global Cleanup: Remove from EVERYONE'S Favorites
                val favoritesRef = database.getReference("Favorites")
                favoritesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { userNode ->
                            if (userNode.hasChild(id)) {
                                userNode.child(id).ref.removeValue()
                            }
                        }
                        callback(true, "Recipe and all its bookmarks deleted")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Even if cleanup fails, the master record is gone
                        callback(true, "Recipe deleted (Bookmark cleanup pending)")
                    }
                })
            } else {
                callback(false, task.exception?.message ?: "Delete failed")
            }
        }
    }
}
