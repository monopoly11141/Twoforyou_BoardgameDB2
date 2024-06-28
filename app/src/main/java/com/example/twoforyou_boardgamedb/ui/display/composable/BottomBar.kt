package com.example.twoforyou_boardgamedb.ui.display.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twoforyou_boardgamedb.ui.display.DisplayViewModel

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Text(
        modifier = modifier,
        text = "${state.bottomBarLabelText} 게임 수 : ${state.displayBoardgameList.size} 개",
        fontSize = 18.sp
    )
}