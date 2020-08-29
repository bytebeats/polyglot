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
 * @description BaiduPolyglot depends on baidu to offer translation service.
 */

class BaiduPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "https://fanyi.baidu.com/v2transapi"
    }

    override fun addSupportedLanguages() {
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
        //dead languages or virtual languages
        langs[Lang.SAN] = "san"
        langs[Lang.ARG] = "arg"
        langs[Lang.GRA] = "gra"
        langs[Lang.KLI] = "kli"
        langs[Lang.HEB] = "heb"
        langs[Lang.ENO] = "eno"
        langs[Lang.FRM] = "frm"
        langs[Lang.PER] = "per"

    }

    /**
     * {"trans_result":{"data":[{"dst":"\u4e0a\u5e1d\uff0c\u4f60\u5728\u8003\u9a8c\u6211\u5417\uff1f","prefixWrap":0,"result":[[0,"\u4e0a\u5e1d\uff0c\u4f60\u5728\u8003\u9a8c\u6211\u5417\uff1f",["0|24"],[],["0|24"],["0|30"]]],"src":"God, are you testing me?"}],"from":"en","status":0,"to":"zh","type":2,"phonetic":[{"src_str":"\u4e0a","trg_str":"sh\u00e0ng"},{"src_str":"\u5e1d","trg_str":"d\u00ec"},{"src_str":"\uff0c","trg_str":" "},{"src_str":"\u4f60","trg_str":"n\u01d0"},{"src_str":"\u5728","trg_str":"z\u00e0i"},{"src_str":"\u8003","trg_str":"k\u01ceo"},{"src_str":"\u9a8c","trg_str":"y\u00e0n"},{"src_str":"\u6211","trg_str":"w\u01d2"},{"src_str":"\u5417","trg_str":"ma"},{"src_str":"\uff1f","trg_str":" "}],"keywords":[{"means":["\u795e","\u4e0a\u5e1d","\u5929\u4e3b"],"word":"God"},{"means":["\u8bd5\u9a8c","\u6d4b\u8bd5","\u68c0\u67e5","\u68d8\u624b\u7684","\u4f24\u8111\u7b4b\u7684","\u96be\u5e94\u4ed8\u7684","\u6d4b\u9a8c","\u8003\u67e5","\u5316\u9a8c","\u68c0\u9a8c","test\u7684\u73b0\u5728\u5206\u8bcd"],"word":"testing"}]},
     * "liju_result":{"double":"","single":""},
     * "logid":2220363170}
     */
    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(text).path("trans_result").findValuesAsText("dst")
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
//        println(result)
        close(entity, response)
        return result
    }

    override fun setFormData(from: Lang, to: Lang, text: String) {
        formData["from"] = langs[from]!!
        formData["to"] = langs[to]!!
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