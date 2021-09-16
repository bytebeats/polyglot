package me.bytebeats.polyglot.dict

import me.bytebeats.polyglot.dict.http.IDictionary
import me.bytebeats.polyglot.dict.http.DictionaryFormDataAdder
import me.bytebeats.polyglot.dict.impl.YouDaoDictionary
import me.bytebeats.polyglot.dict.meta.YouDaoTranslation
import me.bytebeats.polyglot.http.TranslatorConnectionCloser
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.util.StringResUtils
import java.io.IOException

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 18:54
 * @Version 1.0
 * @Description AbstractDictionary
 */

abstract class AbstractDictionary(var listener: DictConsultListener? = null, url: String) :
        TranslatorConnectionCloser(url), DictionaryFormDataAdder, IDictionary {

    init {
        addSupportedLangs()
    }

    abstract fun addSupportedLangs()

    override fun consult(text: String) {
//        val dictionary = PolyglotSettingState.getInstance().dictLang
        val dictionary = StringResUtils.LANG_DESC_ZH
        addFormData(Lang.from(dictionary), text)
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

    companion object Factory {

        fun newInstance(desc: String, listener: DictConsultListener): AbstractDictionary {
            return when (desc) {
                StringResUtils.POLYGLOT_YOUDAO, StringResUtils.QUOTOR_YOUDAO_EN -> YouDaoDictionary(listener)
                else -> YouDaoDictionary(listener)
            }
        }

    }
}