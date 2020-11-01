package me.bytebeats.polyglot.util

import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.PolyglotTranslator

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/5 18:01
 * @version 1.0
 * @description PolyglotUtils supports languages.
 */

class PolyglotUtils {
    companion object {
        val DEFAULT_FROM_LANGS = listOf(
            Lang.AUTO, Lang.ZH, Lang.CHT, Lang.EN, Lang.FRA, Lang.DE, Lang.JP, Lang.KOR, Lang.RU, Lang.SPA, Lang.IT,
            Lang.VIE, Lang.TH, Lang.ARA, Lang.YUE, Lang.WYW, Lang.LAT, Lang.SAN, Lang.ARG, Lang.GRA, Lang.KLI, Lang.HEB,
            Lang.ENO, Lang.FRM, Lang.PER
        )
        val DEFAULT_TO_LANGS = listOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.FRA, Lang.DE, Lang.JP, Lang.KOR, Lang.RU, Lang.SPA, Lang.IT, Lang.VIE,
            Lang.TH, Lang.ARA, Lang.YUE, Lang.WYW, Lang.LAT, Lang.SAN, Lang.ARG, Lang.GRA, Lang.KLI, Lang.HEB, Lang.ENO,
            Lang.FRM, Lang.PER
        )

        private val LANGS_BAIDU = listOf(
            Lang.ZH, Lang.CHT, Lang.YUE, Lang.WYW, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA,
            Lang.PT, Lang.TR, Lang.IT, Lang.ARA, Lang.LAT, Lang.SAN, Lang.ARG, Lang.GRA, Lang.KLI, Lang.HEB, Lang.ENO,
            Lang.FRM, Lang.PER, Lang.AUTO
        )

        private val LANGS_BING = listOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.ARA,
            Lang.TH, Lang.VIE, Lang.AUTO
        )
        private val LANGS_GOOGLE = listOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.VIE,
            Lang.TH, Lang.ARA, Lang.AUTO
        )
        private val LANGS_OMI = listOf(Lang.ZH, Lang.EN, Lang.AUTO)
        private val LANGS_SOGOU = listOf(
            Lang.AUTO, Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT,
            Lang.VIE, Lang.TH, Lang.ARA
        )
        private val LANGS_TENCENT = listOf(
            Lang.ZH, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.TR, Lang.PT,
            Lang.VIE, Lang.ID, Lang.TH, Lang.MS, Lang.HI, Lang.AUTO
        )
        private val LANGS_YOUDAO = listOf(
            Lang.ZH, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.CHT, Lang.DE, Lang.SPA, Lang.IT, Lang.PT,
            Lang.VIE, Lang.ID, Lang.TH, Lang.ARA, Lang.NL, Lang.AUTO
        )

        @JvmStatic
        fun getSupportedPolyglot(from: Lang, to: Lang, isCnPreferred: Boolean = true): List<PolyglotTranslator> {
            val polyglots = mutableListOf<PolyglotTranslator>()
            if (isCnPreferred) {
                if (LANGS_BAIDU.contains(from) && LANGS_BAIDU.contains(to)) {
                    polyglots.add(PolyglotTranslator.Baidu)
                }
                if (LANGS_GOOGLE.contains(from) && LANGS_GOOGLE.contains(to)) {
                    polyglots.add(PolyglotTranslator.Google)
                }
                if (LANGS_BING.contains(from) && LANGS_BING.contains(to)) {
                    polyglots.add(PolyglotTranslator.Bing)
                }
            } else {
                if (LANGS_GOOGLE.contains(from) && LANGS_GOOGLE.contains(to)) {
                    polyglots.add(PolyglotTranslator.Google)
                }
                if (LANGS_BING.contains(from) && LANGS_BING.contains(to)) {
                    polyglots.add(PolyglotTranslator.Bing)
                }
                if (LANGS_BAIDU.contains(from) && LANGS_BAIDU.contains(to)) {
                    polyglots.add(PolyglotTranslator.Baidu)
                }
            }
            if (LANGS_YOUDAO.contains(from) && LANGS_YOUDAO.contains(to)) {
                polyglots.add(PolyglotTranslator.Youdao)
            }
            if (LANGS_SOGOU.contains(from) && LANGS_SOGOU.contains(to)) {
                polyglots.add(PolyglotTranslator.Sogou)
            }
            if (LANGS_OMI.contains(from) && LANGS_OMI.contains(to)) {
                polyglots.add(PolyglotTranslator.Omi)
            }
            if (LANGS_TENCENT.contains(from) && LANGS_TENCENT.contains(to)) {
                polyglots.add(PolyglotTranslator.Tencent)
            }
            return polyglots
        }

        val dictionaryTargetLangs = listOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT,
            Lang.VIE, Lang.TH, Lang.ARA
        )
    }
} 