package com.example.twoforyou_boardgamedb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.twoforyou_boardgamedb.ui.detail.DetailScreen
import com.example.twoforyou_boardgamedb.ui.display.DisplayScreen
import com.example.twoforyou_boardgamedb.ui.filter.FilterScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DisplayScreen
    ) {
        composable<Screen.DetailScreen> {
            val args = it.toRoute<Screen.DetailScreen>()
            DetailScreen(
                navController = navController,
                args.id
            )
        }

        composable<Screen.DisplayScreen> {
            DisplayScreen(
                navController = navController
            )
        }

        composable<Screen.FilterScreen> {
            val args = it.toRoute<Screen.FilterScreen>()
            FilterScreen(
                navController = navController,
                args.idList
            )
        }

    }
}