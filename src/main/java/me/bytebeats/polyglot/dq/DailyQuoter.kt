package me.bytebeats.polyglot.dq

import me.bytebeats.polyglot.util.StringResUtils

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/29 17:17
 * @Version 1.0
 * @Description AbstractDailyQuoter offers quote every day
 */

enum class DailyQuoter(val type: String, val desc: String, val descEn: String) {
    SCALLOP("scallop", StringResUtils.QUOTOR_SCALLOP, StringResUtils.QUOTOR_SCALLOP_EN),
    ICIBA("iciba", StringResUtils.QUOTOR_ICIBA, StringResUtils.QUOTOR_ICIBA_EN),
    YOUDAO("youdao", StringResUtils.QUOTOR_YOUDAO, StringResUtils.QUOTOR_YOUDAO_EN);

    companion object {
        fun fromDesc(desc: String): DailyQuoter {
            for (quoter in values()) {
                if (quoter.desc == desc || quoter.descEn == desc) {
                    return quoter
                }
            }
            return SCALLOP
        }
    }
}