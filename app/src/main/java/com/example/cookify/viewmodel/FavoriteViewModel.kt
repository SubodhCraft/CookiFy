package com.example.cookify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookify.model.RecipeModel
import com.example.cookify.repository.FavoriteRepo

class FavoriteViewModel(private val repo: FavoriteRepo) : ViewModel() {

    private val _favorites = MutableLiveData<List<RecipeModel>>()
    val favorites: LiveData<List<RecipeModel>> = _favorites

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun toggleFavorite(userId: String, recipe: RecipeModel, callback: (Boolean, String) -> Unit) {
        repo.toggleFavorite(userId, recipe) { success, message ->
            if (success) {
                checkIfFavorite(userId, recipe.id)
            }
            callback(success, message)
        }
    }

    fun fetchFavorites(userId: String) {
        repo.getFavorites(userId) { success, data ->
            if (success && data != null) {
                _favorites.postValue(data)
            }
        }
    }

    fun checkIfFavorite(userId: String, recipeId: Int) {
        repo.isFavorite(userId, recipeId) { exists ->
            _isFavorite.postValue(exists)
        }
    }
}
