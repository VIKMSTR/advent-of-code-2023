package cz.vikk.aoc23.day06

import cz.vikk.aoc23.common.ResourceDataLoader
import cz.vikk.aoc23.day05.Day05
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals

class Day06Test {
    @Test
    fun `test first task with demo data`(){
        val tested = Day06()
        val result = tested.firstTask(ResourceDataLoader("/day06/firstTaskDemo.txt"))
        assertEquals(288,result)
    }

    @Test
    fun `run first task with real data`(){
        val tested = Day06()
        val result = tested.firstTask(ResourceDataLoader("/day06/realData.txt"))
        println(result)
    }

    @Test
    fun `test second task with demo data`(){
        val tested = Day06()
        val result = tested.secondTask(ResourceDataLoader("/day06/firstTaskDemo.txt"))
        assertEquals(71503,result)
    }

    @Test
    fun `test second task with real data`(){
        val tested = Day06()
        val result = tested.secondTask(ResourceDataLoader("/day06/realData.txt"))
        println(result)
    }


}
