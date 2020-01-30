package tech.openEdgn.security4k.rsa

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class RsaEncryptTest{
    @Test
    fun test(): Unit {
        val rsaKeyPair = RsaKeyPair(512)
        val rsaPrivateEncrypt = RsaPrivateEncrypt(rsaKeyPair.privateKeyText)
        val rsaPublicEncrypt = RsaPublicEncrypt(rsaKeyPair.publicKeyText)
        val data = Random.nextBytes(10).toString()
        val encryptText = rsaPrivateEncrypt.encryptText(data)
        val decryptText = rsaPublicEncrypt.decryptText(encryptText)
        assertEquals(data,decryptText)
        val encryptText1 = rsaPublicEncrypt.encryptText(data)
        val decryptText2 = rsaPrivateEncrypt.decryptText(encryptText1)
        assertEquals(data,decryptText2)

    }
}