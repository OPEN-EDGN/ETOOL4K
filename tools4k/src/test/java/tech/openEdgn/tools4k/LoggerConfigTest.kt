package tech.openEdgn.tools4k

import org.junit.Test


internal class LoggerConfigTest{
    @Test
    fun test() {
        val resource = javaClass.getResource("/log4k-default.properties")
        val openStream = resource.openStream()
        println(openStream.readBytes().toString(Charsets.UTF_8))
    }
}