package tech.openEdgn.security4k

import tech.openEdgn.security4k.base64.Base64
import java.nio.charset.Charset

fun String.base64encrypt(charset: Charset = Charsets.UTF_8) = Base64.encodeText(this,charset)

fun String.base64decrypt(charset: Charset = Charsets.UTF_8) = Base64.decodeText(this,charset)


fun ByteArray.base64encrypt() = Base64.encode(this)

fun String.base64decrypt() = Base64.decode(this)
