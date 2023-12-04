package cz.vikk.aoc23.day04

import cz.vikk.aoc23.common.ResourceDataLoader
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun `test first task with demo data`(){
        val tested = Day04()
        val result = tested.firstTask(ResourceDataLoader("/day04/firstTaskDemo.txt"))
        StepVerifier.create(result).expectNext(13).expectComplete().verify()
    }

    @Test
    fun `run first task with real data`(){
        val tested = Day04()
        val result = tested.firstTask(ResourceDataLoader("/day04/realData.txt"))
        result.subscribe { println("Result of first task of day four is $it") }
    }

    @Test
    fun `test second task with demo data`(){
        val tested = Day04()
        val result = tested.secondTask(ResourceDataLoader("/day04/firstTaskDemo.txt"))
        assertEquals(30,result)
    }

    @Test
    fun `run second task with real data`(){
        val tested = Day04()
        val result = tested.secondTask(ResourceDataLoader("/day04/realData.txt"))
        println("Result of first task of day four is $result")
    }
}
