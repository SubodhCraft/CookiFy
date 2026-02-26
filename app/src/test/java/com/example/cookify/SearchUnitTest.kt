package com.example.cookify

import com.example.cookify.repository.SearchRepo
import com.example.cookify.viewmodel.SearchViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock

class SearchUnitTest {

    @Test
    fun search_query_change_test() {
        val repo = mock<SearchRepo>()
        val viewModel = SearchViewModel(repo)
        val query = "Pasta"

        viewModel.onQueryChange(query)
        
        assertEquals(query, viewModel.searchQuery.value)
    }

    @Test
    fun search_empty_query_test() {
        val repo = mock<SearchRepo>()
        val viewModel = SearchViewModel(repo)
        
        viewModel.onQueryChange("")
        
        assertTrue(viewModel.searchResults.value?.isEmpty() ?: true)
    }
}
