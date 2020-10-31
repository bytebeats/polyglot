package me.bytebeats.polyglot.http

import me.bytebeats.polyglot.lang.Lang

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:08
 * @version 1.0
 * @description ITranslator will execute a task to translate text over http.
 */

interface IPolyglot {
    /**
     * The control center includes parameter setting, sending HTTP request, receiving
     * and parsing text data.
     *
     * @param from source language
     * @param to target language
     * @param text the content to be translated
     * @return the string form of the translated result.
     */
    fun translate(from: Lang, to: Lang, text: String): String
}