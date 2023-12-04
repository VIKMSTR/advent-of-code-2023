package cz.vikk.aoc23.day04

import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.core.publisher.Mono
import kotlin.math.pow

class Day04 {
    private val numberRegex = """(\d+)""".toRegex()
    private val indexSeparator = ":"
    private val numbersSeparator = "|"

    fun firstTask(dataLoader: ResourceDataLoader): Mono<Long> {
        return dataLoader.loadLinesFromFileFlux()
            .index()
            .map {destructureCard(it!!.t1.toInt(), it.t2)}
            .map { collectPointsFromCard(it) }
            .reduce{t,u -> t+u}
    }
    private fun destructureCard(index: Int,line: String): Card {
        val splitLine = line.split(indexSeparator)
        val numbers = splitLine[1].split(numbersSeparator)
        val winningNumbers = numberRegex.findAll(numbers[0]).toList().map { it.value.toInt() }.sorted().toList()
        val guessedNumbers = numberRegex.findAll(numbers[1]).toList().map { it.value.toInt() }.sorted().toList()
        return Card(index,winningNumbers,guessedNumbers)
    }

    private fun collectPointsFromCard(card: Card): Long{
        val matchedNumbers = card.guessedNumbers.filter { card.winningNumbers.contains(it) }
        return if (matchedNumbers.isNotEmpty()){
            2.0.pow((matchedNumbers.size - 1).toDouble()).toLong()
            } else {
            0
        }
    }


    fun secondTask(dataLoader: ResourceDataLoader): Int {
        val cards = mutableMapOf<Int,Int>() //index: amount

        dataLoader.loadLinesFromFileFlux()
            .index()
            .map {destructureCard(it!!.t1.toInt(), it.t2)}
            .doOnNext{
                cards.computeIfPresent(it.index) { _, mapValue -> mapValue + 1}
                cards.computeIfAbsent(it.index) { _ -> 1 }
                val currentValue = cards[it.index]?:1
                it.matchedNumbers().forEachIndexed { matchedIndex, _ ->
                    val targetIndex = it.index+matchedIndex+1
                    cards.computeIfPresent(targetIndex) { _, mapValue -> mapValue + currentValue }
                    cards.computeIfAbsent(targetIndex) { _ -> currentValue }
                }
            }
            .subscribe()


        return cards.values.sum()
    }

}

data class Card(val index: Int, val winningNumbers: List<Int>,val guessedNumbers:List<Int>) {
    fun matchedNumbers(): List<Int> {
        return  guessedNumbers.filter { winningNumbers.contains(it) }
    }
}
