package org.spek.junit.impl

import org.junit.Test as test
import org.spek.junit.api.JUnitSpek
import org.junit.runner.notification.RunNotifier
import org.mockito.Mockito
import org.mockito.ArgumentCaptor
import org.junit.runner.Description
import org.spek.api.skip
import kotlin.test.assertEquals

public class SkipTest {
    val notifier = Mockito.mock(javaClass<RunNotifier>())

    test fun skipIt() {

        JSpec(javaClass<SkipItSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(1))!!.fireTestIgnored(captor.capture())
        assertEquals("Skipped. Reason: not ready yet(given a situation : an event : should A)",
                captor.getValue()!!.getDisplayName())

    }

    test fun skipOn() {

        JSpec(javaClass<SkipOnSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(1))!!.fireTestIgnored(captor.capture())
        assertEquals("Skipped. Reason: not ready yet(given a situation : an event)",
                captor.getValue()!!.getDisplayName())

    }

    test fun skipGiven() {

        JSpec(javaClass<SkipGivenSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(1))!!.fireTestIgnored(captor.capture())
        assertEquals("Skipped. Reason: for some reason(given a situation)",
                captor.getValue()!!.getDisplayName())

    }

    test fun skipSpek() {

        JSpec(javaClass<SkipSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(1))!!.fireTestIgnored(captor.capture())
        assertEquals("org.spek.junit.impl.SkipSpek",
                captor.getValue()!!.getDisplayName())

    }

    test fun pendingIt() {

        JSpec(javaClass<PendingItSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(2))!!.fireTestIgnored(captor.capture())

        assertEquals("Pending. Reason: not implemented yet(given a situation : an event : should A)",
                captor.getAllValues()!!.get(0).getDisplayName())

        assertEquals("Pending. Reason: not given(given a situation : an event : should B)",
                captor.getAllValues()!!.get(1).getDisplayName())
    }

    test fun pendingOn() {

        JSpec(javaClass<PendingOnSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(2))!!.fireTestIgnored(captor.capture())

        assertEquals("Pending. Reason: not given(given a situation : an event)",
                captor.getAllValues()!!.get(0).getDisplayName())

        assertEquals("Pending. Reason: not implemented yet(given a situation : another event)",
                captor.getAllValues()!!.get(1).getDisplayName())

    }

    test fun pendingGiven() {

        JSpec(javaClass<PendingGivenSpek>()).run(notifier)

        val captor = ArgumentCaptor<Description>()
        Mockito.verify(notifier, Mockito.times(2))!!.fireTestIgnored(captor.capture())

        assertEquals("Pending. Reason: for a reason(given a situation)",
                captor.getAllValues()!!.get(0).getDisplayName())

        assertEquals("Pending. Reason: not given(given another situation)",
                captor.getAllValues()!!.get(1).getDisplayName())

    }
}

class SkipItSpek: JUnitSpek() {{
    given("a situation") {
        on("an event") {
            it("should A") {
                skip("not ready yet")
            }

            it("should B") { }
        }
    }
}
}

class SkipOnSpek: JUnitSpek() {{
    given("a situation") {
        on("an event") {
            skip("not ready yet")

            it("should A") { }
        }

        on("another event") {

            it("should B") { }
        }
    }
}
}

class SkipGivenSpek: JUnitSpek() {{
    given("a situation") {
        skip("for some reason")

        on("an event") {
            it("should A") { }
        }

        on("another event") {
            it("should B") {
                pending("ok!!!")
            }
        }
    }
}
}

skip("for some reason") class SkipSpek: JUnitSpek() {{
}
}

class PendingItSpek: JUnitSpek() {{
    given("a situation") {
        on("an event") {
            it("should A") {
                pending("not implemented yet")
            }

            //default pending.
            it("should B")
        }
    }
}
}

class PendingOnSpek: JUnitSpek() {{
    given("a situation") {
        //default pending.
        on("an event")

        on("another event") {
            pending("not implemented yet")
        }
    }
}
}

class PendingGivenSpek: JUnitSpek() {{
    given("a situation") {
        pending("for a reason")
    }

    //default pending.
    given("another situation")
}
}
