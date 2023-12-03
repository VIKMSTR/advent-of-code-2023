package cz.vikk.aoc23.day03

import arrow.core.Either
import cz.vikk.aoc23.common.ResourceDataLoader

class Day03 {
    private val symbolRegex = """(\W)""".toRegex()
    private val numberRegex = """(\d+)""".toRegex()

    fun firstTask(dataLoader: ResourceDataLoader): Int {
        val lines = dataLoader.loadLinesFromFile()
        val symbols = lines.map { it.mapIndexed { index, line -> extractSymbols(line, index) }.flatten().sortedBy { it.line } }
        val numbers = lines.map { it.mapIndexed { index, line -> extractDigits(line, index) }.flatten().sortedBy { it.line } }
        return when (symbols) {
            is Either.Left -> return -1
            is Either.Right -> when (numbers) {
                is Either.Left -> return -1
                is Either.Right -> numbers.value.filter { it.isValidNumber(symbols.value) }.map { it.number }.reduce { t, u -> t + u }
            }
        }
    }

    private fun extractSymbols(line: String, index: Int): List<PositionedSymbol> {
        val matched = symbolRegex.findAll(line.replace(".", "a"))
        return matched.toList().map { PositionedSymbol(it.value, it.range.first, index) }
    }

    private fun extractDigits(line: String, index: Int): List<PositionedNumber> {
        val matched = numberRegex.findAll(line)
        return matched.toList().map { PositionedNumber(it.value.toInt(), it.range, index) }
    }

    fun secondTask(dataLoader: ResourceDataLoader): Long {
        val lines = dataLoader.loadLinesFromFile()
        val symbols = lines.map { it.mapIndexed { index, line -> extractSymbols(line, index) }.flatten().sortedBy { it.line } }
        val numbers = lines.map { it.mapIndexed { index, line -> extractDigits(line, index) }.flatten().sortedBy { it.line } }
        return when (symbols) {
            is Either.Left -> return -1
            is Either.Right -> when (numbers) {
                is Either.Left -> return -1
                is Either.Right -> symbols.value.map { it.toGearRatio(numbers.value) }.reduce { t, u -> t + u }
            }
        }
    }

}

data class PositionedSymbol(val symbol: String, val position: Int, val line: Int) {
    fun lineRange(): IntRange {
        return IntRange(line - 1, line + 1)
    }

    fun toGearRatio(numbers: List<PositionedNumber>): Long {
        val adjacentLines = numbers.filter { lineRange().contains(it.line) }
        val adjacentNumbers = adjacentLines.filter { it.getNumberRange().contains(position) }
        return if (adjacentNumbers.size == 2) {
            adjacentNumbers.first().number.toLong() * adjacentNumbers.last().number.toLong()
        } else {
            0
        }
    }
}

data class PositionedNumber(val number: Int, val position: IntRange, val line: Int) {
    private val numberRange = IntRange(position.first - 1, position.last + 1)
    fun isValidNumber(symbols: List<PositionedSymbol>): Boolean {
        val adjacentLines = symbols.filter { it.lineRange().contains(line) }
        return adjacentLines.any { numberRange.contains(it.position) }
    }

    fun getNumberRange(): IntRange {
        return numberRange
    }
}
