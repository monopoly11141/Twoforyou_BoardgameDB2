package com.example.twoforyou_boardgamedb.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    object DisplayScreen

    @Serializable
    data class DetailScreen(
        val id: Int = 0
    )

    @Serializable
    data class FilterScreen(
        val idList: List<Int> = emptyList()
    )
}