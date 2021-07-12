package me.bytebeats.polyglot.dict.meta

import com.google.gson.annotations.SerializedName

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/11/7 17:20
 * @Version 1.0
 * @Description TO-DO
 */

data class BaiduTranslation(
    @SerializedName("error_code") val code: Int = 0,
    @SerializedName("from") val from: String? = null,
    @SerializedName("to") val to: String? = null,
    @SerializedName("trans_result") val trans: List<BTrans>? = null,
) {
}

data class BTrans(
    @SerializedName("src") val src: String,
    @SerializedName("dst") val dst: String,
    @SerializedName("dict") val dict: BDict? = null
)

data class BDict(
    @SerializedName("lang") val lang: String,
    @SerializedName("word_result") val wordResult: BWordResult? = null
)

data class BWordResult(@SerializedName("simple_means") val simpleMeans: BSimpleMeans? = null)
data class BSimpleMeans(
    @SerializedName("word_name") val wordName: String,
    @SerializedName("from") val from: String,
    @SerializedName("word_means") val wordMeans: List<String>? = null,
    @SerializedName("exchange") val exchange: BWordPl? = null,
    @SerializedName("symbols") val symbols: List<BSymbol>? = null,
)

data class BWordPl(@SerializedName("word_pl") val wordPl: List<String>? = null)
data class BSymbol(
    @SerializedName("ph_en") val phEn: String? = null,
    @SerializedName("ph_am") val phAm: String? = null,
    @SerializedName("parts") val parts: List<BPart>? = null,
)

data class BPart(@SerializedName("part") val part: String, @SerializedName("means") val means: List<String>? = null)
