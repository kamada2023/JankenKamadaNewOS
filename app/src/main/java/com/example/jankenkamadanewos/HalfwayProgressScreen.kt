package com.example.jankenkamadanewos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HalfwayProgress(navController: NavController) {
    val countApp = CountApp.create()
    val battleCount: Int = countApp.getAddCount()
    val countWin: Int = countApp.getWinCount()
    val countLose: Int = countApp.getLoseCount()
    val countDraw: Int = countApp.getDrawCount()
    Column(modifier = Modifier
        .semantics { contentDescription = Nav.HalfwayProgressScreen.name }
        .fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.halfway_title),
            fontSize = 30.sp,
            modifier = Modifier
                .weight(2f, fill = true)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = R.string.halfway_sub, battleCount),
            fontSize = 30.sp,
            modifier = Modifier
                .weight(3f, fill = true)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = R.string.win_count, countWin),
            modifier = Modifier
                .weight(1f, fill = true)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(id = R.string.lose_count, countLose),
            modifier = Modifier
                .weight(1f, fill = true)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(id = R.string.draw_count, countDraw),
            modifier = Modifier
                .weight(1f, fill = true)
                .fillMaxWidth()
        )

        Button(
            onClick = { navController.navigate(Nav.MainScreen.name) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.next_battle))
        }
    }
}