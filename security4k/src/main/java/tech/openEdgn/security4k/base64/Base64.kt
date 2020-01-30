package tech.openEdgn.security4k.base64

import java.io.ByteArrayOutputStream
import java.lang.IndexOutOfBoundsException
import java.nio.charset.Charset
import kotlin.math.abs

/**
 * 注意：Base64 算法并非加密算法
 *
 * 此 Base64 算法不是位运算方案，效率较低！！！
 */
object Base64 {

    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

    /**
     * base64 编码方案
     *
     * @param data ByteArray 原始数据
     * @return String 编码后的数据
     */
    fun encode(data: ByteArray): String {
        val input = StringBuilder()
        for (d in data) {
            val d1 = if (d < 0) {
                abs( 256 + d )
            } else {
                d.toInt()
            }
            input.append(String.format("%-8s", d1.toString(2).reversed()).replace(" ", "0").reversed())
        }
        var formatSize = input.length / 6 * 6
        val appendSize = 3 - ((input.length - formatSize) / 2)
        if (appendSize in 1..2) {
            for (i in 0 until appendSize) {
                input.append("00")
            }
            formatSize = input.length
        }
        val output = StringBuilder()
        for (index in 0 until formatSize step 6) {
            output.append(chars[input.substring(index, index + 6).toInt(2)])
        }
        if (appendSize in 1..2) {
            for (i in 0 until appendSize) {
                output.append("=")
            }
        }
        return output.toString()
    }

    /**
     * base64 解码方案
     *
     * @param data String 编码数据
     * @return ByteArray 原始数据
     */
    fun decode(data: String): ByteArray {
        val size = when {
            data.endsWith("==") -> 2
            data.endsWith("=") -> 1
            else -> 0
        }
        val input = StringBuilder()
        for(index in 0 until (data.length -size)){
            val item = when (val value = data[index]) {
                in 'A'..'Z' -> value - 'A' + 0
                in 'a'..'z' -> value - 'a' + 26
                in '0'..'9' -> value - '0' + 26 + 26
                '+' -> 26 + 26 + 10
                '/' -> 26 + 26 + 10 + 1
                else -> throw IndexOutOfBoundsException("这不是一个base64字段：[$value]")
            }
            val reversed = String.format("%-6s", item
                    .toString(2).reversed()).replace(" ", "0").reversed()
            input.append(reversed)
        }
        input.setLength(input.length - (size * 2))
        val output = ByteArrayOutputStream()
        for(index in 0 until (input.length)  step 8){
            output.write(input.substring(index, index + 8).toInt(2))
        }
        return output.toByteArray()
    }


    /**
     * 编码一段文本
     *
     * @param data String 原始文本
     * @param charset Charset 编码方式
     * @return String 编码后 base64 字符串
     */
    fun encodeText(data: String, charset: Charset = Charsets.UTF_8) = encode(data.toByteArray(charset))
    /**
     * 解码一段文本
     *
     * @param encryptedText 编码后 base64 字符串
     * @param charset Charset 编码方式
     * @return String 解码后文本
     */
    fun decodeText(encryptedText: String, charset: Charset = Charsets.UTF_8) = decode(encryptedText).toString(charset)

}