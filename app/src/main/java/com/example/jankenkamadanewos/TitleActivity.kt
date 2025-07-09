package com.example.jankenkamadanewos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class TitleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RockPaperScissorsApp()
        }
    }
}

enum class Nav {
    TitleScreen,
    SelectScreen,
    MainScreen,
    ResultScreen,
    HalfwayProgressScreen,
    FinalResultScreen
}

@Composable
fun RockPaperScissorsApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Nav.TitleScreen.name){
        composable(route = Nav.TitleScreen.name) { Title(navController) }
        composable(route = Nav.SelectScreen.name) { Select(navController) }
        composable(route = Nav.MainScreen.name) { Main(navController) }
        composable(route = Nav.ResultScreen.name) {
            val game = Game.create()
            Result(user = game.getSelectedHand(), navController = navController)
        }
        composable(route = Nav.HalfwayProgressScreen.name) { HalfwayProgress(navController) }
        composable(route = Nav.FinalResultScreen.name) { FinalResult(navController) }

    }
}

@Composable
fun Title(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize().semantics { contentDescription = Nav.TitleScreen.name }
    ) {
        val (
            button,
            title,
            topImage,
            speech,
            result
        ) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(topImage.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        Image(
            painter = painterResource(id = R.drawable.main),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topImage) {
                    top.linkTo(title.bottom)
                    bottom.linkTo(speech.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(R.string.chara_speech),
            fontSize = 40.sp,
            modifier = Modifier.constrainAs(speech) {
                top.linkTo(topImage.bottom)
                bottom.linkTo(result.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        ResetButton(
            modifier = Modifier.constrainAs(result) {
                top.linkTo(speech.bottom)
                bottom.linkTo(button.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(
            onClick = { navController.navigate(Nav.SelectScreen.name) },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button) {
                    top.linkTo(result.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(text = stringResource(R.string.next_scene), fontSize = 36.sp)
        }
    }
}

@SuppressLint("UnrememberedMutableState", "RememberReturnType")
@Composable
fun ResetButton(modifier: Modifier) {
    val countApp = CountApp.create()
    //初回コンポーズ時実行
    var clearWin by mutableIntStateOf(
        countApp.getNumOfWins()
    )
    var clearLose by mutableIntStateOf(
        countApp.getNumOfLoses()
    )
    var clearDraw by mutableIntStateOf(
        countApp.getNumOfDraws()
    )

    Column(modifier = modifier) {
        Text(
            text = stringResource(
                R.string.total_result,
                clearWin,
                clearLose,
                clearDraw
            ),
            fontSize = 27.sp
        )

        Button(
            onClick = {
                countApp.clearTotalResult()
                clearWin = 0
                clearLose = 0
                clearDraw = 0
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.semantics { contentDescription = "勝敗数のリセットボタン" }
        ) {
            Text(text = stringResource(R.string.reset), fontSize = 36.sp)
        }
    }
}