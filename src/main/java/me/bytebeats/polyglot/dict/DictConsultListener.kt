package me.bytebeats.polyglot.dict

import me.bytebeats.polyglot.dict.meta.YouDaoTranslation

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 15:53
 * @Version 1.0
 * @Description TO-DO
 */

interface DictConsultListener {
    fun onSuccess(translation: YouDaoTranslation)
    fun onFailure(message: String)
    fun onError(error: String)
}