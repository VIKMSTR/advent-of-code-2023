package cz.vikk.aoc23.day05

import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.util.function.Tuple2
import kotlin.math.max
import kotlin.math.min

class Day05 {
    private val numberRegex = """(\d+)""".toRegex()

    fun firstTask(dataLoader: ResourceDataLoader): Long {
        var seeds = listOf<Long>()
        dataLoader.loadLinesFromFileFlux().index()
            .bufferUntil {it.t2.isBlank()}
            .map { it.dropLast(1) } //cut the trailing emptyline
            .doOnNext {
                if (it.size == 1){
                    seeds = numberRegex.findAll(it[0].t2).toList().map { it.value.toLong() }
                    println("Seed info saved")
                }
            }
            .filter { it.size > 1 }
            .map { createMapper(it) }
            .subscribe {mapper ->
                val converted = seeds.map { mapper.findRangeForInput(it)?.convert(it)?: it }.toList()
                seeds = converted
            }
        return seeds.min()
    }

    private fun createMapper(data: List<Tuple2<Long,String>>): Mapper{
        val name = data[0].t2
         val ranges = data.drop(1).map {
            val conversionNumbers=numberRegex.findAll(it.t2).toList().map { it.value.toLong() }
            MappingRange(LongRange(conversionNumbers[1],conversionNumbers[1]+ (conversionNumbers[2]-1)),conversionNumbers[0])
        }
        return Mapper(name,ranges)
    }

    fun secondTask(dataLoader: ResourceDataLoader): Long{
        var ranges = listOf<LongRange>()
        var mappers = mutableListOf<Mapper>()
        dataLoader.loadLinesFromFileFlux().index()
            .bufferUntil {it.t2.isBlank()}
            .map { it.dropLast(1) } //cut the trailing emptyline
            .doOnNext {
                if (it.size == 1){
                    val (starts,ends) = numberRegex.findAll(it[0].t2).toList().map { it.value.toLong() }.withIndex().partition { it.index % 2 == 0 }
                    println("Seed info saved")
                    ranges = starts.withIndex().map { LongRange(it.value.value,(it.value.value+ (ends[it.index].value-1))) }
                }
            }
            .filter { it.size > 1 }
            .map { createMapper(it) }
            .subscribe {mapper ->
                mappers.add(mapper)
                //old way
               // val processed = ranges.map { mapper.findRangeForInputRange(it)?.splitRange(it)?: listOf(it) }.flatten()
                // multi matches?
                val process = ranges.map {range ->
                   val mappingRanges = mapper.findRangesForInputRange(range)
                   if (mappingRanges.isEmpty()){
                       listOf(range)
                   }else{
                       if (mappingRanges.size == 2) {
                           mappingRanges.sortedBy { it.inputRange.first }
                           listOf(mappingRanges[0].splitRange(range.first..mappingRanges[0].inputRange.last),
                               mappingRanges[1].splitRange(mappingRanges[0].inputRange.first.. range.last),
                               ).flatten()
                       }else if (mappingRanges.size == 1) {

                           mappingRanges[0].splitRange(range)
                       }else{
                           println("WARNING - MORE THAN 2 APPLICABLE MATCHES")
                           mappingRanges.map { it.splitRange(range) }.flatten()
                       }
                   }
                }.flatten()
                //ranges = processed
                ranges = process

            }
        val incorrects = ranges.minOf { it.first }
        return ranges.filter { it.first != 0L }.minByOrNull { it.first }!!.first //lol dont know how do we get the zero starting range. Further investigation required
    }

}

data class Mapper(val name: String,val ranges: List<MappingRange>){
    fun findRangeForInput(input: Long) : MappingRange?{
        return ranges.firstOrNull() { it.inputRange.contains(input) }
    }

    fun findRangeForInputRange(range: LongRange): MappingRange?{
        val matchingRanges = ranges.filter { rangesIntersect(range, it.inputRange) }

        if (matchingRanges.size > 1){
            println("More matches? $matchingRanges for $range")
        }
        return ranges.firstOrNull() {rangesIntersect(range, it.inputRange)}
    }

    fun findRangesForInputRange(range: LongRange): List<MappingRange>{
       return ranges.filter { rangesIntersect(range, it.inputRange) }
    }

    fun rangesIntersect(range: LongRange, mapperRange: LongRange): Boolean {
        //return mapperRange.contains(range.first) && mapperRange.contains(range.last)
        return mapperRange.contains(range.first) || mapperRange.contains(range.last)
    }
}

data class MappingRange(val inputRange: LongRange, val startingOutput: Long) {
    fun convert(input: Long): Long {
        return (input - inputRange.first) + startingOutput
    }

    fun splitRange(input: LongRange): List<LongRange>{
        val ranges = mutableListOf<LongRange>()
        val toConversion = (max(input.first,inputRange.first)) .. min(input.last,inputRange.last)
        if (input.first < inputRange.first) {
            ranges.add( input.first until inputRange.first)
        }
        val converted = convertRange(toConversion)
        ranges.add(converted)
        if (input.last > inputRange.last){
            ranges.add (inputRange.last+1 .. input.last)
        }
        return ranges
    }

    fun convertRange(input: LongRange): LongRange {
        val shiftFirst = input.first - inputRange.first
        val shiftLast = input.last- inputRange.first
        return (startingOutput + shiftFirst) .. (startingOutput + shiftLast)
        //return LongRange(((input.first - inputRange.first) + startingOutput),((input.last - inputRange.first) + startingOutput))
    }
//13659532

}
