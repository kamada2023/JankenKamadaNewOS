package com.example.jankenkamadanewos

class Game{
    companion object Hands{
        private var selectedHand : Int = 0
        fun create(): Game = Game()
    }

    fun setSelectedHand(myHand: Int) {
        selectedHand = myHand
    }

    fun getSelectedHand():Int {
        return selectedHand
    }
}