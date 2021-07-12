package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.http.PolyglotFormDataAdder
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/29 18:14
 * @version 1.0
 * @description YouDaoPolyglot depends on YouDao to offer translation service.
 */

class YouDaoPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule"
    }

    override fun addSupportedLanguages() {
        langs[Lang.AUTO] = "AUTO"
        langs[Lang.ZH] = "zh-CHS"
        langs[Lang.EN] = "en"
        langs[Lang.JP] = "ja"
        langs[Lang.KOR] = "ko"
        langs[Lang.FRA] = "fr"
        langs[Lang.RU] = "ru"
        langs[Lang.CHT] = "zh-CHT"
        langs[Lang.DE] = "de"
        langs[Lang.SPA] = "es"
        langs[Lang.IT] = "it"
        langs[Lang.PT] = "pt"
        langs[Lang.VIE] = "vi"
        langs[Lang.ID] = "id"
        langs[Lang.TH] = "th"
        langs[Lang.ARA] = "ar"
        langs[Lang.NL] = "nl"
    }

    /**
     *
     * {
    "translateResult": [
    [
    {
    "tgt": "Good!",
    "src": "好!"
    }
    ],
    [
    {
    "tgt": "The #",
    "src": "的#"
    }
    ],
    [
    {
    "tgt": "?",
    "src": "吧?"
    }
    ]
    ],
    "errorCode": 0,
    "type": "zh-CHS2en"
    }
     *
     */
    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val tree = mapper.readTree(text)
        val translations = tree.path("translateResult")
        val dst = StringBuilder()
        if (!translations.isEmpty) {
            for (i in 0 until translations.size()) {
                val node = translations[i]
                if (node.isEmpty) continue
                if (dst.isNotEmpty()) {
                    dst.append("\n")
                }
                dst.append(node[0].path("tgt").asText())
            }
        }
        val entries = tree.path("smartResult").path("entries")
        if (!entries.isEmpty) {
            dst.append("\nMore:\n")
            for (node in entries) {
                if (!node.asText().isNullOrEmpty()) {
                    dst.append(node.asText())
                }
            }
        }
        return dst.toString()
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
        request.addHeader(
            "Cookie",
            "OUTFOX_SEARCH_USER_ID=1541101350@10.108.160.105; JSESSIONID=aaaRkwXJWDDOnaNJOQ5qx; OUTFOX_SEARCH_USER_ID_NCOO=1678950924.2441375; ___rl__test__cookies=${System.currentTimeMillis()}"
        )
        request.setHeader("Host", "fanyi.youdao.com")
        request.setHeader("Origin", "http://fanyi.youdao.com")
        request.addHeader("Referer", "http://fanyi.youdao.com/")
        request.addHeader("User-Agent", PolyglotFormDataAdder.USER_AGENT)

        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
//        println(result)
        LogUtils.info(result)
        close(entity, response)
        return result
    }

    override fun addFormData(from: Lang, to: Lang, text: String) {
        val ts = System.currentTimeMillis().toString()//timestamp
        val salt = "$ts${(10 * Math.random()).toInt()}"
        val client = "fanyideskweb"
        formData["i"] = text
        formData["from"] = langs[from]!!
        formData["to"] = langs[to]!!
        formData["client"] = client
        formData["salt"] = salt//salt
        formData["sign"] = ParamUtils.md5("${client}${text}${salt}]BjuETDhU)zqSxf-=B#7m") ?: ""//sign
        formData["lts"] = ts//ts
        formData["bv"] =
            ParamUtils.md5(PolyglotFormDataAdder.USER_AGENT.substring(PolyglotFormDataAdder.USER_AGENT.indexOf('/') + 1))!!//bv
        formData["doctype"] = "json"
        formData["version"] = "2.1"
        formData["keyfrom"] = "fanyi.web"
        formData["action"] = "FY_BY_CLICKBUTTON"
        formData["smartresult"] = "dict"
        formData["typoResult"] = "false"
    }
}