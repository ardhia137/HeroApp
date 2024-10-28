package com.example.heroapp.ui.screen.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heroapp.data.ProgrammingLanguagesRepository
import com.example.heroapp.ui.components.ProgrammingLanguageListItem
import com.example.heroapp.ui.theme.HeroAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val repository = remember { ProgrammingLanguagesRepository.getInstance() }
    val favoriteHeroes = remember { repository.getFavoriteProgrammingLanguages() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (favoriteHeroes.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Tidak Ada Data!!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteHeroes) { hero ->
                        ProgrammingLanguageListItem(
                            name = hero.name,
                            photoUrl = hero.photoUrl,
                            description = hero.desc,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            onClick = {
                                navigateToDetail(hero.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    HeroAppTheme {
        FavoriteScreen(navigateToDetail = {},)
    }
}
