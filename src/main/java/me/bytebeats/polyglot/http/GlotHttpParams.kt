package me.bytebeats.polyglot.http

import me.bytebeats.polyglot.lang.Lang

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 14:51
 * @version 1.0
 * @description  HttpParams is an interface containing two functions to set up
 * HTTP request parameters.
 */

interface GlotHttpParams {
    /**
     * Set the request parameters that will be sent to the server.
     * @param from source language
     * @param to target language
     * @param text the content to be translated
     */
    fun setFormData(from: Lang, to: Lang, text: String)
}