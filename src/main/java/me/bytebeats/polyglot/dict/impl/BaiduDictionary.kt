package me.bytebeats.polyglot.dict.impl

import me.bytebeats.polyglot.dict.AbstractDictionary
import me.bytebeats.polyglot.dict.DictConsultListener
import me.bytebeats.polyglot.dict.meta.YouDaoTranslation
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.ui.PolyglotSettingState
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
 * @Created on 2020/11/7 16:25
 * @Version 1.0
 * @Description TO-DO
 */

class BaiduDictionary(listener: DictConsultListener) : AbstractDictionary(listener, URL) {
    companion object {
        private const val URL = "http://api.fanyi.baidu.com/api/trans/vip/translate"

        @JvmStatic
        fun main(args: Array<String>) {
            println("baidu dictionary")
            BaiduDictionary(object : DictConsultListener {
                override fun onSuccess(translation: YouDaoTranslation) {
                    println(translation.format())
                }

                override fun onFailure(message: String) {
                    println(message)
                }

                override fun onError(error: String) {
                    println(error)
                }
            }).consult("remain")
        }
    }

    override fun addSupportedLangs() {
        langs[Lang.ZH] = "zh"
        langs[Lang.CHT] = "cht"
        langs[Lang.YUE] = "yue"
        langs[Lang.WYW] = "wyw"
        langs[Lang.EN] = "en"
        langs[Lang.JP] = "jp"
        langs[Lang.KOR] = "kor"
        langs[Lang.FRA] = "fra"
        langs[Lang.RU] = "ru"
        langs[Lang.DE] = "de"
        langs[Lang.SPA] = "spa"
        langs[Lang.PT] = "pt"
        langs[Lang.TR] = "tr"
        langs[Lang.IT] = "it"
        langs[Lang.ARA] = "ara"
    }

    /**
     * {
    "from": "en",
    "to": "zh",
    "trans_result": [
    {
    "src": "apple",
    "dst": "苹果",
    "src_tts": "https:\/\/fanyiapp.cdn.bcebos.com\/api\/tts\/95e906875b87d342d7325a36a4e1ab42.mp3",
    "dst_tts": "https:\/\/fanyiapp.cdn.bcebos.com\/api\/tts\/62f4ff87617655bc1f65e24cf4ed4963.mp3",
    "dict": "{\"lang\":\"1\",\"word_result\":{\"simple_means\":{\"word_name\":\"apple\",\"from\":\"original\",\"word_means\":[\"苹果\"],\"exchange\":{\"word_pl\":[\"apples\"]},\"tags\":{\"core\":[\"高考\",\"考研\"],\"other\":[\"\"]},\"symbols\":[{\"ph_en\":\"ˈæpl\",\"ph_am\":\"ˈæpl\",\"parts\":[{\"part\":\"n.\",\"means\":[\"苹果\"]}],\"ph_other\":\"\"}]}}}"
    }
    ]
    }
     */

    override fun parse(text: String): YouDaoTranslation? {
        val translation = GsonUtils.getInstance().from(text, YouDaoTranslation::class.java)
        return translation?.apply { src = formData["q"]!! }
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")

        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
        println(result)
        close(entity, response)
        return result
    }

    override fun addFormData(to: Lang, text: String) {
//        val appID = PolyglotSettingState.getInstance().appID
//        val appKey = PolyglotSettingState.getInstance().appKey
        val appID = "20201107000610694"
        val appKey = "kwjKCeUUNitS4tnwuO1K"
        val curTime = System.currentTimeMillis().toString()
        val salt = UUID.randomUUID().toString()
        val sign = ParamUtils.md5("$appID$text$salt$appKey")?.toLowerCase()
        formData["from"] = langs[Lang.EN]!!
        formData["to"] = langs[to]!!
        formData["appid"] = appID
        formData["salt"] = salt
        formData["sign"] = sign!!
        formData["q"] = text
        formData["dict"] = "0"
    }
}