package me.bytebeats.polyglot.util

import org.apache.commons.lang.CharSet
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.or
import kotlin.experimental.xor

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 16:43
 * @version 1.0
 * @description TO-DO
 */

class ParamUtils {
    companion object {
        private val hexDigits =
            charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

        fun map2List(params: Map<String, String>): List<NameValuePair> {
            val pairs = mutableListOf<NameValuePair>()
            for ((k, v) in params) {
                pairs.add(BasicNameValuePair(k, v))
            }
            return pairs
        }

        fun concatUrl(url: String, params: Map<String, String>): String {
            if (params.isEmpty()) return url
            val target = StringBuilder(url)
            if (!target.contains("?")) {
                target.append("?")
            }
            for ((k, v) in params) {
                if (!target.endsWith("?")) {
                    target.append("&")
                }
                target.append(k)
                target.append("=")
                target.append(encodeUtf8(v))
            }
            return target.toString()
        }

        private fun encodeUtf8(input: String?): String {
            if (input.isNullOrEmpty()) return ""
            try {
                return URLEncoder.encode(input, "utf-8")
            } catch (e: UnsupportedEncodingException) {
                LogUtils.info(e.message)
            }
            return ""
        }

        fun md5(input: String?): String? {
            if (input == null) return null
            try {
                val msgDigest = MessageDigest.getInstance("MD5")
                val inputBytes = input.toByteArray(Charset.forName("utf-8"))
                msgDigest.update(inputBytes)
                val md5Bytes = msgDigest.digest()
                return bytes2Hex(md5Bytes)
            } catch (e: NoSuchAlgorithmException) {
                LogUtils.info(e.message)
            } catch (e: UnsupportedEncodingException) {
                LogUtils.info(e.message)
            }
            return ""
        }

        private fun bytes2Hex(bytes: ByteArray): String {
            val chars = CharArray(bytes.size * 2)
            var idx = 0
            for (b in bytes) {
                chars[idx++] = hexDigits[(b.toInt() shl 4) and 0xf]
                chars[idx++] = hexDigits[b.toInt() and 0xf]
            }
            return String(chars)
        }
    }
}