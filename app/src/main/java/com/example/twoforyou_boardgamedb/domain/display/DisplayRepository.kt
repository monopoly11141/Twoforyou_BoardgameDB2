package com.example.twoforyou_boardgamedb.domain.display

import android.service.carrier.CarrierMessagingService.ResultCallback
import com.example.twoforyou_boardgamedb.data.db.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import kotlinx.coroutines.flow.Flow

interface DisplayRepository {
    fun getBoardgamegeekApi(): BoardgamegeekApi

    suspend fun insertItemToDb(boardgameItem: BoardgameItem)
    suspend fun deleteBoardgameItem(boardgameItem: BoardgameItem)
    suspend fun updateBoardgameItem(boardgameItem: BoardgameItem)

    fun getBoardgameItem(id: Int, callback: ResultCallback<BoardgameItem>)
    fun getAllBoardgameItem(): Flow<List<BoardgameItem>>
    fun getBoardgameFromKeyword(keyword: String): Flow<List<BoardgameItem>>
}