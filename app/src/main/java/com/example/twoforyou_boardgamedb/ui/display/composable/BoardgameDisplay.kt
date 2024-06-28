package com.example.twoforyou_boardgamedb.ui.display.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedb.ui.display.DisplayViewModel

@Composable
fun BoardgameDisplay(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = state.displayBoardgameList,
            key = { boardgameItem ->
                boardgameItem.id
            }
        ) { boardgameItem ->

            Boardgame(
                boardgameItem,
                navController
            )

            Divider(
                thickness = 2.dp,
                color = Color.Black
            )
        }
    }
}