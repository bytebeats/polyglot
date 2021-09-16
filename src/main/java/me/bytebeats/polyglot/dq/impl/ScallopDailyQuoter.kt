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
 * @Description YoudaoDailyQuoter offer daily quote by scallop APIs.
 */

class ScallopDailyQuoter() : AbstractDailyQuoter(URL) {
    companion object {
        private const val URL = "https://apiv3.shanbay.com/weapps/dailyquote/quote/"
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
        val json = mapper.readTree(text)
        val date = json["assign_date"].asText()
        val content = json["content"].asText()
        val translation = json["translation"].asText()
        return DailyQuote(date, content, translation)
    }

    override fun add() {
        formData["date"] = getToday()
    }
}