package com.example.twoforyou_boardgamedb.data.model.api_model

import com.example.twoforyou_boardgamedb.data.model.api_model.Item
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
@Xml(name= "items")
data class Items(
    @Element(name="item")
    val item: Item = Item(),
)