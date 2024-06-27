package com.example.twoforyou_boardgamedb.data.repository.display

import android.service.carrier.CarrierMessagingService.ResultCallback
import com.example.twoforyou_boardgamedb.data.db.local.BoardgameDao
import com.example.twoforyou_boardgamedb.data.db.remote.BoardgamegeekApi
import com.example.twoforyou_boardgamedb.data.model.BoardgameItem
import com.example.twoforyou_boardgamedb.data.model.api_model.Item
import com.example.twoforyou_boardgamedb.data.model.api_model.Items
import com.example.twoforyou_boardgamedb.domain.display.DisplayRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class DisplayRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val boardgameDao: BoardgameDao
) : DisplayRepository {

    override fun getBoardgamegeekApi(): BoardgamegeekApi {
        return retrofit.create(BoardgamegeekApi::class.java)
    }

    override suspend fun insertItemToDb(boardgameItem: BoardgameItem) {
        boardgameDao.insertBoardgame(boardgameItem)
    }

    override suspend fun deleteBoardgameItem(boardgameItem: BoardgameItem) {
        boardgameDao.deleteBoardgame(boardgameItem)
    }

    override suspend fun updateBoardgameItem(boardgameItem: BoardgameItem) {
        boardgameDao.updateBoardgame(boardgameItem)
    }

    override fun getBoardgameItem(id: Int, callback: ResultCallback<BoardgameItem>) {
        val call = getBoardgamegeekApi().boardListPost(id.toString())
        var items: Items
        call.enqueue(object : Callback<Items> {
            override fun onResponse(
                call: Call<Items>,
                response: Response<Items>
            ) {
                if (response.isSuccessful) {
                    try {
                        items = response.body()!!
                        val item = items.item

                        val boardgameItem = makeBoardgameItemFromItem(item, id)

                        callback.onReceiveResult(boardgameItem)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(p0: Call<Items>, p1: Throwable) {
                p1.printStackTrace()
            }
        })

    }

    override fun getAllBoardgameItem(): Flow<List<BoardgameItem>> {
        return boardgameDao.getAllBoardgame()
    }

    override fun getBoardgameFromKeyword(keyword: String): Flow<List<BoardgameItem>> {
        return boardgameDao.getBoardgameFromKeyword(keyword)
    }

    private fun makeBoardgameItemFromItem(item: Item, id: Int): BoardgameItem {
        val boardgameItem = BoardgameItem()

        boardgameItem.koreanName = getKoreanName(item.name.map {
            it.value
        })
        boardgameItem.englishName = getEnglishName(item.name.map {
            it.value
        })
        boardgameItem.boardgameUrl = "https://boardgamegeek.com/boardgame/$id"
        boardgameItem.imageUrl = item.imageUrl
        boardgameItem.description = item.description
        boardgameItem.linkValueList =
            item.link.filter { it.type == "boardgamemechanic" }.map { it.value }
        boardgameItem.averageValue = item.statistics.ratings.averageValue.toFloat()
        boardgameItem.bayesAverageValue =
            item.statistics.ratings.bayesAverageValue.toFloat()
        boardgameItem.maxPlayTimeValue = item.maxPlayTimeValue.toInt()
        boardgameItem.minPlayTimeValue = item.minPlayTimeValue.toInt()
        boardgameItem.maxPlayersValue = item.maxPlayersValue.toInt()
        boardgameItem.minPlayersValue = item.minPlayersValue.toInt()
        boardgameItem.numUsersRated =
            item.statistics.ratings.usersRatedValue.toInt()
        boardgameItem.ranking =
            item.statistics.ratings.ranks.rank.filter { it.friendlyName == "Board Game Rank" }[0].value.toInt()
        boardgameItem.averageWeight =
            item.statistics.ratings.averageWeightValue.toFloat()

        return boardgameItem
    }

    private fun getKoreanName(nameList: List<String>): String {
        for (koreanName in nameList) {
            if (filterKoreanFromString(koreanName).isNotEmpty()) {
                return koreanName
            }
        }
        return ""
    }

    //English name always appears at [0] from api.
    private fun getEnglishName(nameList: List<String>): String {
        return nameList[0]
    }

    private fun filterKoreanFromString(nameString: String): String {
        return nameString.filter {
            "^[ㄱ-ㅎ가-힣]*$".toRegex().containsMatchIn(it.toString())
        }.replace("_", " ").trim()
    }

}