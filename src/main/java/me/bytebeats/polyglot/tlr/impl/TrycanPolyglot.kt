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
 * @created on 2020/8/30 19:09
 * @version 1.0
 * @description TrycanPolyglot depends on trycan to offer translation service
 */

class TrycanPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "http://fanyi.trycan.com/Transfer.do"
    }

    override fun addSupportedLanguages() {
        langs[Lang.ZH] = "zh"
        langs[Lang.CHT] = "cht"
        langs[Lang.EN] = "en"
    }

    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(text).path("data").findValuesAsText("dst")
        if (result.isNullOrEmpty()) {
            return ""
        }
        val dst = StringBuilder()
        for (s in result) {
            if (dst.isNotEmpty()) {
                dst.append("\n")
            }
            dst.append(s)
        }
        return dst.toString()
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
//        val request = HttpPost(ParamUtils.concatUrl(url, formData))
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
        println(result)
        close(entity, response)
        return result
    }

    override fun setFormData(from: Lang, to: Lang, text: String) {
        formData["word_from"] = langs[from]!!
        formData["word_to"] = langs[to]!!
        formData["word_src"] = text
    }
}