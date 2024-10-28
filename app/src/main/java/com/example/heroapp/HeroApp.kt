package com.example.heroapp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.heroapp.di.Injection
import com.example.heroapp.ui.navigation.NavigationItem
import com.example.heroapp.ui.navigation.Screen
import com.example.heroapp.ui.screen.home.HomeScreen
import com.example.heroapp.ui.screen.ProfileScreen
import com.example.heroapp.ui.screen.detail.DetailScreen
import com.example.heroapp.ui.screen.detail.DetailViewModel
import com.example.heroapp.ui.screen.favorite.FavoriteScreen
import com.example.heroapp.ui.theme.HeroAppTheme

@Composable
fun HeroApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val heroDetailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailHero.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailHero.createRoute(id))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailHero.createRoute(id))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    "Muhammad Ardhia Nugraha",
                    "ardhia137@gmail.com",
                    "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos-4f44c2ddd4732720a6c8f74b1b4ad86420240215191931.png"
                )
            }
            composable(
                route = Screen.DetailHero.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("id") ?: ""
                heroDetailViewModel.fetchProgrammingLanguage(id) // Fetch hero data

                val hero by heroDetailViewModel.programmingLanguage.collectAsState()

                if (hero != null) {
                    DetailScreen(
                        id = hero!!.id,
                        name = hero!!.name,
                        description = hero!!.desc,
                        photoUrl = hero!!.photoUrl,
                        onBackClick = { navController.navigateUp() }
                    )
                } else {
                    Text("Loading...")
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HeroesAppPreview() {
    HeroAppTheme {
        HeroApp()
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier.height(70.dp)
    ) {

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home,

            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite,
                ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier.padding(top = 10.dp)
                    )
                },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
