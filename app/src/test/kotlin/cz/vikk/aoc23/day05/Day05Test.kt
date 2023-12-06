package cz.vikk.aoc23.day05

import cz.vikk.aoc23.common.ResourceDataLoader
import cz.vikk.aoc23.day04.Day04
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Day05Test {
    @Test
    fun `test first task with demo data`(){
        val tested = Day05()
        val result = tested.firstTask(ResourceDataLoader("/day05/firstTaskDemo.txt"))
     //   StepVerifier.create(result).expectNext(13).expectComplete().verify()
        assertEquals(35,result)
    }

    @Test
    fun `run first task with demo data`(){
        val tested = Day05()
        val result = tested.firstTask(ResourceDataLoader("/day05/realData.txt"))
        //   StepVerifier.create(result).expectNext(13).expectComplete().verify()
        println(result)
    }

    @Test
    fun `test second task with demo data`(){
        val tested = Day05()
        val result = tested.secondTask(ResourceDataLoader("/day05/firstTaskDemo.txt"))
        assertEquals(46,result)
    }
    @Test

    fun `run second task with real data`(){
        val tested = Day05()
        val result = tested.secondTask(ResourceDataLoader("/day05/realData.txt"))
        assertNotEquals(13659532,result) // wrong value returned
        assertNotEquals(0,result) // wrong value returned
        println(result)
    }
}
