package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.http.GlotHttpParams
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.GlotJsUtils
import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils
import java.util.*
import javax.script.Invocable
import javax.script.ScriptEngineManager

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/30 17:30
 * @version 1.0
 * @description TO-DO
 */

class SogouPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "https://fanyi.sogou.com/reventondc/translateV2"
    }

    override fun addSupportedLanguages() {
        langs[Lang.AUTO] = "auto"
        langs[Lang.ZH] = "zh-CHS"
        langs[Lang.CHT] = "zh-CHT"
        langs[Lang.EN] = "en"
        langs[Lang.JP] = "ja"
        langs[Lang.KOR] = "ko"
        langs[Lang.FRA] = "fr"
        langs[Lang.RU] = "ru"
        langs[Lang.DE] = "de"
        langs[Lang.SPA] = "es"
        langs[Lang.IT] = "it"
        langs[Lang.VIE] = "vi"
        langs[Lang.TH] = "th"
        langs[Lang.ARA] = "ar"
    }

    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(text).path("data").path("translate").path("dit").asText()
        return result
    }

    /**
     * {
    "data": {
    "detect": {
    "zly": "zly",
    "detect": "zh-CHS",
    "errorCode": "0",
    "language": "中文",
    "id": "a0761c8f8d35a43b130976bb5718504c",
    "text": "忧郁的小乌龟"
    },
    "translate": {
    "qc_type": "1",
    "zly": "zly",
    "errorCode": "10",
    "index": "content0",
    "from": "zh-CHS",
    "source": "sogou",
    "text": "忧郁的小乌龟",
    "to": "en",
    "id": "a0761c8f8d35a43b130976bb5718504c",
    "dit": "憂鬱的小烏龜",
    "orig_text": "忧郁的小乌龟",
    "md5": ""
    }
    },
    "status": "0",
    "info": "success",
    "zly": "zly"
    }
     */
    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
//        val request = HttpPost(ParamUtils.concatUrl(url, formData))
        request.setHeader("Host", "fanyi.sogou.com")
        request.setHeader("Origin", "https://fanyi.sogou.com")
        //Referer really mattered. It's very important
        request.setHeader(
            "Referer",
            "https://fanyi.sogou.com/?keyword=${formData["text"]}&transfrom=${formData["from"]}&transto=${formData["to"]}"
        )
        //It's very important
        request.setHeader("User-Agent", GlotHttpParams.USER_AGENT)
        //It's very important
        request.setHeader(
            "Cookie",
            "ssuid=3013214756; sw_uuid=6726719957; SUID=82087D7B6B20A00A000000005F44DC76; ABTEST=0|1598779621|v17; SUV=1598779628713; SNUID=296DBEA4DFDA71CB3BE2153BDF9B2F65; SGINPUT_UPSCREEN=1598779644157; usid=0-sIGlnrPalPfwS2; IPLOC=CN3201"
        )
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
//        println(result)
        close(entity, response)
        return result
    }

    override fun setFormData(from: Lang, to: Lang, text: String) {
        formData["from"] = langs[from]!!
        formData["to"] = langs[to]!!
        formData["text"] = text.trim()
        formData["client"] = "pc"
        formData["fr"] = "browser_pc"
        formData["useDetect"] = "on"
//        formData["useDetectResult"] = if (from == Lang.AUTO) "on" else "off"
        formData["needQc"] = "1"
        formData["uuid"] = token()
        formData["oxford"] = "on"
        formData["word_group"] = "true"
        formData["second_query"] = "true"
        formData["dict"] = "true"
        formData["pid"] = "sogou-dict-vr"
//        formData["isReturnSugg"] = "on"
        formData["s"] = ParamUtils.md5("${langs[from]!!}${langs[to]!!}${text.trim()}8511813095152")!!
    }

    private fun token(): String {
        try {
            val engine = ScriptEngineManager().getEngineByName("js")
            val reader = GlotJsUtils.getReader(GlotJsUtils.JS_SOGOu)
            engine.eval(reader)
            if (engine is Invocable) {
                return engine.invokeFunction("token").toString()
            }
        } catch (e: Exception) {
            LogUtils.info(e.message)
        }
        return ""
    }
}