package me.bytebeats.polyglot.dict

import me.bytebeats.polyglot.util.StringResUtils

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/11/1 17:04
 * @Version 1.0
 * @Description TO-DO
 */

enum class Dict(val id: String, val desc: String, val descEN: String) {
    YouDao("youdao", StringResUtils.POLYGLOT_YOUDAO, StringResUtils.POLYGLOT_YOUDAO_EN);

    companion object {
        fun from(desc: String): Dict {
            for (dict in values()) {
                if (dict.desc == desc || dict.descEN == desc) {
                    return dict
                }
            }
            return YouDao
        }
    }
}