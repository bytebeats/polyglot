package me.bytebeats.polyglot.http

import me.bytebeats.polyglot.util.LogUtils
import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.IOException

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/18 20:09
 * @Version 1.0
 * @Description DailyQuoteConnectionCloser is an abstract base class associated with HTTP
 */

abstract class DailyQuoteConnectionCloser(
    open val url: String,
    val formData: MutableMap<String, String> = mutableMapOf(),
    val httpClient: CloseableHttpClient = HttpClients.createDefault()
) {

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