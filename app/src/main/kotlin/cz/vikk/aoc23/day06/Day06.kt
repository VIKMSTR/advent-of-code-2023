package cz.vikk.aoc23.day06

import cz.vikk.aoc23.common.ResourceDataLoader

class Day06 {
    private val numberRegex = """(\d+)""".toRegex()

    fun firstTask(dataLoader: ResourceDataLoader): Long {
        dataLoader.loadLinesFromFile().fold(
            { println("Error:" + it.errorMessage)
            return -1
            },
            {
                val times = numberRegex.findAll(it[0]).map { it.value.toInt()}.toList()
                val distances = numberRegex.findAll(it[1]).map { it.value.toInt()}.toList()
                val races = times.zip(distances)
                return races.map {
                    val time = it.first
                    val distance = it.second
                    ((time - ((1..<time).first { (time - it) * it > distance } * 2)) + 1).toLong()
                }.reduce(Long::times)
            }
        )
    }

    fun secondTask(dataLoader: ResourceDataLoader): Long {
        dataLoader.loadLinesFromFile().fold(
            { println("Error:" + it.errorMessage)
                return -1
            },
            {
                val time = numberRegex.findAll(it[0]).map { matchResult -> matchResult.value }.joinToString(separator = "").toLong()
                val distance = numberRegex.findAll(it[1]).map { matchResult -> matchResult.value }.joinToString(separator = "").toLong()
                return (time - ((1..<time).first { (time - it) * it > distance } * 2L)) + 1L
            }
        )
    }
}
