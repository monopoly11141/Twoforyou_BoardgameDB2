package com.example.twoforyou_boardgamedb.data.model.api_model

import com.example.twoforyou_boardgamedb.data.model.api_model.Ranks
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ratings")
data class Ratings(
    @Element(name="ranks")
    val ranks: Ranks = Ranks(),
    @Path("usersrated")
    @Attribute(name = "value")
    val usersRatedValue : String = "",
    @Path("average")
    @Attribute(name = "value")
    val averageValue: String = "",
    @Path("bayesaverage")
    @Attribute(name = "value")
    val bayesAverageValue : String = "",
    @Path("averageweight")
    @Attribute(name = "value")
    val averageWeightValue : String = "",
)
