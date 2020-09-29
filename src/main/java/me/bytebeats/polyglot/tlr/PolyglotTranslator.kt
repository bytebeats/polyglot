package me.bytebeats.polyglot.tlr

import me.bytebeats.polyglot.util.StringResUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/6 18:03
 * @version 1.0
 * @description TO-DO
 */

enum class PolyglotTranslator(val idx: String, val desc: String, val descEN: String) {
    Baidu("baidu", StringResUtils.POLYGLOT_BAIDU, StringResUtils.POLYGLOT_BAIDU_EN),
    Bing("bing", StringResUtils.POLYGLOT_BING, StringResUtils.POLYGLOT_BING_EN),
    Google("google", StringResUtils.POLYGLOT_GOOGLE, StringResUtils.POLYGLOT_GOOGLE_EN),
    Omi("omi", StringResUtils.POLYGLOT_OMI, StringResUtils.POLYGLOT_OMI_EN),
    Sogou("sogou", StringResUtils.POLYGLOT_SOGOU, StringResUtils.POLYGLOT_SOGOU_EN),
    Tencent("tencent", StringResUtils.POLYGLOT_TENCENT, StringResUtils.POLYGLOT_TENCENT_EN),
    Youdao("youdao", StringResUtils.POLYGLOT_YOUDAO, StringResUtils.POLYGLOT_YOUDAO_EN);

    companion object {
        fun from(desc: String): PolyglotTranslator {
            for (t in values()) {
                if (t.desc == desc || t.descEN == desc) return t
            }
            return Baidu
        }
    }
}