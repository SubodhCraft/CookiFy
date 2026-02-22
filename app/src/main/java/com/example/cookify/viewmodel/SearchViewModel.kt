package com.example.cookify.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cookify.model.RecipeModel
import com.example.cookify.utils.RecipeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {

    // UI States
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<RecipeModel>>(emptyList())
    val searchResults: StateFlow<List<RecipeModel>> = _searchResults

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        // Optionally clear results when query is empty
        if (newQuery.isEmpty()) {
            _searchResults.value = emptyList()
        }
    }

    fun performSearch(query: String, onEmpty: () -> Unit, onError: (String) -> Unit) {
        if (query.isBlank()) {
            onEmpty()
            return
        }

        // Static filtering logic
        val filteredResults = RecipeData.allRecipes.filter {
            it.title.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true)
        }

        if (filteredResults.isEmpty()) {
            _searchResults.value = emptyList()
            onError("No recipes found for '$query'")
        } else {
            _searchResults.value = filteredResults
            addToHistory(query)
        }
    }

    private fun addToHistory(query: String) {
        val currentHistory = _recentSearches.value.toMutableList()
        if (!currentHistory.contains(query)) {
            currentHistory.add(0, query)
            _recentSearches.value = currentHistory.take(5) // Keep last 5
        }
    }
}