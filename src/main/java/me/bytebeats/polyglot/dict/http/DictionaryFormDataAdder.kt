package me.bytebeats.polyglot.dict.http

import me.bytebeats.polyglot.lang.Lang

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 19:03
 * @Version 1.0
 * @Description TO-DO
 */

interface DictionaryFormDataAdder {
    /**
     * Set the request parameters that will be sent to the server.
     * @param to target language
     * @param text the content to be translated
     */
    fun addFormData(to: Lang, text: String)
}