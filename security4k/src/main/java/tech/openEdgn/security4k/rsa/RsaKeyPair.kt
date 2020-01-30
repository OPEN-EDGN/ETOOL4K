package tech.openEdgn.security4k.rsa

import tech.openEdgn.security4k.base64.Base64
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

class RsaKeyPair(keySize:Int = 1024,random: SecureRandom =  SecureRandom.getInstanceStrong()) {
    private var privateKey: RSAPrivateKey
    private var publicKey: RSAPublicKey

    init {
        val keyPairGen = KeyPairGenerator.getInstance("RSA")
        keyPairGen.initialize(keySize, random)
        val keyPair = keyPairGen.generateKeyPair()
        publicKey = keyPair.public as RSAPublicKey
        privateKey = keyPair.private as RSAPrivateKey
    }

    /**
     * 得到公钥
     */
    val publicKeyText:String
        get()  = Base64.encode(publicKey.encoded)


    /**
     * 得到私钥
     */
    val privateKeyText:String
        get()  = Base64.encode(privateKey.encoded)

    override fun toString(): String {
        return "{\r\n\t公钥：\r\n\t[$publicKeyText]\r\n\t私钥：\r\n\t[$privateKeyText]\r\n}"
    }
}