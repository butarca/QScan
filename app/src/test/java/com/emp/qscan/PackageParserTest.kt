package com.emp.qscan

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PackageParserTest {
    @Test
    fun validPayload() {
        val p = parsePackage("""{"v":1,"id":"PKG-000123","date":"2026-11-26","task":"MOVE","loc":"A-03-2","note":"Krhko"}""")
        assertEquals(PackageInfo("PKG-000123", "2026-11-26", Task.MOVE, "A-03-2", "Krhko"), p)
    }

    @Test
    fun optionalFieldsMissing() {
        val p = parsePackage("""{"v":1,"id":"PKG-1","task":"inspect"}""")
        assertEquals(PackageInfo("PKG-1", null, Task.INSPECT, null, null), p)
    }

    @Test
    fun rejectsInvalid() {
        listOf(
            "",
            "hello world",
            "[1,2]",
            """{"v":2,"id":"PKG-1","task":"MOVE"}""",
            """{"id":"PKG-1","task":"MOVE"}""",
            """{"v":1,"task":"MOVE"}""",
            """{"v":1,"id":"  ","task":"MOVE"}""",
            """{"v":1,"id":"PKG-1","task":"DANCE"}""",
            """{"v":1,"id":"PKG-1","task":"MOVE","date":"26.11.2026"}""",
        ).forEach { assertNull(it, parsePackage(it)) }
    }
}
