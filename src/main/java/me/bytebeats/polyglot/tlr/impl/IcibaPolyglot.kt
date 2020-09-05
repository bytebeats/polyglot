package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.http.FormDataAdder
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/3 18:23
 * @version 1.0
 * @description TO-DO
 */

class IcibaPolyglot() : AbstractPolyglot(URL) {
    companion object {
        //        private const val URL = "http://www.iciba.com/fy"
        private const val URL = "https://ifanyi.iciba.com/index.php"
    }

    private val queries = mutableMapOf<String, String>()

    override fun addSupportedLanguages() {
        langs[Lang.ZH] = "zh"
        langs[Lang.EN] = "en"
        langs[Lang.JP] = "ja"
        langs[Lang.KOR] = "ko"
        langs[Lang.FRA] = "fr"
        langs[Lang.DE] = "de"
        langs[Lang.CHT] = "cht"
        langs[Lang.RU] = "ru"
        langs[Lang.DE] = "de"
        langs[Lang.SPA] = "es"
        langs[Lang.PT] = "pt"
        langs[Lang.TR] = "tr"
        langs[Lang.IT] = "it"
    }

    /**
     * {
    "status": 1,
    "content": {
    "from": "zh",
    "to": "es",
    "vendor": "ciba",
    "out": "Traducción",
    "ciba_use": "来自机器翻译。",
    "ciba_out": "",
    "err_no": 0
    }
    }
     *
     */
    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        return mapper.readTree(text).path("content").path("out").asText()
    }

    override fun query(): String {
        println(ParamUtils.concatUrl(url, queries))
        val request = HttpPost(ParamUtils.concatUrl(url, queries))
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
        request.setHeader("Content-Type", "application/x-www-form-urlencoded")
        request.setHeader("Host", "ifanyi.iciba.com")
        request.setHeader("Origin", "http://www.iciba.com")
        request.addHeader("Referer", "http://www.iciba.com/fy")
        request.addHeader("User-Agent", FormDataAdder.USER_AGENT)
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "utf-8")
        println(response)
        close(entity, response)
        return result
    }

    override fun addFormData(from: Lang, to: Lang, text: String) {
        formData["from"] = langs[from]!!
        formData["to"] = langs[to]!!
        formData["q"] = text.trim()
        //
        queries["c"] = "trans"
        queries["m"] = "fy"
        queries["clint"] = "6"
        queries["auth_user"] = "key_ciba"
        queries["sign"] = ParamUtils.md5(
            "6key_cibaifanyicjbysdlove1${
                formData.map {
                    "${ParamUtils.encodeUtf8(it.key)}=${
                        ParamUtils.encodeUtf8(it.value)
                    }"
                }.joinToString("&")
            }"
        )!!.substring(0, 16)
    }
}