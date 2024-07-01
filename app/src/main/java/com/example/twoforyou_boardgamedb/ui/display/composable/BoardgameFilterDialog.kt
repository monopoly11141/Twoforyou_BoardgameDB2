package com.example.twoforyou_boardgamedb.ui.display.composable

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedb.navigation.Screen
import com.example.twoforyou_boardgamedb.ui.display.DisplayViewModel
import java.math.RoundingMode

@Composable
fun BoardgameFilterDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    navController: NavController,
    viewModel: DisplayViewModel = hiltViewModel(),
) {
    var sliderPlayerCountRange by remember { mutableStateOf(1f..10f) }
    var sliderBayesAverageRange by remember { mutableStateOf(0f..10f) }
    var sliderWeightRange by remember { mutableStateOf(0f..5f) }

    val dfOneDecimal = DecimalFormat("0.#")
    dfOneDecimal.roundingMode = RoundingMode.HALF_UP.ordinal

    val dfTwoDecimal = DecimalFormat("0.##")
    dfTwoDecimal.roundingMode = RoundingMode.HALF_UP.ordinal

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column {
                    RangeSlider(
                        value = sliderPlayerCountRange,
                        steps = 8,
                        onValueChange = { range -> sliderPlayerCountRange = range },
                        valueRange = 1f..10f
                    )
                    Text(
                        text = if (sliderPlayerCountRange.endInclusive == 10f) {
                            "플레이 인원 : ${sliderPlayerCountRange.start.toInt()}..${sliderPlayerCountRange.endInclusive.toInt()}+ 명"
                        } else {
                            "플레이 인원 : ${sliderPlayerCountRange.start.toInt()}..${sliderPlayerCountRange.endInclusive.toInt()} 명"
                        }
                    )
                }

                Column {
                    RangeSlider(
                        value = sliderBayesAverageRange,
                        steps = 19,
                        onValueChange = { range -> sliderBayesAverageRange = range },
                        valueRange = 0f..10f,
                    )
                    Text(
                        text = "긱 평점 : ${dfOneDecimal.format(sliderBayesAverageRange.start)}..${
                            dfOneDecimal.format(
                                sliderBayesAverageRange.endInclusive
                            )
                        } 점"
                    )
                }

                Column {
                    RangeSlider(
                        value = sliderWeightRange,
                        steps = 19,
                        onValueChange = { range -> sliderWeightRange = range },
                        valueRange = 0f..5f,
                    )
                    Text(
                        text = "복잡도 : ${dfTwoDecimal.format(sliderWeightRange.start)}..${
                            dfTwoDecimal.format(
                                sliderWeightRange.endInclusive
                            )
                        }"
                    )
                }

                Button(
                    onClick = {
                        val filteredBoardgameIdList = viewModel.filterBoardgame(
                            sliderPlayerCountRange,
                            sliderBayesAverageRange,
                            sliderWeightRange
                        )

                        navController.navigate(
                            Screen.FilterScreen(filteredBoardgameIdList)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "필터",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}