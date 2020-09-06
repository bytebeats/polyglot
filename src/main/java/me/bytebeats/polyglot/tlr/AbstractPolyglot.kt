package me.bytebeats.polyglot.tlr

import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.http.HttpEntityCloser
import me.bytebeats.polyglot.http.FormDataAdder
import me.bytebeats.polyglot.http.ITranslator
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.impl.*
import me.bytebeats.polyglot.util.StringResUtils
import java.io.IOException

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:45
 * @version 1.0
 * @description  AbstractPolyglot is an abstract base class for all polyglots
 * which includes several (abstract) functions. By setting parameters,
 * the request is sent to the target server, and then parse the return
 * result to achieve the the purpose of translation.
 */

abstract class AbstractPolyglot(url: String) : HttpEntityCloser(url), FormDataAdder, ITranslator {
    init {
        addSupportedLanguages()
    }

    /**
     * Initialize the supported language mapping.
     */
    abstract fun addSupportedLanguages()

    override fun translate(from: Lang, to: Lang, text: String): String {
        var result = ""
        addFormData(from, to, text)
        try {
            result = parse(query())
        } catch (e: Exception) {
            LogUtils.info(e.message)
        } finally {
            close()
        }
        return result
    }

    /**
     * Parse the string to extract the content of interest.
     *
     * @param text the string form of the to-be-translated result.
     * @return translation results after parsing.
     * @throws IOException if the parsing fails.
     */
    @Throws(IOException::class)
    abstract fun parse(text: String): String

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
        fun newInstance(polyglot: String): AbstractPolyglot =
            when (polyglot) {
                StringResUtils.POLYGLOT_BING -> BingPolyglot()
                StringResUtils.POLYGLOT_GOOGLE -> GooglePolyglot()
                StringResUtils.POLYGLOT_OMI -> OmiPolyglot()
                StringResUtils.POLYGLOT_SOGOU -> SogouPolyglot()
                StringResUtils.POLYGLOT_TENCENT -> TencentPolyglot()
                StringResUtils.POLYGLOT_YOUDAO -> YouDaoPolyglot()
                else -> BaiduPolyglot()
            }
    }
}