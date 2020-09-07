package me.bytebeats.polyglot.util

import me.bytebeats.polyglot.lang.Lang

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
        val LANGS_DEFAULT = setOf(
            Lang.ZH, Lang.CHT, Lang.WYW, Lang.EN, Lang.FRA, Lang.DE, Lang.JP, Lang.KOR, Lang.RU, Lang.SPA, Lang.IT,
            Lang.VIE, Lang.TH, Lang.ARA, Lang.LAT, Lang.SAN, Lang.ARG, Lang.GRA, Lang.KLI, Lang.HEB, Lang.ENO,
            Lang.FRM, Lang.PER
        )

        private val LANGS_BAIDU = setOf(
            Lang.ZH, Lang.CHT, Lang.YUE, Lang.WYW, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA,
            Lang.PT, Lang.TR, Lang.IT, Lang.ARA, Lang.LAT, Lang.SAN, Lang.ARG, Lang.GRA, Lang.KLI, Lang.HEB, Lang.ENO,
            Lang.FRM, Lang.PER
        )

        private val LANGS_BING = setOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.ARA,
            Lang.TH, Lang.VIE
        )
        private val LANGS_GOOGLE = setOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.VIE,
            Lang.TH, Lang.ARA
        )
        private val LANGS_OMI = setOf(Lang.ZH, Lang.EN)
        private val langsSupportedBySogou = setOf(
            Lang.AUTO, Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT,
            Lang.VIE, Lang.TH, Lang.ARA
        )
        private val LANGS_TENCENT = setOf(
            Lang.ZH, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.TR, Lang.PT,
            Lang.VIE, Lang.ID, Lang.TH, Lang.MS, Lang.ARA, Lang.HI
        )
        private val LANGS_YOUDAO = setOf(
            Lang.ZH, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.CHT, Lang.DE, Lang.SPA, Lang.IT, Lang.PT,
            Lang.VIE, Lang.ID, Lang.TH, Lang.ARA, Lang.NL
        )

        fun getSupportedPolyglot(from: Lang, to: Lang): List<String> {
            val polyglots = mutableListOf<String>()
            if (LANGS_BAIDU.contains(from) && LANGS_BAIDU.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_BAIDU)
            }
            if (LANGS_BING.contains(from) && LANGS_BING.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_BING)
            }
            if (LANGS_GOOGLE.contains(from) && LANGS_GOOGLE.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_GOOGLE)
            }
            if (LANGS_OMI.contains(from) && LANGS_OMI.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_OMI)
            }
            if (langsSupportedBySogou.contains(from) && langsSupportedBySogou.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_SOGOU)
            }
            if (LANGS_TENCENT.contains(from) && LANGS_TENCENT.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_TENCENT)
            }
            if (LANGS_YOUDAO.contains(from) && LANGS_YOUDAO.contains(to)) {
                polyglots.add(StringResUtils.POLYGLOT_YOUDAO)
            }
            return polyglots
        }

    }
} 