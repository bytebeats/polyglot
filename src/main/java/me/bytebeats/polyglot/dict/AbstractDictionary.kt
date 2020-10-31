package me.bytebeats.polyglot.dict

import me.bytebeats.polyglot.dict.http.IDictionary
import me.bytebeats.polyglot.dict.http.DictionaryFormDataAdder
import me.bytebeats.polyglot.dict.impl.YouDaoDictionary
import me.bytebeats.polyglot.dict.meta.YouDaoTranslation
import me.bytebeats.polyglot.http.TranslatorConnectionCloser
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.ui.PolyglotSettingState
import java.io.IOException

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 18:54
 * @Version 1.0
 * @Description TO-DO
 */

abstract class AbstractDictionary(val listener: DictConsultListener? = null, url: String) :
        TranslatorConnectionCloser(url), DictionaryFormDataAdder, IDictionary {

    init {
        addSupportedLangs()
    }

    abstract fun addSupportedLangs()

    override fun consult(text: String) {
//        val dictionary = PolyglotSettingState.getInstance().dictionary
//        addFormData(Lang.from(dictionary), text)
        addFormData(Lang.ZH, text)
        try {
            val translation = parse(query())
            if (translation?.isSuccessful() == true) {
                listener?.onSuccess(translation)
            } else {
                listener?.onFailure(translation?.getError() ?: "Unknown failure")
            }
        } catch (e: Exception) {
            listener?.onError(e.message ?: e.cause?.message ?: "Unknown error")
        } finally {
            close()
        }
    }

    /**
     * Parse the string to extract the content of interest.
     *
     * @param text the string form of the to-be-translated result.
     * @return translation results after parsing.
     * @throws IOException if the parsing fails.
     */
    @Throws(IOException::class)
    abstract fun parse(text: String): YouDaoTranslation?

    /**
     * Execute the translation or TTS task (send a POST or GET request to the server),
     * receive the result of translation or speech conversion., and return the content
     * or save file name as string.
     *
     * @return the string form of the translated result.
     * @throws Exception if the request fails
     */
    @Throws(Exception::class)
    abstract fun query(): String

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("youdao dictionary")
            YouDaoDictionary(object : DictConsultListener {
                override fun onSuccess(translation: YouDaoTranslation) {
                    println(translation.format())
                }

                override fun onFailure(message: String) {
                    println(message)
                }

                override fun onError(error: String) {
                    println(error)
                }
            }).consult("remain")
        }
    }
}