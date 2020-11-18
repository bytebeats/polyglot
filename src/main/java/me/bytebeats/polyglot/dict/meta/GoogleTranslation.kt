package me.bytebeats.polyglot.dict.meta

import com.google.gson.annotations.SerializedName
import me.bytebeats.polyglot.lang.Lang

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/11/7 19:25
 * @Version 1.0
 * @Description TO-DO
 */

data class GoogleTranslation(
    val sentences: List<GSentence>? = null,
    val dict: List<GDict>? = null,
    val spell: GSpell? = null,
    @SerializedName("ld_result") val ldResult: GldResult,
    @SerializedName("alternative_translations") val alternativeTranslations: List<GAlternativeTranslations>? = null
) {
}

sealed class GSentence
data class GTransSentence(val orig: String, val trans: String, val backend: Int) : GSentence()
data class GTranslitSentence(
    @SerializedName("src_translit") val srcTranslit: String?,
    @SerializedName("translit") val translit: String?,
) : GSentence()

data class GDict(val pos: String, val terms: List<String>? = null, val entry: List<GDictEntry>? = null)
data class GDictEntry(
    @SerializedName("word") val word: String,
    @SerializedName("reverse_translation") val reverseTranslation: List<String>?,
    @SerializedName("score") val score: Float,
)

data class GSpell(@SerializedName("spell_res") val spell: String)
data class GldResult(
    @SerializedName("srclangs") val srcLangs: List<String>,
    @SerializedName("srclangs_confidences") val srcLangsConfidences: List<Float>
)

data class GAlternativeTranslations(
    @SerializedName("src_phrase") val srcPhrase: String,
    @SerializedName("raw_src_segment") val rawSrcSegment: String,
    @SerializedName("alternative") val alternative: List<GAlternative>,
)

data class GAlternative(
    @SerializedName("word_postproc") val wordPostproc: String,
    @SerializedName("score") val score: Float,
    @SerializedName("has_preceding_space") val hasPrecedingSpace: Boolean,
    @SerializedName("attach_to_next_token") val attachToNextToken: Boolean,
)