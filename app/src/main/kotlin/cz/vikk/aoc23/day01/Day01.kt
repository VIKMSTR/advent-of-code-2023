package cz.vikk.aoc23.day01

import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.core.publisher.Mono

class Day01 {

    private val digitRegex = """([0-9])""".toRegex()
    private val numberArray = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    fun firstTask(dataLoader: ResourceDataLoader): Mono<Int> {
        return dataLoader.loadLinesFromFileFlux().map { convertToDoubleDigitNumber(it) }.onErrorReturn(0).reduce { t, u -> t + u }
    }

    private fun convertToDoubleDigitNumber(input: String): Int {
        val regexResult = digitRegex.findAll(input).map { it.value }.toList()
        return "${regexResult.first()}${regexResult.last()}".toInt()
    }

    fun secondTask(dataLoader: ResourceDataLoader): Mono<Int> {
        return dataLoader.loadLinesFromFileFlux()
            .map { detectNumbers(it) }
            .map { createDoubleDigitNumber(it) }
            .onErrorReturn(0)
            .reduce { t, u -> t + u }
    }

    private fun detectNumbers(input: String): List<String> {
        return numberArray.map {
            extractNumbers(input, it)
        }.flatten()
         .filter {
                it.position != -1
         }.sortedBy {
                it.position
         }.map {
                it.convertToDigit()
         }.toList()
    }

    private fun createDoubleDigitNumber(digits: List<String>): Int {
        return "${digits.first()}${digits.last()}".toInt()
    }

    private fun extractNumbers(input: String, number: String): List<NumberWithPosition> {
        var index = input.indexOf(number, 0, true)
        var arrayOfPositionedNumbers = mutableListOf<NumberWithPosition>()
        while (index != -1) {
            arrayOfPositionedNumbers.add(NumberWithPosition(index, number))
            val nextIndex = if ((index +1) < input.length ) index +1 else break
            index = input.indexOf(number, nextIndex, true)
        }
        return arrayOfPositionedNumbers
    }
}

class NumberWithPosition(val position: Int, val content: String) {
    private val replacementSolid = mapOf(
        "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7",
        "eight" to "8", "nine" to "9",
    )

    fun convertToDigit(): String {
        return if (content.lowercase() in replacementSolid.keys) {
            replacementSolid[content.lowercase()]!!
        } else {
            content
        }
    }
}
