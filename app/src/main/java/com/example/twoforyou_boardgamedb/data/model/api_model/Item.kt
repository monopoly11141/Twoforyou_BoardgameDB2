package com.example.twoforyou_boardgamedb.data.model.api_model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
data class Item(

    @Element(name = "name")
    var name: List<Name> = listOf(),

    @PropertyElement(name = "description")
    var description: String = "",

    @PropertyElement(name = "thumbnail")
    var imageUrl: String = "",

    @Path("minplayers")
    @Attribute(name = "value")
    val minPlayersValue : String = "",

    @Path("maxplayers")
    @Attribute(name = "value")
    val maxPlayersValue : String = "",

    @Path("minplaytime")
    @Attribute(name = "value")
    val minPlayTimeValue : String = "",

    @Path("maxplaytime")
    @Attribute(name = "value")
    val maxPlayTimeValue : String = "",

    @Element(name = "link")
    var link: List<Link> = listOf(),

    @Element(name = "statistics")
    var statistics: Statistics = Statistics()
)

