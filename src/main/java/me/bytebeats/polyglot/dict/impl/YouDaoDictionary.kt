package me.bytebeats.polyglot.dict.impl

import me.bytebeats.polyglot.dict.AbstractDictionary
import me.bytebeats.polyglot.dict.DictConsultListener
import me.bytebeats.polyglot.dict.meta.YouDaoTranslation
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.util.GsonUtils
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils
import java.util.*

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 19:26
 * @Version 1.0
 * @Description TO-DO
 */

class YouDaoDictionary(listener: DictConsultListener) : AbstractDictionary(listener, URL) {
    companion object {
        private const val URL = "https://openapi.youdao.com/api"
    }

    override fun addSupportedLangs() {
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
     * {
    "returnPhrase": [
    "dictionary"
    ],
    "query": "dictionary",
    "errorCode": "0",
    "l": "en2zh-CHS",
    "tSpeakUrl": "http://openapi.youdao.com/ttsapi?q\u003d%E5%AD%97%E5%85%B8\u0026langType\u003dzh-CHS\u0026sign\u003dCB3E1B4B35E08C0F9DCEF04CE16BCBBF\u0026salt\u003d1604150506142\u0026voice\u003d4\u0026format\u003dmp3\u0026appKey\u003d0de194293a5be337\u0026ttsVoiceStrict\u003dfalse",
    "web": [
    {
    "value": [
    "词典",
    "字典",
    "字典",
    "词典正文"
    ],
    "key": "dictionary"
    },
    {
    "value": [
    "城市词典",
    "都市词典",
    "俚语词典"
    ],
    "key": "Urban Dictionary"
    },
    {
    "value": [
    "数据字典",
    "数据词典",
    "资料字典",
    "资料辞典"
    ],
    "key": "Data Dictionary"
    }
    ],
    "requestId": "262d6c75-b034-4367-b0f6-a31e032c70dd",
    "translation": [
    "字典"
    ],
    "dict": {
    "url": "yddict://m.youdao.com/dict?le\u003deng\u0026q\u003ddictionary"
    },
    "webdict": {
    "url": "http://m.youdao.com/dict?le\u003deng\u0026q\u003ddictionary"
    },
    "basic": {
    "exam_type": [
    "高中",
    "初中",
    "CET6",
    "CET4",
    "考研"
    ],
    "us-phonetic": "ˈdɪkʃəneri",
    "phonetic": "ˈdɪkʃənri",
    "uk-phonetic": "ˈdɪkʃənri",
    "wfs": [
    {
    "wf": {
    "name": "复数",
    "value": "dictionaries"
    }
    }
    ],
    "uk-speech": "http://openapi.youdao.com/ttsapi?q\u003ddictionary\u0026langType\u003den\u0026sign\u003d35A6F5FF28005BD4046A83CFB1610D09\u0026salt\u003d1604150506142\u0026voice\u003d5\u0026format\u003dmp3\u0026appKey\u003d0de194293a5be337\u0026ttsVoiceStrict\u003dfalse",
    "explains": [
    "n. 字典；词典"
    ],
    "us-speech": "http://openapi.youdao.com/ttsapi?q\u003ddictionary\u0026langType\u003den\u0026sign\u003d35A6F5FF28005BD4046A83CFB1610D09\u0026salt\u003d1604150506142\u0026voice\u003d6\u0026format\u003dmp3\u0026appKey\u003d0de194293a5be337\u0026ttsVoiceStrict\u003dfalse"
    },
    "isWord": true,
    "speakUrl": "http://openapi.youdao.com/ttsapi?q\u003ddictionary\u0026langType\u003den\u0026sign\u003d35A6F5FF28005BD4046A83CFB1610D09\u0026salt\u003d1604150506142\u0026voice\u003d4\u0026format\u003dmp3\u0026appKey\u003d0de194293a5be337\u0026ttsVoiceStrict\u003dfalse"
    }
     */

    override fun parse(text: String): YouDaoTranslation? {
        val translation = GsonUtils.fromJson<YouDaoTranslation?>(text)
        return translation?.apply { src = formData["q"]!! }
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")

        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
//        println(result)
        close(entity, response)
        return result
    }

    override fun addFormData(to: Lang, text: String) {
//        val appID = PolyglotSettingState.getInstance().appID
//        val appKey = PolyglotSettingState.getInstance().appKey
        val appID = "0de194293a5be337"
        val appKey = "KQrktgEdLOinQuQ5LhWLETxWyu9x4UVw"
        val curTime = (System.currentTimeMillis() / 1000).toString()
        val salt = UUID.randomUUID().toString()
        val qInSign = if (text.length <= 20) text else "${text.take(10)}${text.length}${text.takeLast(10)}"
        val sign = ParamUtils.sha256("$appID$qInSign$salt$curTime$appKey")
        formData["from"] = langs[Lang.EN]!!
        formData["to"] = langs[to]!!
        formData["appKey"] = appID
        formData["salt"] = salt
        formData["sign"] = sign!!
        formData["q"] = text
        formData["signType"] = "v3"
        formData["curtime"] = curTime
    }
}