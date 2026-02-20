package com.example.cookify.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cookify.model.RecipeSearch
import com.example.cookify.repository.SearchRepo
import com.example.cookify.repository.SearchRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {
    private val repository: SearchRepo = SearchRepoImpl()

    // UI States
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<RecipeSearch>>(emptyList())
    val searchResults: StateFlow<List<RecipeSearch>> = _searchResults

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun performSearch(query: String, onEmpty: () -> Unit, onError: (String) -> Unit) {
        if (query.isBlank()) {
            onEmpty()
            return
        }

        repository.searchRecipes(query,
            onResult = { results ->
                if (results.isEmpty()) {
                    onError("No recipes found for '$query'")
                } else {
                    _searchResults.value = results
                    addToHistory(query)
                }
            },
            onError = { onError(it) }
        )
    }

    private fun addToHistory(query: String) {
        val currentHistory = _recentSearches.value.toMutableList()
        if (!currentHistory.contains(query)) {
            currentHistory.add(0, query)
            _recentSearches.value = currentHistory.take(5) // Keep last 5
        }
    }
}