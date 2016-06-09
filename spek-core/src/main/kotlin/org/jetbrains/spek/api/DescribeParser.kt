package org.jetbrains.spek.api

import java.util.concurrent.CopyOnWriteArrayList

class DescribeParser() : DescribeBody {
    val befores: MutableList<() -> Unit> = CopyOnWriteArrayList()
    val afters: MutableList<() -> Unit> = CopyOnWriteArrayList()
    val children: MutableList<SpekTree> = CopyOnWriteArrayList()

    fun children(): List<SpekTree> {
        if (children.isEmpty()) {
            throw RuntimeException(this.javaClass.canonicalName + ": no tests found")
        }
        return children
    }

    override fun describe(description: String, evaluateBody: DescribeBody.() -> Unit) {
        val inner = DescribeParser()
        inner.evaluateBody()

        children.add(SpekTree(
                description,
                ActionType.DESCRIBE,
                SpekStepRunner(befores, afters),
                inner.children))
    }

    override fun xdescribe(description: String, evaluateBody: DescribeBody.() -> Unit) {
        val inner = DescribeParser()
        inner.evaluateBody()

        children.add(SpekTree(
                description,
                ActionType.DESCRIBE,
                SpekIgnoreRunner(),
                listOf()))
    }

    override fun xit(description: String, @Suppress("UNUSED_PARAMETER") assertions: () -> Unit) {
        children.add(SpekTree(
                "it " + description,
                ActionType.IT,
                SpekIgnoreRunner(),
                listOf()))
    }

    override fun it(description: String, assertions: () -> Unit) {
        children.add(SpekTree(
                "it " + description,
                ActionType.IT,
                SpekStepRunner(befores, afters, assertions),
                listOf()
        ))
    }

    override fun fdescribe(description: String, evaluateBody: DescribeBody.() -> Unit) {
        val inner = DescribeParser()
        inner.evaluateBody()

        children.add(SpekTree(
                description,
                ActionType.DESCRIBE,
                SpekStepRunner(befores, afters),
                inner.children,
                true
        ))
    }

    override fun fit(description: String, assertions: () -> Unit) {
        children.add(SpekTree(
                "it " + description,
                ActionType.IT,
                SpekStepRunner(befores, afters, assertions),
                listOf(),
                true
        ))
    }


    override fun beforeEach(actions: () -> Unit) {
        befores.add(actions)
    }

    override fun afterEach(actions: () -> Unit) {
        afters.add(actions)
    }
}