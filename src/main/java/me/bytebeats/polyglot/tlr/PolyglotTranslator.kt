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

enum class PolyglotTranslator(val idx: String, val des: String) {
    Baidu("baidu", StringResUtils.POLYGLOT_BAIDU),
    Bing("bing", StringResUtils.POLYGLOT_BING),
    Google("google", StringResUtils.POLYGLOT_GOOGLE),
    Omi("omi", StringResUtils.POLYGLOT_OMI),
    Sogou("sogou", StringResUtils.POLYGLOT_SOGOU),
    Tencent("tencent", StringResUtils.POLYGLOT_TENCENT),
    Youdao("youdao", StringResUtils.POLYGLOT_YOUDAO);

    companion object {
        fun from(des: String): PolyglotTranslator {
            for (t in values()) {
                if (t.des == des) return t
            }
            return Baidu
        }
    }
}