package com.example.twoforyou_boardgamedb.ui.display.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.twoforyou_boardgamedb.ui.display.DisplayViewModel

@Composable
fun AddBoardgameDialog(
    modifier: Modifier = Modifier,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var dialogBoardgameUrl by remember { mutableStateOf("") }
    var hasSuccesfullyAddedboardgame by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = { viewModel.setShowAddBoardgameDialog(false) }) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = dialogBoardgameUrl,
                onValueChange = { updatedUrl ->
                    dialogBoardgameUrl = updatedUrl
                },
                label = {
                    Text("보드게임 url")
                }
            )

            Button(
                onClick = {
                    hasSuccesfullyAddedboardgame = viewModel.insertBoardgameItemToDb(dialogBoardgameUrl)
                    viewModel.setShowAddBoardgameDialog(false)
                    dialogBoardgameUrl = ""
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text("보드게임 추가")
            }
        }
    }
    if (!hasSuccesfullyAddedboardgame) {
        Toast.makeText(LocalContext.current, "올바른 url을 입력하세요", Toast.LENGTH_SHORT).show()
        hasSuccesfullyAddedboardgame = true
    }
}