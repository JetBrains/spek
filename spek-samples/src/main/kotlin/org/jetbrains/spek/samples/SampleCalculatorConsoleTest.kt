package org.jetbrains.spek.samples

import org.jetbrains.spek.api.*

class CalculatorConsoleSpecs : Spek({
    given("a calculator") {
        val calculator = SampleCalculator()
        on("calling sum with two numbers") {
            val sum = calculator.sum(2, 4)
            it("should return the result of adding the first number to the second number") {
                shouldEqual(6, sum)
            }
        }
        on("calling subtract with two numbers") {
            val subtract = calculator.subtract(4, 2)
            it("should return the result of subtracting the second number from the first number") {
                shouldEqual(2, subtract)
            }
        }
    }
})

