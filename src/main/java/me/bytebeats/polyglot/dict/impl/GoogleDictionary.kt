package me.bytebeats.polyglot.dict.impl

import me.bytebeats.polyglot.dict.AbstractDictionary
import me.bytebeats.polyglot.dict.DictConsultListener
import me.bytebeats.polyglot.dict.meta.YouDaoTranslation
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.util.GsonUtils
import me.bytebeats.polyglot.util.ParamUtils
import me.bytebeats.polyglot.util.tk
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/11/7 16:25
 * @Version 1.0
 * @Description TO-DO
 */

class GoogleDictionary(listener: DictConsultListener) : AbstractDictionary(listener, URL) {
    companion object {
        const val GOOGLE_HOST = "translate.google.com"
        const val GOOGLE_HOST_CN = "translate.google.cn"
        private const val URL = "https://%s/translate_a/single"

        @JvmStatic
        fun main(args: Array<String>) {
            println("google dictionary")
            GoogleDictionary(object : DictConsultListener {
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
        langs[Lang.ZH] = "zh-CN"
        langs[Lang.CHT] = "zh-TW"
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
        formData["sl"] = langs[Lang.EN]!!
        formData["tl"] = langs[to]!!
        formData["client"] = "gtx"
        formData["dt"] = "1"
        formData["t"] = "1"
        formData["bd"] = "1"
        formData["rm"] = "1"
        formData["qca"] = "1"
        formData["dj"] = "1"
        formData["ie"] = "UTF-8"
        formData["oe"] = "UTF-8"
        formData["hl"] = "1"
        formData["tk"] = text.tk()
    }
}