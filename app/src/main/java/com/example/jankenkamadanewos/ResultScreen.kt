package com.example.jankenkamadanewos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

private val countApp = CountApp.create()

fun determinesCPUsHand(): Int {
    val seed: Long = System.currentTimeMillis()
    val cpu: Int = Random(seed).nextInt(3)
    return cpu
}

@Composable
fun Result(user: Int, navController:NavController) {
    val cpu = determinesCPUsHand()
    ResultScreen(user, navController, cpu)
}

@Composable
fun ResultScreen(user: Int, navController:NavController, cpu: Int) {
    if (user == cpu) {
        if (countApp.getAddCount() < countApp.getCount()) {
            countApp.setDrawCount()
        }
    } else if ((user == 2 && cpu == 0) || ((user + 1) == cpu)) {
        if (countApp.getAddCount() < countApp.getCount()) {
            countApp.setWinCount()
        }
    } else {
        if (countApp.getAddCount() < countApp.getCount()) {
            countApp.setLoseCount()
        }
    }
    countApp.setAddCount()

    Column(
        modifier = Modifier
            .semantics { contentDescription = Nav.ResultScreen.name }
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultImage(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f, fill = true),
            user = user,
            cpu = cpu
        )

        Box(
            modifier = Modifier
                .weight(0.1f, fill = true)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.cpu))
            HandImage(id = cpu)
        }

        ResultText(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            user = user,
            cpu = cpu,
            fontSize = 30
        )

        Box(
            modifier = Modifier
                .weight(0.1f, fill = true)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.user))
            HandImage(id = user)
        }

        val conOrEnd = continueOrEnd()

        Button(
            onClick = { navController.navigate(conOrEnd) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = when (conOrEnd) {
                    Nav.FinalResultScreen.name -> {
                        stringResource(id = R.string.next_result)
                    }
                    Nav.MainScreen.name -> {
                        stringResource(id = R.string.next_battle)
                    }
                    else -> {
                        stringResource(id = R.string.next_scene)
                    }
                }
            )
        }
    }
}

@Composable
fun HandImage(id: Int) {
    when (id) {
        MyHand.GU.ordinal -> Image(
            painter = painterResource(id = R.drawable.j_gu02),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        MyHand.CH.ordinal -> Image(
            painter = painterResource(id = R.drawable.j_ch02),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        MyHand.PA.ordinal -> Image(
            painter = painterResource(id = R.drawable.j_pa02),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        else -> Image(painter = painterResource(id = R.drawable.imgres),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ResultImage(modifier: Modifier, user: Int, cpu: Int) {
    if (user == cpu) {
        Image(
            painter = painterResource(id = R.drawable.draw),
            contentDescription = null,
            modifier = modifier
        )
    } else if ((user == 2 && cpu == 0) || ((user + 1) == cpu)) {
        Image(
            painter = painterResource(id = R.drawable.win),
            contentDescription = null,
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.lose),
            contentDescription = null,
            modifier = modifier
        )
    }
}

@Composable
fun ResultText(modifier: Modifier, user: Int, cpu: Int, fontSize: Int) {
    if (user == cpu) {
        Text(
            text = stringResource(id = R.string.draw),
            fontSize = fontSize.sp,
            modifier = modifier
        )
    } else if ((user == 2 && cpu == 0) || ((user + 1) == cpu)) {
        Text(
            text = stringResource(id = R.string.win),
            fontSize = fontSize.sp,
            modifier = modifier
        )
    } else {
        Text(
            text = stringResource(id = R.string.lose),
            fontSize = fontSize.sp,
            modifier = modifier
        )
    }
}

private fun continueOrEnd():String {

    var conOrEnd: String
    val first = Nav.MainScreen.name
    val end = Nav.FinalResultScreen.name
    val con = Nav.HalfwayProgressScreen.name

    val game: Int = countApp.getCount()
    val rounds: Int = countApp.getAddCount()
    val win: Int = countApp.getWinCount()
    val lose: Int = countApp.getLoseCount()

    conOrEnd = if (rounds == 1) {
        first
    } else {
        con
    }

    if (countApp.getBattleFormat() == 1) {
        if ((win - lose) > (game - rounds)) {
            conOrEnd = end
        } else if ((lose - win) > (game - rounds)) {
            conOrEnd = end
        } else if (countApp.getDrawCount() > (game / 2)) {
            conOrEnd = end
        }
    }

    if (game <= rounds) {
        conOrEnd = end
    }
    return conOrEnd
}