package com.example.twoforyou_boardgamedb.data.model.api_model

import com.example.twoforyou_boardgamedb.data.model.api_model.Rank
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ranks")
data class Ranks(
    @Element
    var rank: List<Rank> = listOf(Rank())
)
