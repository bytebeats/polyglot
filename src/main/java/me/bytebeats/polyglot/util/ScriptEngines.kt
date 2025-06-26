package me.bytebeats.polyglot.util

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import javax.script.ScriptEngine

fun findGraalScriptEngine(): ScriptEngine {
    val engine = GraalJSScriptEngine.create(
        null,
        Context.newBuilder("js")
            .allowHostAccess(HostAccess.ALL)
            .allowHostClassLookup { true }
            .option("js.ecmascript-version", "2022"))
    engine.put("javaObj", Any())
    return engine
}