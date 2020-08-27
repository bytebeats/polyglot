package me.bytebeats.polyglot.tlr

import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.http.AbstractHttpAttrs
import me.bytebeats.polyglot.http.GlotHttpParams
import me.bytebeats.polyglot.lang.Lang
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

abstract class AbstractPolyglot(url: String) : AbstractHttpAttrs(url), GlotHttpParams {
    init {
        addSupportedLanguages()
    }

    /**
     * Initialize the supported language mapping.
     */
    abstract fun addSupportedLanguages()

    override fun execute(from: Lang, to: Lang, text: String): String {
        var result = ""
        setFormData(from, to, text)
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
     * @param text the string form of the translated result.
     * @return translation results after parsing.
     * @throws IOException if the parsing fails.
     */
    @Throws(IOException::class)
    abstract fun parse(translatedRaw: String): String
}