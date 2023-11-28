package cz.vikk.aoc23.example

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import cz.vikk.aoc23.common.ResourceDataLoader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.test.assertEquals
import kotlin.test.fail

class DayZeroTest {

    @Test
    fun testZeroSum(){
        val tested = DayZero()
        when (val result = tested.taskZeroSum(ResourceDataLoader("/example/dayzero.txt"))) {
            is Left -> fail(result.value.errorMessage)
            is Right -> assertEquals(147,result.value)
        }
    }

    @Test
    fun testZeroSumFlux(){
        val tested = DayZero()
        val mono = tested.taskZeroSumFlux(ResourceDataLoader("/example/dayzero.txt"))
        mono.subscribe {
            assertEquals(147,it)
        }
    }
}
