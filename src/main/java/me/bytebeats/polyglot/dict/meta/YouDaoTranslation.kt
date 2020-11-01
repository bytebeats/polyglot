package me.bytebeats.polyglot.dict.meta

import com.google.gson.annotations.SerializedName

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 17:32
 * @Version 1.0
 * @Description YouDaoTranslation is the data structure for response of YouDao service
 */

data class YouDaoTranslation(
    @SerializedName("query") val query: String? = null,
    @SerializedName("errorCode") val errorCode: Int = -1,
    @SerializedName("translation") val translation: List<String>? = null,
    @SerializedName("basic") val basicExplain: BasicExplain? = null,
    @SerializedName("l") val languages: String? = null,
    @SerializedName("web") val webExplains: List<WebExplain>? = null,
) {
    var src: String? = null
    fun isSuccessful() = errorCode == 0
    fun getError(): String = when (errorCode) {
        101 -> "缺少必填参数"
        102 -> "不支持的语言类型"
        103 -> "翻译文本过长"
        104 -> "不支持的API类型"
        105 -> "不支持的签名类型"
        106 -> "不支持的响应类型"
        107 -> "不支持的加密传输类型"
        108 -> "appKey无效, \n请在 Preferences -> Other Settings -> Polyglot Translators 输入有道智云appID和appKey"
        109 -> "batchLog格式不正确"
        110 -> "无相关服务的有效实例"
        111 -> "开发者账号无效"
        201 -> "解密失败"
        202 -> "签名检验失败"
        203 -> "访问IP地址不在可访问IP列表"
        301 -> "辞典查询失败"
        302 -> "翻译查询失败"
        303 -> "服务端的其他异常"
        401 -> "账户已欠费"
        else -> "未知返回码: $errorCode"
    }

    fun format(): String {
        val formatted = StringBuilder()
        formatted.append(src)
        formatted.append(" : ")
        if (translation?.isNotEmpty() == true) {
            for (index in translation.indices) {
                formatted.append(translation[index])
                if (index != translation.lastIndex) {
                    formatted.append(",")
                } else {
                    formatted.append(";")
                }
            }
        }
        basicExplain?.apply {
            if (phonetic?.isNotEmpty() == true) {
                formatted.append("\nphonetic: [$phonetic];")
            }
            if (phoneticUS?.isNotEmpty() == true) {
                formatted.append(" US: [$phoneticUS];")
            }
            if (phoneticUK?.isNotEmpty() == true) {
                formatted.append(" UK: [$phoneticUK]")
            }
            if (explains?.isNotEmpty() == true) {
                for (s in explains) {
                    formatted.append("\n$s")
                }
            }
            if (wordForms?.isNotEmpty() == true) {
                formatted.append("\n")
                val forms = wordForms.map { it.wordForm }
                for (index in forms.indices) {
                    formatted.append("${forms[index].name}: ${forms[index].value}")
                    if (index != wordForms.lastIndex) {
                        formatted.append(", ")
                    }
                }
            }
            if (webExplains?.isNotEmpty() == true) {
                formatted.append("\nInterpretation on Network:")
                for (e in webExplains) {
                    formatted.append("\n${e.key} : ${e.values?.joinToString(separator = ", ")}")
                }
            }
        }
        return formatted.toString()
    }
}

data class BasicExplain(
    @SerializedName("phonetic") val phonetic: String? = null,
    @SerializedName("uk-phonetic") val phoneticUK: String? = null,
    @SerializedName("us-phonetic") val phoneticUS: String? = null,
    @SerializedName("explains") val explains: List<String>? = null,
    @SerializedName("wfs") val wordForms: List<WordFormWrapper>? = null,
)

data class WebExplain(
    @SerializedName("key") val key: String,
    @SerializedName("value") val values: List<String>? = null,
)

data class WordFormWrapper(@SerializedName("wf") val wordForm: WordForm)
data class WordForm(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: String,
)