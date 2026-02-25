package com.example.cookify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookify.model.RecipeModel
import com.example.cookify.repository.RecipeRepo

class RecipeViewModel(private val repo: RecipeRepo) : ViewModel() {

    private val _recipes = MutableLiveData<List<RecipeModel>>()
    val recipes: LiveData<List<RecipeModel>> = _recipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchAllRecipes()
    }

    fun fetchAllRecipes() {
        _isLoading.value = true
        repo.getAllRecipes { success, data ->
            _isLoading.value = false
            if (success && data != null) {
                _recipes.postValue(data)
            }
        }
    }

    fun addRecipe(recipe: RecipeModel, callback: (Boolean, String) -> Unit) {
        repo.addRecipe(recipe, callback)
    }

    fun deleteRecipe(id: String, callback: (Boolean, String) -> Unit) {
        repo.deleteRecipe(id, callback)
    }
}
