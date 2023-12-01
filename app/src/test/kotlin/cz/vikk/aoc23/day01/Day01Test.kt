package cz.vikk.aoc23.day01


import cz.vikk.aoc23.common.ResourceDataLoader
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class Day01Test {

    @Test
    fun `test first task with demo data`(){
        val tested = Day01()
        val result = tested.firstTask(ResourceDataLoader("/day01/firstTaskDemo.txt"))
        StepVerifier.create(result).expectNext(142).expectComplete().verify()
    }

    @Test
    fun `run first task with real data`(){
        val tested = Day01()
        val result = tested.firstTask(ResourceDataLoader("/day01/realData.txt"))
        result.subscribe { println("Result of first task of day one is $it") }
    }

    @Test
    fun `test second task with demo data`(){
        val tested = Day01()
        val result = tested.secondTask(ResourceDataLoader("/day01/secondTaskDemo.txt"))
        StepVerifier.create(result).expectNext(281).expectComplete().verify()
    }

    @Test
    fun `run second task with real data`(){
        val tested = Day01()
        val result = tested.secondTask(ResourceDataLoader("/day01/realData.txt"))
        result.subscribe { println("Result of second task of day one is $it") }
    }

    @Test
    fun `test second task with real data`(){
        val tested = Day01()
        val result = tested.secondTask(ResourceDataLoader("/day01/realData.txt"))
        StepVerifier.create(result).expectNext(57345).expectComplete().verify()
    }
}
