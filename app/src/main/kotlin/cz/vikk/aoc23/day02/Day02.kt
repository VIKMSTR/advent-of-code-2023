package cz.vikk.aoc23.day02

import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

class Day02 {

    val indexSeparator = ":"
    val turnSeparator = ";"
    val combinationSeparator = ","
    val colorSeparator = " "

    fun firstTask(dataLoader: ResourceDataLoader): Mono<Int> {
     return dataLoader.loadLinesFromFileFlux().index()
            .map { destructureGame(it) }
            .filter {
                filterGameSessions(it)
            }
            .map { it.index }
            .onErrorReturn(0)
            .reduce { t, u -> t + u }
    }

    private fun destructureGame(it: Tuple2<Long, String>?) : GameSession{
        val game = it!!.t2.split(indexSeparator)[1]
        val turns = game.split(turnSeparator).map { destructureTurn(it) }
        return GameSession(it.t1.toInt()+1,turns)
    }

    private fun destructureTurn(turnString: String) : GameTurn {
        val combinations = turnString.split(combinationSeparator).map {
            val combination = it.trim().split(colorSeparator)
            Combination(combination[0].toInt(),combination[1])
        }
        return GameTurn(combinations)
    }

    private fun filterGameSessions(game: GameSession): Boolean {
        var validGame = true
        game.turns.map { it.combinations }.flatten().forEach {
            when(it.color){
                "red"-> if (it.amount > 12) validGame = false
                "green" ->  if (it.amount > 13) validGame = false
                "blue" ->  if (it.amount > 14) validGame = false
            }
        }
        return validGame
    }

    fun secondTask(dataLoader: ResourceDataLoader): Mono<Int> {
        return dataLoader.loadLinesFromFileFlux().index()
            .map { destructureGame(it) }
            .map { extractMaxAmounts(it) }
            .map {
                it.reduce{t,u -> t*u}
            }
            .reduce { t, u -> t + u }
    }

    private fun extractMaxAmounts(game: GameSession): List<Int>{
        val colors = game.turns.map { it.combinations }.flatten().groupBy { it.color }
        return colors.entries.mapNotNull { it.value.maxOfOrNull { it.amount } }

    }



}
data class GameSession(val index: Int, val turns: List<GameTurn>){

}

data class GameTurn (val combinations: List<Combination> ){

}

data class Combination(val amount: Int, val color: String)

