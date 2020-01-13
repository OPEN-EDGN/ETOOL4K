package tech.openEdgn.tools4k

import tech.openEdgn.tools4k.METHOD.*
import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*
import kotlin.text.Charsets.UTF_8

//## With IOStream
/**
 * 读取`InputStream` 下的所有数据并转换成字符串
 *
 * @receiver InputStream `InputStream` 流
 * @param charset Charset 编码类型
 * @return String 返回的字符串
 */
fun InputStream.readText(charset: Charset = UTF_8): String {
    return StringUtils.readInputStream(this,charset)
}

/**
 * 接收当异常完全抛出时所输出的字段
 *
 * @receiver Throwable 需接收的异常
 * @return String 输出的字符串
 */

fun Throwable.toPrintString(): String  = StringUtils.throwableFormat(this)

enum class METHOD(val algorithm: String){
    MD5("md5")
    ,SHA1("sha1")
    ,SHA224("sha-224")
    ,SHA256("sha-256")
    ,SHA384("sha-384")
    ,SHA512("sha-512")
    ,CRC32("crc32")
}

/**
 * 计算 `InputStream` 流的散列值
 *
 * @receiver InputStream `InputStream`流
 * @param method METHOD 散列值类型
 * @return String 散列值字符串
 */
fun InputStream.calculatedHash(method: METHOD):String{
    return when (method) {
        MD5, SHA1,SHA224, SHA256,SHA384, SHA512 -> IOUtils.calculatedHash(this,method.algorithm)
        CRC32 ->  IOUtils.calculatedCrc(this,java.util.zip.CRC32())
    }.toUpperCase( Locale.ENGLISH)
}

/**
 * 计算字符串的散列值
 *
 * @receiver String 原始数据
 * @param method METHOD 散列值类型
 * @param charset Charset 编码
 * @return String 散列值字符串
 */
fun String.calculatedHash(method: METHOD,charset: Charset = UTF_8) =
        this.byteInputStream(charset).calculatedHash(method)

/**
 * 计算文件的散列值
 *
 * 注意：如果文件不存在会抛出异常
 *
 * @receiver File 文件
 * @param method METHOD 散列值类型
 * @return String 散列值字符串
 */
fun File.calculatedHash(method: METHOD) = kotlin.run {
    this.inputStream().calculatedHash(method)
}

//## With IOStream END

//## With String

fun String.md5sum(charset: Charset = UTF_8) = this.calculatedHash(MD5,charset)
fun String.sha1sum(charset: Charset = UTF_8) = this.calculatedHash(SHA1,charset)
fun String.sha224sum(charset: Charset = UTF_8) = this.calculatedHash(SHA224,charset)
fun String.sha256sum(charset: Charset = UTF_8) = this.calculatedHash(SHA256,charset)
fun String.sha384sum(charset: Charset = UTF_8) = this.calculatedHash(SHA384,charset)
fun String.sha512sum(charset: Charset = UTF_8) = this.calculatedHash(SHA512,charset)
fun String.crc32sum(charset: Charset = UTF_8) = this.calculatedHash(CRC32,charset)

//## With String END

//## With File

fun File.md5sum() = this.calculatedHash(MD5)
fun File.sha1sum() = this.calculatedHash(SHA1)
fun File.sha224sum() = this.calculatedHash(SHA224)
fun File.sha256sum() = this.calculatedHash(SHA256)
fun File.sha384sum() = this.calculatedHash(SHA384)
fun File.sha512sum() = this.calculatedHash(SHA512)
fun File.crc32sum() = this.calculatedHash(CRC32)

//## With File END


fun Closeable?.safeClose() {
    try {
        this?.close()
    } catch (e: Exception) {

    }
}