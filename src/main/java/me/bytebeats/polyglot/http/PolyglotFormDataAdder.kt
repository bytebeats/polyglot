package me.bytebeats.polyglot.http

import me.bytebeats.polyglot.lang.Lang

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 14:51
 * @version 1.0
 * @description  FormDataAdder is an interface containing one function to set up
 * HTTP request parameters.
 */

interface PolyglotFormDataAdder {
    /**
     * Set the request parameters that will be sent to the server.
     * @param from source language
     * @param to target language
     * @param text the content to be translated
     */
    fun addFormData(from: Lang, to: Lang, text: String)

    companion object {
        const val USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36"
    }
}