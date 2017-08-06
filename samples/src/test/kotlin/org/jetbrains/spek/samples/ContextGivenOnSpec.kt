package org.jetbrains.spek.samples

import org.jetbrains.samples.Calculator
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class ContextGivenOnSpec : Spek({
    given("a calculator") {
        val calculator = Calculator()
        var result: Int

        on("addition") {
            result = calculator.add(2, 4)

            it("should return the result of adding the first number to the second number") {
                assertEquals(6, result)
            }
        }

        on("subtraction") {
            result = calculator.subtract(4, 2)

            it("should return the result of subtracting the second number from the first number") {
                assertEquals(2, result)
            }
        }
    }
})

