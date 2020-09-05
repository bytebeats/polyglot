package me.bytebeats.polyglot.lang

import me.bytebeats.polyglot.util.StringResource

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 14:39
 * @version 1.0
 * @description Lang implies languages.
 */

enum class Lang(val lang: String, val desc: String) {
    AUTO("auto", StringResource.LANG_DESC_AUTO),
    ZH("zh", StringResource.LANG_DESC_ZH),
    EN("en", StringResource.LANG_DESC_EN),
    JP("jp", StringResource.LANG_DESC_JP),
    KOR("kor", StringResource.LANG_DESC_KR),
    FRA("fra", StringResource.LANG_DESC_FR),
    DE("de", StringResource.LANG_DESC_DE),
    RU("ru", StringResource.LANG_DESC_RU),
    CHT("cht", StringResource.LANG_DESC_CHT),
    SPA("spa", StringResource.LANG_DESC_SPA),
    PT("pt", StringResource.LANG_DESC_PT),
    ARA("ara", StringResource.LANG_DESC_ARA),
    TR("tr", StringResource.LANG_DESC_TR),
    PL("pl", StringResource.LANG_DESC_PL),
    DAN("dan", StringResource.LANG_DESC_DAN),
    YUE("yue", StringResource.LANG_DESC_YUE),
    TH("th", StringResource.LANG_DESC_TH),
    VIE("vie", StringResource.LANG_DESC_VIE),
    ID("id", StringResource.LANG_DESC_ID),
    MS("ms", StringResource.LANG_DESC_MS),
    HI("hi", StringResource.LANG_DESC_HI),
    IR("ir", StringResource.LANG_DESC_IR),
    WYW("wyw", StringResource.LANG_DESC_WYW),
    IT("it", StringResource.LANG_DESC_IT),
    KK("kk", StringResource.LANG_DESC_KK),
    TL("tl", StringResource.LANG_DESC_TL),
    ICE("ice", StringResource.LANG_DESC_ICE),
    FIN("fin", StringResource.LANG_DESC_FIN),
    NL("nl", StringResource.LANG_DESC_NL),
    TAT("tat", StringResource.LANG_DESC_TAT),
    KUR("kur", StringResource.LANG_DESC_KUR),

    /**
     * dead languages
     */
    LAT("lat", StringResource.LANG_DESC_LAT),
    SAN("san", StringResource.LANG_DESC_SAN),
    ARG("arg", StringResource.LANG_DESC_ARG),
    GRA("gra", StringResource.LANG_DESC_GRA),
    KLI("kli", StringResource.LANG_DESC_KLI),
    HEB("heb", StringResource.LANG_DESC_HEB),
    ENO("eno", StringResource.LANG_DESC_ENO),
    FRM("frm", StringResource.LANG_DESC_FRM),
    PER("per", StringResource.LANG_DESC_PER);

    companion object {
        fun from(desc: String): Lang {
            for (lang in values()) {
                if (lang.desc == desc) {
                    return lang
                }
            }
            return AUTO
        }
    }
}