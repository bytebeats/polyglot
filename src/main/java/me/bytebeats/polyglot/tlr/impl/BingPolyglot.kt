package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.util.EntityUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/30 14:48
 * @version 1.0
 * @description BingPolyglot depends on Bing-cn to offer translation service
 */

class BingPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL =
            "https://cn.bing.com/ttranslatev3"
    }

    override fun addSupportedLanguages() {
        langs[Lang.AUTO] = "auto-detect"
        langs[Lang.ZH] = "zh-Hans"
        langs[Lang.CHT] = "zh-Hant"
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

    /**
     * [{"detectedLanguage":{"language":"zh-Hans","score":1.0},"translations":[{"text":"台湾。","to":"ja","sentLen":{"srcSentLen":[2],"transSentLen":[3]}}]}]
     */
    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val translations = mapper.readTree(text)[0].path("translations")[0]
        val result = StringBuilder()
        for (i in 0 until translations.size()) {
            result.append(translations[i].path("text").asText())
            if (i != translations.size() - 1) {
                result.append(";")
            }
        }
        return result.toString()
    }

    override fun query(): String {
        val uri = URIBuilder(url)
        uri.addParameter("isVertical", "1")
        uri.addParameter("IG", "1D91F775B11C43EC843BB93AC6D98BBC")
        uri.addParameter("IID", "translator.5028.10")

        val request = HttpPost(uri.toString())
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
        close(entity, response)
        return result
    }

    override fun addFormData(from: Lang, to: Lang, text: String) {
        formData["fromLang"] = langs[from]!!
        formData["to"] = langs[to]!!
        formData["text"] = text
    }
}