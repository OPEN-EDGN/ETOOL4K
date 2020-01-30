package tech.openEdgn.security4k.rsa

import tech.openEdgn.security4k.base.ReversibleEncryptionFactory
import tech.openEdgn.security4k.base64.Base64
import java.io.InputStream
import java.lang.IndexOutOfBoundsException
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


abstract class RsaEncrypt @JvmOverloads constructor(
        rsaKey: String,
        private val keyType: Int = Cipher.PRIVATE_KEY,
        private var maxBlockSize: Int = -1
) : ReversibleEncryptionFactory {

    protected val defaultAlgorithm = "SHA512WithRSA"
    /**
     * 密钥由base64解析成原始数据
     */
    protected val rsaKeyData by lazy {
        Base64.decode(rsaKey)
    }
    protected val transformation: String = "RSA"
    private val algorithm = "RSA/ECB/PKCS1Padding"

    /**
     * 加密后的数据长度
     */
    private val encryptedBlockSize: Int

    init {
        encryptedBlockSize = newInitCipher(Cipher.ENCRYPT_MODE).getOutputSize(1)
        if (maxBlockSize == -1) {
            maxBlockSize = encryptedBlockSize - 11
        }
    }

    private fun newInitCipher(mode: Int): Cipher {
        val cipher = Cipher.getInstance(algorithm)
        val keyFactory = KeyFactory.getInstance(transformation)
        val key = keyFactory.let {
            if (keyType == Cipher.PRIVATE_KEY) {
                it.generatePrivate(PKCS8EncodedKeySpec(rsaKeyData))
            } else {
                it.generatePublic(X509EncodedKeySpec(rsaKeyData))
            }
        }
        cipher.init(mode, key)
        return cipher
    }


    override fun calculateCipherLength(plainData: ByteArray, offset: Int, length: Int): Int {
        if (plainData.size < offset + length || offset < 0 || length < 0) {
            throw IndexOutOfBoundsException()
        }
        if (length == 0) {
            return 0
        }
        return ((length / maxBlockSize) + 1) * encryptedBlockSize
    }

    override fun calculatePlainLength(cipherData: ByteArray, offset: Int, length: Int): Int {
        if (cipherData.size < offset + length || offset < 0 || length < 0) {
            throw IndexOutOfBoundsException()
        }
        if (length == 0) {
            return 0
        }
        return length
    }

    private fun process(
            inputData: ByteArray,
            inputDataOffset: Int,
            outputData: ByteArray,
            outputDataOffset: Int,
            length: Int,
            mode: Int,
            blockSize: Int

    ): Int {
        if (inputData.size < inputDataOffset + length ||
                length < 0 || inputDataOffset < 0 || outputDataOffset < 0 ||
                outputData.size - outputDataOffset < length
        ) {
            throw IndexOutOfBoundsException(
                    "${inputData.size} < $inputDataOffset + $length ||" +
                            " $length < 0 || $inputDataOffset < 0 || $outputDataOffset < 0 ||" +
                            " ${outputData.size} - $outputDataOffset < $length"
            )
        }
        if (length == 0) {
            return 0
        }
        val cipher = newInitCipher(mode)
        var inputOffset = inputDataOffset
        var outputOffset = outputDataOffset
        var outputSize = 0
        var inputReadSize = 0
        while (inputReadSize < length) {
            val whileCopySize = length - inputReadSize
            var blokeLen = blockSize
            if (whileCopySize < blockSize) {
                blokeLen = whileCopySize
            }
            //尾部数据
            val resultSize = cipher.doFinal(inputData, inputOffset, blokeLen, outputData, outputOffset)
            outputOffset += resultSize
            outputSize += resultSize
            inputReadSize += blokeLen
            inputOffset += blokeLen
        }
        return outputSize

    }

    override fun encrypt(
            inputData: ByteArray,
            inputDataOffset: Int,
            outputData: ByteArray,
            outputDataOffset: Int,
            length: Int
    ) = process(inputData, inputDataOffset, outputData, outputDataOffset, length, Cipher.ENCRYPT_MODE, maxBlockSize)

    override fun decrypt(
            inputData: ByteArray,
            inputDataOffset: Int,
            outputData: ByteArray,
            outputDataOffset: Int,
            length: Int
    ) = process(
            inputData,
            inputDataOffset,
            outputData,
            outputDataOffset,
            length,
            Cipher.DECRYPT_MODE,
            encryptedBlockSize
    )

    protected fun signature(algorithm: String, inputStream: InputStream): Signature {
        val signature = newSignature(algorithm)
        val b = ByteArray(4096)
        var readSize: Int
        while (true) {
            readSize = inputStream.read(b, 0, b.size)
            if (readSize == -1) {
                break
            }
            signature.update(b, 0, readSize)
        }
        inputStream.close()
        return signature
    }
    abstract fun newSignature(algorithm: String): Signature

}