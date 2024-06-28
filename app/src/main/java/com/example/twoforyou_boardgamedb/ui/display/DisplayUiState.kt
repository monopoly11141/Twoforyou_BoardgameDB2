package com.example.twoforyou_boardgamedb.ui.display

import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import com.example.twoforyou_boardgamedb.ui.display.util.DISPLAY_ORDER

data class DisplayUiState(
    val boardgameItemList: List<BoardgameItem> = emptyList(),
    val displayBoardgameList: List<BoardgameItem> = emptyList(),
    val displayOrder: DISPLAY_ORDER = DISPLAY_ORDER.ALPHABETICAL,
    val bottomBarLabelText: String = "전체",
    val showAddBoardgameDialog : Boolean = false
)