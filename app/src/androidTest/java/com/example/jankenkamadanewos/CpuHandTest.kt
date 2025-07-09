package com.example.jankenkamadanewos

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Rule
import org.junit.Test

class CpuHandTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Test
    fun usersHandGu_cpusHandGu_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(0, navController, 0)
        }
        composeTestRule.onNodeWithText("引き分け").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandGu_cpusHandCh_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(0, navController, 1)
        }
        composeTestRule.onNodeWithText("あんたの勝ち！！").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandGu_cpusHandPa_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(0, navController, 2)
        }
        composeTestRule.onNodeWithText("あなたの負け．．").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandCh_cpusHandGu_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(1, navController, 0)
        }
        composeTestRule.onNodeWithText("あなたの負け．．").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandCh_cpusHandCh_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(1, navController, 1)
        }
        composeTestRule.onNodeWithText("引き分け").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandCh_cpusHandPa_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(1, navController, 2)
        }
        composeTestRule.onNodeWithText("あんたの勝ち！！").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandPa_cpusHandGu_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(2, navController, 0)
        }
        composeTestRule.onNodeWithText("あんたの勝ち！！").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandPa_cpusHandCh_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(2, navController, 1)
        }
        composeTestRule.onNodeWithText("あなたの負け．．").assertExists()
        Thread.sleep(500)
    }

    @Test
    fun usersHandPa_cpusHandPa_Test(){
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            ResultScreen(2, navController, 2)
        }
        composeTestRule.onNodeWithText("引き分け").assertExists()
        Thread.sleep(500)
    }
}