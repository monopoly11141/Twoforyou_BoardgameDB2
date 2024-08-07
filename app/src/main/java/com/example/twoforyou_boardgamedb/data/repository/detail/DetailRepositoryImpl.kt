package com.example.twoforyou_boardgamedb.data.repository.detail

import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import com.example.twoforyou_boardgamedb.domain.detail.DetailRepository

class DetailRepositoryImpl(
    private val boardgameDao: BoardgameDao
) : DetailRepository {
    override suspend fun getBoardgameItemByRoomKey(id: Int): BoardgameItem {
        return boardgameDao.getBoardgameById(id)
    }
}