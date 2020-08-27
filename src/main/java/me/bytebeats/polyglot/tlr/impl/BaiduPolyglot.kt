package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.GlotJsUtils
import me.bytebeats.polyglot.util.LogUtils
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils
import javax.script.Invocable
import javax.script.ScriptEngineManager
import javax.script.ScriptException

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:56
 * @version 1.0
 * @description TO-DO
 */

class BaiduPolyglot : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "https://fanyi.baidu.com/v2transapi"
    }

    override fun addSupportedLanguages() {
        langs.add(Lang.ZH)
        langs.add(Lang.EN)
        langs.add(Lang.JP)
        langs.add(Lang.KOR)
        langs.add(Lang.FRA)
        langs.add(Lang.RU)
        langs.add(Lang.DE)
        langs.add(Lang.CHT)
    }

    override fun parse(translatedRaw: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(translatedRaw).path("trans_result").findValuesAsText("dst")
        if (result != null) {
            val des = StringBuilder()
            for (s in result) {
                if (des.isNotEmpty()) {
                    des.append("\n")
                }
                des.append(s)
            }
            return des.toString()
        }
        return ""
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")
        request.addHeader(
            "Cookie",
            "BIDUPSID=FAAF6477F3E560AC94B0AADD45FB51E8; PSTM=1596595912; BAIDUID=FAAF6477F3E560ACC2CF11072FF242AC:FG=1; BDUSS=XhybkNqSjlUbnZmTTlxdGRVazZDdkVqMmpUeFlESWJTTmp6eUlpeEtIQU9yRkZmRVFBQUFBJCQAAAAAAAAAAAEAAADi0kwgcGNMaXR0bGVQYW4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA4fKl8OHypfN; BDUSS_BFESS=XhybkNqSjlUbnZmTTlxdGRVazZDdkVqMmpUeFlESWJTTmp6eUlpeEtIQU9yRkZmRVFBQUFBJCQAAAAAAAAAAAEAAADi0kwgcGNMaXR0bGVQYW4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA4fKl8OHypfN; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; MCITY=-131%3A; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; delPer=0; PSINO=2; H_PS_PSSID=32662_1464_32533_32328_31254_32046_32679_32117_32092_32582; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1597741056,1598237654,1598348583,1598517252; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1598517252; __yjsv5_shitong=1.0_7_41feb6601e1a40dca04ed5516fdd1e3b4647_300_1598517252370_123.125.8.130_b60c7695; yjs_js_security_passport=6b0b4132f6ffb40f2bcbc632f9e8a52c94f473b4_1598517253_js"
        )
        request.addHeader(
            "User-Agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36"
        )
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
        close(entity, response)
        return result
    }

    override fun setFormData(from: Lang, to: Lang, text: String) {
        formData["from"] = from.lang
        formData["to"] = to.lang
        formData["query"] = text
        formData["transtype"] = "translang"
        formData["simple_means_flag"] = "3"
        formData["sign"] = token(text, "320305.131321201")
        formData["token"] = "72f97fdf8a444e118884a466c86703d8"
    }

    private fun token(text: String, gtk: String): String {
        try {
            val engine = ScriptEngineManager().getEngineByName("js")
            val reader = GlotJsUtils.getReader(GlotJsUtils.JS_BAIDU)
            engine.eval(reader)
            if (engine is Invocable) {
                return engine.invokeFunction("token", text, gtk).toString()
            }
        } catch (e: ScriptException) {
            LogUtils.info(e.message)
        } catch (e: NoSuchMethodException) {
            LogUtils.info(e.message)
        }
        return ""
    }
}