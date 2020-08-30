package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
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
 * @created on 2020/8/29 18:14
 * @version 1.0
 * @description YouDaoPolyglot depends on YouDao to offer translation service.
 */

class YouDaoPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule"
    }

    override fun addSupportedLanguages() {
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

    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(text).path("translateResult").findValuesAsText("tgt")
        if (result.isNullOrEmpty()) {
            return ""
        }
        val dst = StringBuilder()
        for (v in result) {
            if (dst.isNotEmpty()) {
                dst.append("\n")
            }
            dst.append(v)
        }
        return dst.toString()
    }

    override fun query(): String {
//        val request = HttpPost(ParamUtils.concatUrl(URL, formData))
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
//        request.addHeader("Cookie", "OUTFOX_SEARCH_USER_ID=1541101350@10.108.160.105;")
        request.addHeader(
            "Cookie",
            "OUTFOX_SEARCH_USER_ID=1541101350@10.108.160.105; JSESSIONID=aaaRkwXJWDDOnaNJOQ5qx; OUTFOX_SEARCH_USER_ID_NCOO=1678950924.2441375; ___rl__test__cookies=1598697428158"
        )
        request.setHeader("Host", "fanyi.youdao.com")
        request.setHeader("Origin", "http://fanyi.youdao.com")
        request.addHeader("Referer", "http://fanyi.youdao.com/")
        request.addHeader(
            "User-Agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36"
        )

        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
        println(result)
        close(entity, response)
        return result
    }

    override fun setFormData(from: Lang, to: Lang, text: String) {
        val salt = (System.currentTimeMillis() + (Math.random() * 10 + 1).toLong()).toString()
        val client = "fanyideskweb"
        formData["i"] = text
        formData["from"] = langs[from]!!
        formData["to"] = langs[to]!!
        formData["smartresult"] = "dict"
        formData["client"] = client
        formData["salt"] = salt
        formData["Its"] = salt.substring(0, salt.length - 1)
        formData["sign"] = ParamUtils.md5("${client}${text}${salt}ebSeFb%=XZ%T[KZ)c(sy!") ?: ""
        formData["doctype"] = "json"
        formData["version"] = "2.1"
        formData["keyfrom"] = "fanyi.web"
        formData["action"] = "FY_BY_CLICKBUTTON"
        formData["typoResult"] = "false"
    }
}