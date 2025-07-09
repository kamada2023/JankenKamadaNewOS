package com.example.jankenkamadanewos

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VictoryOrDefeatTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private val cpuTestingHand = 0
    @Before
    fun setup() {
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            NavHost(navController = navController, startDestination = Nav.TitleScreen.name){
                composable(route = Nav.TitleScreen.name) { Title(navController) }
                composable(route = Nav.SelectScreen.name) { Select(navController) }
                composable(route = Nav.MainScreen.name) { Main(navController) }
                composable(route = Nav.ResultScreen.name) {
                    val game = Game.create()
                    ResultScreen(user = game.getSelectedHand(), navController = navController, cpu = cpuTestingHand)
                }
                composable(route = Nav.HalfwayProgressScreen.name) { HalfwayProgress(navController) }
                composable(route = Nav.FinalResultScreen.name) { FinalResult(navController) }
            }
        }
    }

    //初期表示
    @Test
    fun navHost_verifyStartDestination(){
        composeTestRule.onNodeWithContentDescription(Nav.TitleScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    //TitleScreenから遷移
    @Test
    fun navHost_clickNextScenes_navigateSelectScreen(){
        composeTestRule.onNodeWithText("次のシーンへ").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.SelectScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    //SelectScreenから遷移
    private fun navHost_clickGameStart_navigateMainScreen_2AdditionalMatch(){
        composeTestRule.onNodeWithContentDescription("回数のスライダー")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(4f) } //3回戦
        Thread.sleep(1000)
        composeTestRule.onNodeWithText("ゲームスタート").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.MainScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    private fun navHost_clickGameStart_navigateMainScreen_ChangedToStarContest_2AdditionalMatch(){
        composeTestRule.onNodeWithContentDescription("対戦形式のスライダー")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(2f) } //星取り戦に変更
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription("回数のスライダー")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(4f) } //3回戦
        Thread.sleep(1000)
        composeTestRule.onNodeWithText("ゲームスタート").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.MainScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    private fun navHost_clickGameStart_navigateMainScreen_ChangedToStarContest_4AdditionalMatch(){
        composeTestRule.onNodeWithContentDescription("対戦形式のスライダー")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(2f) } //星取り戦に変更
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription("回数のスライダー")
            .performSemanticsAction(SemanticsActions.SetProgress) { it(5.5f) } //5回戦
        Thread.sleep(1000)
        composeTestRule.onNodeWithText("ゲームスタート").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.MainScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    //MainScreen_action
    private fun navHost_clickGU_navigateResultScreen(){
        composeTestRule.onNodeWithContentDescription("GU").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.ResultScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    private fun navHost_clickCH_navigateResultScreen(){
        composeTestRule.onNodeWithContentDescription("CH").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.ResultScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    private fun navHost_clickPA_navigateResultScreen(){
        composeTestRule.onNodeWithContentDescription("PA").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.ResultScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    //ResultScreen_action
    private fun navHost_clickNextResult_navigateFinalResultScreen(){
        composeTestRule.onNodeWithText("リザルト画面へ").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.FinalResultScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    private fun navHost_clickNextBattle_navigateMainScreen(){
        composeTestRule.onNodeWithText("次の対戦へ").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.MainScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    private fun navHost_clickNextScene_navigateHalfwayProgressScreen(){
        composeTestRule.onNodeWithText("次のシーンへ").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.HalfwayProgressScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    //FinalResultScreen_action
    private fun navHost_clickNextScene_navigateTitleScreen(){
        composeTestRule.onNodeWithText("タイトルへ").performClick()
        composeTestRule.onNodeWithContentDescription(Nav.TitleScreen.name).assertIsDisplayed()
        Thread.sleep(500)
    }

    @Test
    fun navHost_RoundRobinGame_3rdRound_win(){
        navHost_clickNextScenes_navigateSelectScreen()
        navHost_clickGameStart_navigateMainScreen_2AdditionalMatch()
        //1回戦
        navHost_clickPA_navigateResultScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //2回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //3回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextResult_navigateFinalResultScreen()
        //titleに戻る
        navHost_clickNextScene_navigateTitleScreen()
    }

    @Test
    fun navHost_RoundRobinGame_3rdRound_lose(){
        navHost_clickNextScenes_navigateSelectScreen()
        navHost_clickGameStart_navigateMainScreen_2AdditionalMatch()
        //1回戦
        navHost_clickCH_navigateResultScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //2回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //3回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextResult_navigateFinalResultScreen()
        //titleに戻る
        navHost_clickNextScene_navigateTitleScreen()
    }

    @Test
    fun navHost_RoundRobinGame_3rdRound_draw(){
        navHost_clickNextScenes_navigateSelectScreen()
        navHost_clickGameStart_navigateMainScreen_2AdditionalMatch()
        //1回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //2回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //3回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextResult_navigateFinalResultScreen()
        //titleに戻る
        navHost_clickNextScene_navigateTitleScreen()
    }

    @Test
    fun navHost_StarGame_3rdRound_DrawInTheMiddle(){
        navHost_clickNextScenes_navigateSelectScreen()
        navHost_clickGameStart_navigateMainScreen_ChangedToStarContest_2AdditionalMatch()
        //1回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //2回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextResult_navigateFinalResultScreen()
        //titleに戻る
        navHost_clickNextScene_navigateTitleScreen()
    }

    @Test
    fun navHost_StarGame_5thRound_WinningInTheMiddle(){
        navHost_clickNextScenes_navigateSelectScreen()
        navHost_clickGameStart_navigateMainScreen_ChangedToStarContest_4AdditionalMatch()
        //1回戦
        navHost_clickPA_navigateResultScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //2回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //3回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //4回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextResult_navigateFinalResultScreen()
        //titleに戻る
        navHost_clickNextScene_navigateTitleScreen()
    }

    @Test
    fun navHost_StarGame_5thRound_LostInTheMiddle(){
        navHost_clickNextScenes_navigateSelectScreen()
        navHost_clickGameStart_navigateMainScreen_ChangedToStarContest_4AdditionalMatch()
        //1回戦
        navHost_clickCH_navigateResultScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //2回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //3回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextScene_navigateHalfwayProgressScreen()
        navHost_clickNextBattle_navigateMainScreen()
        //4回戦
        navHost_clickGU_navigateResultScreen()
        navHost_clickNextResult_navigateFinalResultScreen()
        //titleに戻る
        navHost_clickNextScene_navigateTitleScreen()
    }
}