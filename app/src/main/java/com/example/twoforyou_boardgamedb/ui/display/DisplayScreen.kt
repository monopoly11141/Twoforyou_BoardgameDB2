package com.example.twoforyou_boardgamedb.ui.display

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedb.ui.display.composable.AddBoardgameDialog
import com.example.twoforyou_boardgamedb.ui.display.composable.BoardgameDisplay
import com.example.twoforyou_boardgamedb.ui.display.composable.BottomBar
import com.example.twoforyou_boardgamedb.ui.display.composable.TopSearchBar

@Composable
fun DisplayScreen(
    navController: NavController,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    //updating all boardgame item data from api

    Scaffold(
        floatingActionButton =
        {
            FloatingActionButton(
                onClick = { viewModel.setShowAddBoardgameDialog(true) },
                shape = CircleShape,
                modifier = Modifier
                    .imePadding()
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "추가")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 16.dp)
        ) {
            TopSearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                navController = navController
            )

            BoardgameDisplay(
                modifier = Modifier
                    .weight(1f),
                navController = navController
            )

            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    if (state.showAddBoardgameDialog) {
        AddBoardgameDialog()
    }

}