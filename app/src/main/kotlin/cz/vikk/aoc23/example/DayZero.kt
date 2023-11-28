package cz.vikk.aoc23.example

import arrow.core.Either
import cz.vikk.aoc23.common.AocException
import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.core.publisher.Mono

class DayZero {

    fun taskZeroSum(dataLoader: ResourceDataLoader): Either<AocException,Int> {
        val data = dataLoader.loadLinesFromFile()
        return data.map { lines -> lines.stream().map { handleSum(it) }.reduce { sum, element -> sum + element }.orElse(-1)}
    }

    private fun handleSum(s : String): Int{
        return try{
            s.toInt()
        }catch (e: NumberFormatException){
            0
        }
    }

    fun taskZeroSumFlux(dataLoader: ResourceDataLoader): Mono<Int> {
        val flux = dataLoader.loadLinesFromFileFlux()
        return flux.map { it.toInt() }
            .onErrorReturn(0) //if parsing goes off, use 0 value instead
            .reduce { t, u -> t+u  } // sum it
    }
}
