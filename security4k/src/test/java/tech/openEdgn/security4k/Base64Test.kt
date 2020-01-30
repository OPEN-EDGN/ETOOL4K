package tech.openEdgn.security4k

import org.junit.Test
import tech.openEdgn.security4k.base64.Base64
import java.util.*

class Base64Test{
    @Test
    fun test(): Unit {
//        println(String.format("%-8s", 11.toByte().toString(2).reversed()).replace(" ","0").reversed())
//        for (i in 0 until  12){
//            println(i)
//        }

        val data = "a".toByteArray(Charsets.UTF_8)
        val message = Base64.encode(data)
        println(message)
        val decode = Base64.decode(message)
        println(decode.toString(Charsets.UTF_8))
        println(Arrays.toString(data))
        println(Arrays.toString(decode))

    }

}