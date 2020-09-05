package me.bytebeats.polyglot.util

import me.bytebeats.polyglot.lang.Lang

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/5 18:01
 * @version 1.0
 * @description TO-DO
 */

class PolyglotUtils {
    companion object {
        private val langsSupportedByBaidu = setOf(
            Lang.ZH, Lang.CHT, Lang.YUE, Lang.WYW, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA,
            Lang.PT, Lang.TR, Lang.IT, Lang.SAN, Lang.ARG, Lang.GRA, Lang.KLI, Lang.HEB, Lang.ENO, Lang.FRM, Lang.PER
        )

        private val langsSupportedByBing = setOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.ARA,
            Lang.TH, Lang.VIE
        )
        private val langsSupportedByGoogle = setOf(
            Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.VIE,
            Lang.TH, Lang.ARA
        )
        private val langsSupportedByOmi = setOf(Lang.ZH, Lang.EN)
        private val langsSupportedBySogou = setOf(
            Lang.AUTO, Lang.ZH, Lang.CHT, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT,
            Lang.VIE, Lang.TH, Lang.ARA
        )
        private val langsSupportedByTencent = setOf(
            Lang.ZH, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.DE, Lang.SPA, Lang.IT, Lang.TR, Lang.PT,
            Lang.VIE, Lang.ID, Lang.TH, Lang.MS, Lang.ARA, Lang.HI
        )
        private val langsSupportedByYoudao = setOf(
            Lang.ZH, Lang.EN, Lang.JP, Lang.KOR, Lang.FRA, Lang.RU, Lang.CHT, Lang.DE, Lang.SPA, Lang.IT, Lang.PT,
            Lang.VIE, Lang.ID, Lang.TH, Lang.ARA, Lang.NL
        )

        fun getSupportedPolyglot(from: Lang, to: Lang): List<String> {
            val polyglots = mutableListOf<String>()
            if (langsSupportedByBaidu.contains(from) && langsSupportedByBaidu.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_BAIDU)
            }
            if (langsSupportedByBing.contains(from) && langsSupportedByBing.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_BING)
            }
            if (langsSupportedByGoogle.contains(from) && langsSupportedByGoogle.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_GOOGLE)
            }
            if (langsSupportedByOmi.contains(from) && langsSupportedByOmi.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_OMI)
            }
            if (langsSupportedBySogou.contains(from) && langsSupportedBySogou.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_SOGOU)
            }
            if (langsSupportedByTencent.contains(from) && langsSupportedByTencent.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_TENCENT)
            }
            if (langsSupportedByYoudao.contains(from) && langsSupportedByYoudao.contains(to)) {
                polyglots.add(StringResource.POLYGLOT_YOUDAO)
            }
            return polyglots
        }

    }
} 