package com.example.twoforyou_boardgamedb.ui.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import com.example.twoforyou_boardgamedb.ui.display.util.DISPLAY_ORDER

data class DisplayUiState(
    val boardgameItemList: List<BoardgameItem> = emptyList(),
    val displayBoardgmaeList: List<BoardgameItem> = emptyList(),
    var displayOrder: DISPLAY_ORDER = DISPLAY_ORDER.ALPHABETICAL,
    var bottomBarLabelText: String = "전체"
)