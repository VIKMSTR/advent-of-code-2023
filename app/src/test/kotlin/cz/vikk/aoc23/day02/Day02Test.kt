package cz.vikk.aoc23.day02

import cz.vikk.aoc23.common.ResourceDataLoader
import cz.vikk.aoc23.day01.Day01
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class Day02Test {

    @Test
    fun `test first task with demo data`(){
        val tested = Day02()
        val result = tested.firstTask(ResourceDataLoader("/day02/firstTaskDemo.txt"))
        StepVerifier.create(result).expectNext(8).expectComplete().verify()
    }

    @Test
    fun `run first task with real data`(){
        val tested = Day02()
        val result = tested.firstTask(ResourceDataLoader("/day02/realData.txt"))
        result.subscribe { println("Result of first task of day two is $it") }
    }

    @Test
    fun `test second task with demo data`(){
        val tested = Day02()
        val result = tested.secondTask(ResourceDataLoader("/day02/firstTaskDemo.txt"))
        StepVerifier.create(result).expectNext(2286).expectComplete().verify()
    }

    @Test
    fun `run second task with real data`(){
        val tested = Day02()
        val result = tested.secondTask(ResourceDataLoader("/day02/realData.txt"))
        result.subscribe { println("Result of second task of day two is $it") }
    }


}
