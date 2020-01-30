package tech.openEdgn.security4k.base

import tech.openEdgn.security4k.base64.Base64
import java.lang.Exception
import java.lang.RuntimeException
import java.nio.charset.Charset

interface ReversibleEncryptionFactory  {


    /**
     * 计算密文长度
     *
     */
    fun calculateCipherLength(plainData: ByteArray, offset: Int, length: Int): Int

    /**
     * 计算明文长度
     */
    fun calculatePlainLength(cipherData: ByteArray, offset: Int, length: Int): Int



    @Throws(Exception::class)
    fun encrypt(
        inputData: ByteArray,
        inputDataOffset: Int,
        outputData: ByteArray,
        outputDataOffset: Int,
        length: Int
    ): Int

    /**
     * 解密一段密文
     */

    @Throws(Exception::class)
    fun decrypt(
       inputData: ByteArray,
       inputDataOffset: Int,
       outputData: ByteArray,
       outputDataOffset: Int,
        length: Int
    ): Int

    /**
     * 加密一段字符串
     *
     * @param data String
     * @param charset Charset
     * @return String
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptText(data: String, charset: Charset = Charsets.UTF_8): String {
        val plainData = data.toByteArray(charset)
        val cipherData = ByteArray(calculateCipherLength(plainData, 0, plainData.size))
        val encryptSize = encrypt(plainData, 0, cipherData, 0, plainData.size)
        if (encryptSize < 0) {
            throw RuntimeException("encryption fail! encryptionSize < 0")
        }
        return Base64.encode(cipherData)
    }

    fun decryptText(cipherText: String, charset: Charset = Charsets.UTF_8): String {
        val cipherData = Base64.decode(cipherText)
        val calculatePlainLength = calculatePlainLength(cipherData, 0, cipherData.size)
        val plainData = ByteArray(calculatePlainLength)
        val decryptLength = decrypt(cipherData, 0, plainData, 0, cipherData.size)
        if (decryptLength < 0) {
            throw RuntimeException("encryption fail! decryptLength < 0")
        }
        return String(plainData, 0, decryptLength, charset)
    }



}