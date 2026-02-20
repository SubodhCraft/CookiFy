package com.example.cookify.repository

import com.example.cookify.model.RecipeSearch
import com.google.firebase.database.*

class SearchRepoImpl : SearchRepo {
    // Reference to the "recipes" node in Firebase
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("recipes")

    override fun searchRecipes(
        query: String,
        onResult: (List<RecipeSearch>) -> Unit,
        onError: (String) -> Unit
    ) {
        // Firebase prefix search logic
        // \uf8ff is a high-range Unicode character used to search for
        // any string starting with the provided query.
        database.orderByChild("name")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val recipes = snapshot.children.mapNotNull {
                        it.getValue(RecipeSearch::class.java)
                    }
                    onResult(recipes)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error.message)
                }
            })
    }
}