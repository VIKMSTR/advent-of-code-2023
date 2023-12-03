package cz.vikk.aoc23.day03

import cz.vikk.aoc23.common.ResourceDataLoader
import reactor.test.StepVerifier
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun `test the first task of day three with demo data`(){
        val tested = Day03()
        val result = tested.firstTask(ResourceDataLoader("/day03/firstTaskDemo.txt"))
        assertEquals(4361,result)
    }

    @Test
    fun `run the first task of day three with real data`(){
        val tested = Day03()
        val result = tested.firstTask(ResourceDataLoader("/day03/realData.txt"))
        println("Result of first task of day three is $result")
    }

    @Test
    fun `test the second task of day three with demo data`(){
        val tested = Day03()
        val result = tested.secondTask(ResourceDataLoader("/day03/firstTaskDemo.txt"))
        assertEquals(467835,result)
    }

    @Test
    fun `run the second task of day three with real data`(){
        val tested = Day03()
        val result = tested.secondTask(ResourceDataLoader("/day03/realData.txt"))
        println("Result of second task of day three is $result")
    }
}
