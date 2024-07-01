package com.example.twoforyou_boardgamedb.ui.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedb.ui.display.composable.Boardgame
import com.example.twoforyou_boardgamedb.ui.filter.composable.FilterBoardgameItem

@Composable
fun FilterScreen(
    navController: NavController,
    idList: List<Int> = emptyList(),
    viewModel: FilterViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    viewModel.updateBoardgameList(idList)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(state.boardgameList) {
                FilterBoardgameItem(it, navController)
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }
        }
        Text(
            text = "검색된 보드게임 : ${state.boardgameList.size} 개",
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            fontSize = 20.sp
        )
    }

}