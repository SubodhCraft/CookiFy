package com.example.cookify.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cookify.model.RecipeModel
import com.example.cookify.repository.SearchRepo
import com.example.cookify.utils.RecipeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(private val repo: SearchRepo) : ViewModel() {

    // UI States
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<RecipeModel>>(emptyList())
    val searchResults: StateFlow<List<RecipeModel>> = _searchResults

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        if (newQuery.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            searchLocallyAndRemotely(newQuery)
        }
    }

    private fun searchLocallyAndRemotely(query: String) {
        _isLoading.value = true
        
        // 1. Get static results
        val staticResults = RecipeData.allRecipes.filter {
            it.title.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true)
        }

        // 2. Get remote results from Firebase
        repo.searchRecipes(query, 
            onResult = { remoteResults ->
                // Combine and remove duplicates by title
                val combined = (staticResults + remoteResults).distinctBy { it.title }
                _searchResults.value = combined
                _isLoading.value = false
            },
            onError = {
                // If remote fails, still show static
                _searchResults.value = staticResults
                _isLoading.value = false
            }
        )
    }

    fun performSearch(query: String, onEmpty: () -> Unit, onError: (String) -> Unit) {
        if (query.isBlank()) {
            onEmpty()
            return
        }

        searchLocallyAndRemotely(query)
        addToHistory(query)
    }

    private fun addToHistory(query: String) {
        val currentHistory = _recentSearches.value.toMutableList()
        if (!currentHistory.contains(query)) {
            currentHistory.add(0, query)
            _recentSearches.value = currentHistory.take(5) // Keep last 5
        }
    }
}