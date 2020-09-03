package me.bytebeats.polyglot

import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.impl.*
import me.bytebeats.polyglot.util.ParamUtils
import java.util.*

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 17:30
 * @version 1.0
 * @description TO-DO
 */

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val baidu = BaiduPolyglot()
            println("Baidu----------------")
            println(baidu.execute(Lang.EN, Lang.ZH, "God, are you testing me?"))
//            println(baidu.execute(Lang.EN, Lang.CHT, "God, are you testing me?"))
//            println(baidu.execute(Lang.EN, Lang.JP, "God, are you testing me?"))
            val tencent = TencentPolyglot()
            println("Tencent----------------")
            println(tencent.execute(Lang.ZH, Lang.FRA, "德意志"))
            println("YouDao----------------")
            val youdao = YouDaoPolyglot()
            println(youdao.execute(Lang.ZH, Lang.EN, "忧郁的小乌龟"))
            println("Google----------------")
            val google = GooglePolyglot()
            println(google.execute(Lang.ZH, Lang.CHT, "台湾"))
            println("Bing----------------")
            val bing = BingPolyglot()
            println(bing.execute(Lang.ZH, Lang.FRA, "忧郁的小乌龟"))
            println("Sogou----------------")
            val sogou = SogouPolyglot()
            println(sogou.execute(Lang.ZH, Lang.CHT, "忧郁小乌龟"))
//            println("trycan----------------")
//            val trycan = TrycanPolyglot()
//            println(trycan.execute(Lang.ZH, Lang.CHT, "忧郁的小乌龟"))
        }
    }
}