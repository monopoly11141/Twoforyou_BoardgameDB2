package com.example.twoforyou_boardgamedb.data.model.api_model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Name(
    @Attribute(name = "type")
    var type: String = "",
    @Attribute(name = "value")
    var value: String = ""
)
