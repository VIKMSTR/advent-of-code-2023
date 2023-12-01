package cz.vikk.aoc23.day01

import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.core.publisher.Mono

class Day01 {

    private val digitRegex = """([0-9])""".toRegex()
    private val writtenNumberRegex = """(one|two|three|four|five|six|seven|eight|nine)""".toRegex()
    private val writtenNumberRegexSolid = """(?=(one|two|three|four|five|six|seven|eight|nine))""".toRegex()
    private val replacementMap = mapOf(
        "one" to "o1e", "two" to "t2o", "three" to "th3ree", "four" to "f4r", "five" to "f5e", "six" to "s6x", "seven" to "s7n",
        "eight" to "e8h", "nine" to "n9e",
    )
    private val replacementSolid = mapOf(
        "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7",
        "eight" to "8", "nine" to "9",
    )

    fun firstTask(dataLoader: ResourceDataLoader): Mono<Int> {
        return dataLoader.loadLinesFromFileFlux().map { convertToDoubleDigitNumber(it) }.onErrorReturn(0).reduce { t, u -> t + u }
    }

    private fun convertToDoubleDigitNumber(input: String): Int {
        val regexResult = digitRegex.findAll(input).map { it.value }.toList()
        return "${regexResult.first()}${regexResult.last()}".toInt()
    }

    fun secondTask(dataLoader: ResourceDataLoader): Mono<Int> {
        return dataLoader.loadLinesFromFileFlux()
            .map { replaceStringWithNumeralsHack(it) }
            .map { convertToDoubleDigitNumber(it) }
           // .doOnEach { println(it) }
            .onErrorReturn(0)
            .reduce { t, u -> t + u }
    }

    private fun replaceStringWithNumeralsHack(input: String): String {
        var result = input
        replacementMap.forEach{ result = result.replace(it.key,it.value,true)}
        return result
    }
    private fun replaceStringWithNumerals(input: String): String {
         return writtenNumberRegex.replace(input.lowercase()){
             replacementMap[it.value] + ""
         }
    }

}
