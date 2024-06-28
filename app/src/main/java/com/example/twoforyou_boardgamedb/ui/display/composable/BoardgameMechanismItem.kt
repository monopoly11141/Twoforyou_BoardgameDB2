package com.example.twoforyou_boardgamedb.ui.display.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twoforyou_boardgamedb.ui.display.util.getKoreanNameOfMechanismItem

@Composable
fun BoardgameMechanismItem(
    mechanism: String
) {
    Card(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(2.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {
        Text(
            text = getKoreanNameOfMechanismItem(mechanism),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}