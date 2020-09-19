package me.bytebeats.polyglot.tlr

import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.http.TranslatorConnectionCloser
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

abstract class AbstractPolyglot(url: String) : TranslatorConnectionCloser(url), FormDataAdder, ITranslator {
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

//        @JvmStatic
//        fun main(args: Array<String>) {
//            println("Baidu----------------")
//            val baidu = BaiduPolyglot()
//            println(baidu.translate(Lang.EN, Lang.ZH, "God, are you testing me?"))
//            println("Tencent----------------")
//            val tencent = TencentPolyglot()
//            println(tencent.translate(Lang.ZH, Lang.FRA, "德意志"))
//            println("YouDao----------------")
//            val youdao = YouDaoPolyglot()
//            println(youdao.translate(Lang.ZH, Lang.EN, "忧郁的小乌龟"))
//            println("Google----------------")
//            val google = GooglePolyglot()
//            println(google.translate(Lang.ZH, Lang.CHT, "台湾"))
//            println("Bing----------------")
//            val bing = BingPolyglot()
//            println(bing.translate(Lang.ZH, Lang.FRA, "忧郁的小乌龟"))
//            println("Sogou----------------")
//            val sogou = SogouPolyglot()
//            println(sogou.translate(Lang.ZH, Lang.CHT, "忧郁小乌龟"))
////            println("trycan----------------")
////            val trycan = TrycanPolyglot()
////            println(trycan.execute(Lang.ZH, Lang.CHT, "忧郁的小乌龟"))
//            println("Omi----------------")
//            val omi = OmiPolyglot()
//            println(omi.translate(Lang.EN, Lang.ZH, "Blue turtle"))
////            println("iciba----------------")
////            val iciba = IcibaPolyglot()
////            println(iciba.execute(Lang.ZH, Lang.EN, "好的"))
//        }
    }
}