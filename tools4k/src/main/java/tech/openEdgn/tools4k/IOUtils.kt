package tech.openEdgn.tools4k

import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.util.zip.Checksum


object IOUtils {
    /**
     * #计算一个流的哈希信息
     *
     * @param input InputStream 标准流
     * @param method String 哈希类型
     * @return String 哈希值
     * @throws IOException 如果发生任意错误都会抛出
     */
    @JvmStatic
    @Throws(IOException::class, Exception::class, RuntimeException::class)
    fun calculatedHash(input: InputStream, method: String): String {
        try {
            val md = MessageDigest.getInstance(method)
            val dataBytes = ByteArray(4096)
            var read: Int
            while (true) {
                read = input.read(dataBytes)
                if (read == -1) {
                    break
                }
                md.update(dataBytes, 0, read)
            }
            val bytes = md.digest()
             return BigInteger(1, bytes).toString(16).toUpperCase()
        } catch (e: Exception) {
            input.safeClose()
            throw e
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun calculatedCrc(inputStream: InputStream,checksum: Checksum): String {
        try {
            val dataBytes = ByteArray(2048)
            var read: Int
            while (true) {
                read = inputStream.read(dataBytes)
                if (read == -1) {
                    break
                }
                checksum.update(dataBytes, 0, read)
            }
            inputStream.close()
            return checksum.value.toString(16).toUpperCase()
        } catch (e: Exception) {
            inputStream.safeClose()
            throw e
        }
    }
}