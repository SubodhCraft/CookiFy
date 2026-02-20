package com.example.cookify.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookify.viewmodel.SearchViewModel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel()) {
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).statusBarsPadding()) {
        // Search Bar
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search recipes...") },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.performSearch(query,
                        onEmpty = { Toast.makeText(context, "Search field is empty!", Toast.LENGTH_SHORT).show() },
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                    )
                }) {
                    Icon(painter = painterResource(com.example.cookify.R.drawable.baseline_search_24), contentDescription = "Search")
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Logic to display History or Results
        if (query.isEmpty() && recentSearches.isNotEmpty()) {
            Text("Recent Searches", style = MaterialTheme.typography.titleSmall)
            recentSearches.forEach { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    modifier = Modifier.clickable { viewModel.onQueryChange(item) }
                )
            }
        } else {
            LazyColumn {
                items(results) { recipe ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        onClick = {
                             Toast.makeText(context, "Selected: ${recipe.title}", Toast.LENGTH_SHORT).show()
                             // Future: Navigate to detail
                        }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(recipe.title, style = MaterialTheme.typography.titleMedium)
                            Text(recipe.description, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearch(){
    SearchScreen()
}