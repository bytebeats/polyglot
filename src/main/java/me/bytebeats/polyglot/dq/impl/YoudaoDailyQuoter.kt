package me.bytebeats.polyglot.dq.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.dq.AbstractDailyQuoter
import me.bytebeats.polyglot.meta.DailyQuote
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/18 20:28
 * @Version 1.0
 * @Description YoudaoDailyQuoter offer daily quote by you dao APIs.
 */

class YoudaoDailyQuoter() : AbstractDailyQuoter(URL) {
    companion object {
        private const val URL = "https://dict.youdao.com/infoline"
    }

    override fun query(): String {
        val request = HttpGet(ParamUtils.concatUrl(url, formData))
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
//        println(result)
        close(entity, response)
        return result
    }

    override fun parse(text: String?): DailyQuote? {
        if (text.isNullOrEmpty()) return null
        val mapper = ObjectMapper()
        val json = mapper.readTree(text).path(getToday())[0]
        val date = getToday()
        val content = json["title"].asText()
        val translation = json["summary"].asText()
        return DailyQuote(date, content, translation)
    }

    override fun add() {
        formData["mode"] = "publish"
        formData["update"] = "auto"
        formData["apiversion"] = "5.0"
        formData["date"] = getToday()
    }
}