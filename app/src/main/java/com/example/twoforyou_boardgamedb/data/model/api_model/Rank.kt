package com.example.twoforyou_boardgamedb.data.model.api_model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rank")
data class Rank(
    @Attribute(name = "friendlyname")
    var friendlyName : String = "",
    @Attribute(name = "value")
    var value: String = "",
    @Attribute(name = "bayesaverage")
    var bayesaverage: String = "",

)
