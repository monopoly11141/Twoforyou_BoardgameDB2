package com.example.twoforyou_boardgamedb.ui.detail

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.math.RoundingMode

@Composable
fun DetailScreen(
    navController: NavController,
    id: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        viewModel.updateDetailBoardgameItemById(id)
    }

    val boardgameItem = state.detailBoardgameItem

    val uriHandler = LocalUriHandler.current

    val df = DecimalFormat("0.00")
    df.roundingMode = RoundingMode.HALF_UP.ordinal

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(boardgameItem.imageUrl).build(),
        contentScale = ContentScale.Fit
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter,
                contentScale = ContentScale.Fit,
                alpha = 0.2f,
                alignment = Alignment.TopCenter
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = boardgameItem.koreanName.ifBlank { boardgameItem.englishName },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(
                            color = Color.Gray,
                            radius = this.size.maxDimension / 2.0f
                        )
                    }
                    .padding(horizontal = 16.dp)
                ,
                text = "${df.format(boardgameItem.bayesAverageValue)}점",
            )
        }


        Button(onClick = {
            uriHandler.openUri(boardgameItem.boardgameUrl)
        }) {
            Text(text = "긱 웹사이트 가기")
        }
    }
}