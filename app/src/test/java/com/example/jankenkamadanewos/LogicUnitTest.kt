package com.example.jankenkamadanewos

import org.junit.Assert.assertNotNull
import org.junit.Test

class LogicUnitTest {
    @Test
    fun testResultScreenLogic(){
        val decision = determinesCPUsHand()
        assertNotNull(decision)
    }
}