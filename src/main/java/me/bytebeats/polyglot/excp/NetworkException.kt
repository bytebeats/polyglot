package me.bytebeats.polyglot.excp

import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/11/7 20:13
 * @Version 1.0
 * @Description TO-DO
 */

class NetworkException(host: String, cause: IOException) : IOException("${cause.message}. host=$host", cause) {
    companion object {
        fun wrapIfIsNetworkException(throwable: Throwable, host: String): Throwable {
            return when (throwable) {
                is SocketException,
                is SocketTimeoutException,
                is SSLHandshakeException,
                is ConnectException,
                is UnknownHostException -> NetworkException(host, throwable as IOException)
                else -> throwable
            }
        }
    }
}