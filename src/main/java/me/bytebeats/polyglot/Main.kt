package me.bytebeats.polyglot

import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.impl.*

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
            println("Baidu")
            println(baidu.execute(Lang.EN, Lang.ZH, "God, are you testing me?"))
            println(baidu.execute(Lang.EN, Lang.CHT, "God, are you testing me?"))
            println(baidu.execute(Lang.EN, Lang.JP, "God, are you testing me?"))
            val tencent = TencentPolyglot()
            println("Tencent")
            println(tencent.execute(Lang.ZH, Lang.FRA, "德意志"))
            println("YouDao")
//            val youdao = YouDaoPolyglot()
//            println(youdao.execute(Lang.ZH, Lang.EN, "德意志"))
            println("Google")
            val google = GooglePolyglot()
            println(google.execute(Lang.ZH, Lang.CHT, "台湾"))
            println("Bing")
            val bing = BingPolyglot()
            println(bing.execute(Lang.ZH, Lang.FRA, "忧郁的小乌龟"))
        }
    }
}