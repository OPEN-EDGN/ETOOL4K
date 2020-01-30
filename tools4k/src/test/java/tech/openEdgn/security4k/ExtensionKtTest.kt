package tech.openEdgn.security4k

import org.junit.Assert.*
import org.junit.Test
import tech.openEdgn.tools4k.METHOD
import tech.openEdgn.tools4k.calculatedHash
import tech.openEdgn.tools4k.readText
import tech.openEdgn.tools4k.toPrintString
import java.io.File
import java.io.IOException
import kotlin.random.Random

class ExtensionKtTest{
    @Test
    fun readText() {
        val test = Random.toString()
        assertEquals(test.byteInputStream().readText(),test)
    }

    @Test
    fun toPrintString() {
        assertTrue(IOException("test").toPrintString().lines().size > 1)
    }

    @Test
    fun calculatedHash() {
        for (value in METHOD.values()) {
            println("$value = ${File("D:\\Env\\blog.tar.gz").calculatedHash(value)}")
        }
    }

}