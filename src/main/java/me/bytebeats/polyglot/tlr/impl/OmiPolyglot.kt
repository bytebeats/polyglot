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
 * @created on 2020/9/3 18:09
 * @version 1.0
 * @description TO-DO
 */

class OmiPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "https://www.omifanyi.com/transsents.do"
    }

    override fun addSupportedLanguages() {
        langs[Lang.ZH] = "c"
        langs[Lang.EN] = "e"
    }

    /**
     *{
    "languageType": "c2e",
    "sentsToTrans": "忧郁的乌龟",
    "wordToTrans": "",
    "srcLength": 5,
    "srcTransLength": 5,
    "unTransStartChar": "",
    "resType": "1",
    "sentsResults": [
    [
    "忧郁的乌龟"
    ],
    [
    "The Melancholy Turtle"
    ],
    [
    "TEXTCONTENT"
    ]
    ],
    "sentExample": [],
    "doYouMean": "",
    "userDbName": "",
    "success": true,
    "errorInfo": "",
    "servletWrapper": {}
    }
     */
    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(text).path("sentsResults")[1][0].asText()
        return result
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "utf-8")
        close(entity, response)
        return result
    }

    override fun addFormData(from: Lang, to: Lang, text: String) {
        formData["languageType"] = "${langs[from]}2${langs[to]}"
        formData["sentsToTrans"] = text.trim()
    }
}