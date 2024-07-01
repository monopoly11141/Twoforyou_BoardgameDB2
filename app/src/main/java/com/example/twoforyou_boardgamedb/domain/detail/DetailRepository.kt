package com.example.twoforyou_boardgamedb.domain.detail

import com.example.twoforyou_boardgamedb.data.model.BoardgameItem

interface DetailRepository {
    suspend fun getBoardgameItemByRoomKey(id: Int) : BoardgameItem
}