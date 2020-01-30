package tech.openEdgn.security4k.rsa

import tech.openEdgn.security4k.base64.Base64
import java.io.InputStream
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher

class RsaPrivateEncrypt(rsaKey: String, maxBlockSize: Int = -1) :
    RsaEncrypt(rsaKey, keyType = Cipher.PRIVATE_KEY, maxBlockSize = maxBlockSize) {

    /**
     * 为数据签名
     *
     * @param plainData ByteArray
     * @param algorithm String
     * @return String
     */
    fun signPlainData(plainData: ByteArray, algorithm: String = defaultAlgorithm): String {
        val signature = newSignature(algorithm)
        signature.update(plainData)
        return Base64.encode(signature.sign())
    }

    /**
     * 为流签名
     *
     * @param inputStream InputStream
     * @param algorithm String
     * @return String
     */
    fun signInputStream(inputStream: InputStream, algorithm: String = defaultAlgorithm): String {
        val signature = signature(algorithm, inputStream)
        return Base64.encode(signature.sign())
    }



    override fun newSignature(algorithm: String): Signature {
        val pkcs8KeySpec = PKCS8EncodedKeySpec(rsaKeyData)
        val keyFactory = KeyFactory.getInstance(transformation)
        val privateK = keyFactory.generatePrivate(pkcs8KeySpec)
        val signature = Signature.getInstance(algorithm)
        signature.initSign(privateK)
        return signature
    }
}