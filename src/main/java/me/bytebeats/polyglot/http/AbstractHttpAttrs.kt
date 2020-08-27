package me.bytebeats.polyglot.http

import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.lang.Lang
import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.IOException

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:14
 * @version 1.0
 * @description AbstractHttpAttrs is an abstract base class associated with HTTP.
 */

abstract class AbstractHttpAttrs(
    open val url: String,
    val formData: MutableMap<String, String> = mutableMapOf(),
    val langs: MutableSet<Lang> = mutableSetOf(),
    val httpClient: CloseableHttpClient = HttpClients.createDefault()
) : TranslationExecutor {

    /**
     * Release and close the resources of HTTP
     * @param httpEntity http entity
     * @param httpResponse http response
     */
    fun close(httpEntity: HttpEntity?, httpResponse: CloseableHttpResponse?) {
        try {
            EntityUtils.consume(httpEntity)
            httpResponse?.close()
        } catch (e: IOException) {
            LogUtils.info(e.message)
        } finally {
            httpClient.close()
        }
    }

    /**
     * Release and close the resources of HTTP
     */
    fun close() {
        try {
            httpClient.close()
        } catch (e: IOException) {
            LogUtils.info(e.message)
        }
    }

}