package com.example.heroapp.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heroapp.ViewModelFactory
import com.example.heroapp.di.Injection
import com.example.heroapp.ui.components.ProgrammingLanguageListItem

import com.example.heroapp.ui.components.ScrollToTopButton
import com.example.heroapp.ui.components.SearchBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val groupedProgrammingLanguages by viewModel.groupedProgrammingLanguages.collectAsState()
    val query by viewModel.query
    Box(
        modifier = modifier
    ) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(state = listState) {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            if (groupedProgrammingLanguages.all { it.value.isEmpty() }) {
                item {
                    Text(
                        text = "Tidak ada data",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(vertical = 300.dp, horizontal = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                groupedProgrammingLanguages.forEach { (initial, programmingLanguage) ->
                    items(programmingLanguage, key = { it.id }) { programmingLanguage ->
                        ProgrammingLanguageListItem(
                            name = programmingLanguage.name,
                            photoUrl = programmingLanguage.photoUrl,
                            description = programmingLanguage.desc,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 10.dp),
                            onClick = {
                                navigateToDetail(programmingLanguage.id)
                            }
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 10.dp, end = 10.dp)
                .align(Alignment.BottomEnd)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

