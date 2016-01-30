package org.jetbrains.spek.api

import org.junit.Test as test
import org.junit.Before as before
import org.mockito.*
import org.junit.*
import org.jetbrains.spek.console.ActionStatusReporter
import org.jetbrains.spek.console.WorkflowReporter
import org.jetbrains.spek.console.CompositeWorkflowReporter

class ListenerTest {
    val firstStepListener = Mockito.mock(ActionStatusReporter::class.java)
    val firstListener = Mockito.mock(WorkflowReporter::class.java)!!

    val secondStepListener = Mockito.mock(ActionStatusReporter::class.java)
    val secondListener = Mockito.mock(WorkflowReporter::class.java)!!

    val throwable = RuntimeException("Test Exception")

    val multicaster = CompositeWorkflowReporter()

    @before fun setup() {
        multicaster.addListener(firstListener)
        multicaster.addListener(secondListener)
    }

    @test fun givenExecution() {
        //given two listener with following conditions.
        BDDMockito.given(firstListener.given("Spek", "Test"))!!.willReturn(firstStepListener)
        BDDMockito.given(secondListener.given("Spek", "Test"))!!.willReturn(secondStepListener)
        val stepListener = multicaster.given("Spek", "Test")

        //when execution started.
        stepListener.started();
        //then "executionStarted" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.started()
        Mockito.verify(secondStepListener)!!.started()

        //when execution completed.
        stepListener.completed()
        //then "executionCompleted" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.completed()
        Mockito.verify(secondStepListener)!!.completed()

        //when execution execution failed.
        stepListener.failed(throwable)
        //then "executionFailed" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.failed(throwable)
        Mockito.verify(secondStepListener)!!.failed(throwable)
    }

    @test fun onExecution() {
        //given two listener with following conditions.
        BDDMockito.given(firstListener.on("Spek", "Test", "Test"))!!.willReturn(firstStepListener)
        BDDMockito.given(secondListener.on("Spek", "Test", "Test"))!!.willReturn(secondStepListener)
        val stepListener = multicaster.on("Spek", "Test", "Test")

        //when execution started.
        stepListener.started();
        //then "executionStarted" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.started()
        Mockito.verify(secondStepListener)!!.started()

        //when execution completed.
        stepListener.completed()
        //then "executionCompleted" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.completed()
        Mockito.verify(secondStepListener)!!.completed()

        //when execution execution failed.
        stepListener.failed(throwable)
        //then "executionFailed" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.failed(throwable)
        Mockito.verify(secondStepListener)!!.failed(throwable)
    }

    @test fun itExecution() {
        //given two listener with following conditions.
        BDDMockito.given(firstListener.it("Spek", "Test", "Test", "Test"))!!.willReturn(firstStepListener)
        BDDMockito.given(secondListener.it("Spek", "Test", "Test", "Test"))!!.willReturn(secondStepListener)

        val stepListener = multicaster.it("Spek", "Test", "Test", "Test")

        //when execution started.
        stepListener.started();
        //then "executionStarted" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.started()
        Mockito.verify(secondStepListener)!!.started()

        //when execution completed.
        stepListener.completed()
        //then "executionCompleted" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.completed()
        Mockito.verify(secondStepListener)!!.completed()

        //when execution execution failed.
        stepListener.failed(throwable)
        //then "executionFailed" in both step listeners must be called.
        Mockito.verify(firstStepListener)!!.failed(throwable)
        Mockito.verify(secondStepListener)!!.failed(throwable)
    }
}
