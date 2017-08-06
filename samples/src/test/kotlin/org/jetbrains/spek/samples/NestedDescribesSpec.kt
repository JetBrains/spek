package org.jetbrains.spek.samples

import org.jetbrains.samples.Calculator
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import kotlin.test.assertEquals

class NestedDescribesSpec : Spek({
    describe("a calculator") {
        val calculator = Calculator()
        var result = 0

        describe("addition") {
            beforeEachTest {
                result = calculator.add(2, 4);
            }

            it("should return the result of adding the first number to the second number") {
                assertEquals(6, result)
            }
            it("should fail") {
                assertEquals(6, result)
            }
        }

        describe("subtraction") {
            beforeEachTest {
                result = calculator.subtract(4, 2)
            }

            it("should return the result of subtracting the second number from the first number") {
                assertEquals(2, result)
            }
        }
    }
})

