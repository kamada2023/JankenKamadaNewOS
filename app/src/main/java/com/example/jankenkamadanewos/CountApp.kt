package com.example.jankenkamadanewos

class CountApp {
    companion object CountUp{
        private var Count:Int = 1
        private var AddCount:Int = 0
        private var WinCount:Int = 0
        private var LoseCount:Int = 0
        private var DrawCount:Int = 0
        private var BattleFormat:Int = 0
        private var Num_Of_Wins:Int = 0
        private var Num_Of_Loses:Int = 0
        private var Num_Of_Draws:Int = 0
        fun create(): CountApp = CountApp()
    }

    fun getCount():Int{
        return Count
    }
    fun setCount(count:Int){
        Count = count
    }
    fun getAddCount():Int{
        return AddCount
    }
    fun setAddCount(){
        AddCount++
    }

    fun getWinCount():Int{
        return WinCount
    }
    fun setWinCount(){
        WinCount++
    }
    fun getLoseCount():Int{
        return LoseCount
    }
    fun setLoseCount(){
        LoseCount++
    }
    fun getDrawCount():Int{
        return DrawCount
    }
    fun setDrawCount(){
        DrawCount++
    }
    fun clearResult(){
        Count = 1
        AddCount = 0
        WinCount = 0
        LoseCount = 0
        DrawCount = 0
    }

    fun getBattleFormat():Int{
        return BattleFormat
    }
    fun setBattleFormat(battleFormat:Int){
        BattleFormat = battleFormat
    }
    fun getNumOfWins():Int{
        return Num_Of_Wins
    }
    fun setNumOfWins(){
        Num_Of_Wins++
    }
    fun getNumOfLoses():Int{
        return Num_Of_Loses
    }
    fun setNumOfLoses(){
        Num_Of_Loses++
    }
    fun getNumOfDraws():Int{
        return Num_Of_Draws
    }
    fun setNumOfDraws(){
        Num_Of_Draws++
    }
    fun clearTotalResult(){
        Num_Of_Wins = 0
        Num_Of_Loses = 0
        Num_Of_Draws = 0
    }
}