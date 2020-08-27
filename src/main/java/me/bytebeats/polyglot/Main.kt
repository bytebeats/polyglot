package me.bytebeats.polyglot

import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.impl.BaiduPolyglot

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
            val polyglot = BaiduPolyglot()
//            println(polyglot.execute(Lang.EN, Lang.ZH, "God, are you testing me?"))
//            println(polyglot.execute(Lang.EN, Lang.CHT, "God, are you testing me?"))
            println(polyglot.execute(Lang.EN, Lang.JP, "God, are you testing me?"))
        }
    }
}