package me.bytebeats.polyglot.lang

import me.bytebeats.polyglot.util.StringResUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 14:39
 * @version 1.0
 * @description Lang implies languages.
 */

enum class Lang(val lang: String, val desc: String) {
    AUTO("auto", StringResUtils.LANG_DESC_AUTO),
    ZH("zh", StringResUtils.LANG_DESC_ZH),
    EN("en", StringResUtils.LANG_DESC_EN),
    JP("jp", StringResUtils.LANG_DESC_JP),
    KOR("kor", StringResUtils.LANG_DESC_KR),
    FRA("fra", StringResUtils.LANG_DESC_FR),
    DE("de", StringResUtils.LANG_DESC_DE),
    RU("ru", StringResUtils.LANG_DESC_RU),
    CHT("cht", StringResUtils.LANG_DESC_CHT),
    SPA("spa", StringResUtils.LANG_DESC_SPA),
    PT("pt", StringResUtils.LANG_DESC_PT),
    ARA("ara", StringResUtils.LANG_DESC_ARA),
    TR("tr", StringResUtils.LANG_DESC_TR),
    PL("pl", StringResUtils.LANG_DESC_PL),
    DAN("dan", StringResUtils.LANG_DESC_DAN),
    YUE("yue", StringResUtils.LANG_DESC_YUE),
    TH("th", StringResUtils.LANG_DESC_TH),
    VIE("vie", StringResUtils.LANG_DESC_VIE),
    ID("id", StringResUtils.LANG_DESC_ID),
    MS("ms", StringResUtils.LANG_DESC_MS),
    HI("hi", StringResUtils.LANG_DESC_HI),
    IR("ir", StringResUtils.LANG_DESC_IR),
    WYW("wyw", StringResUtils.LANG_DESC_WYW),
    IT("it", StringResUtils.LANG_DESC_IT),
    KK("kk", StringResUtils.LANG_DESC_KK),
    TL("tl", StringResUtils.LANG_DESC_TL),
    ICE("ice", StringResUtils.LANG_DESC_ICE),
    FIN("fin", StringResUtils.LANG_DESC_FIN),
    NL("nl", StringResUtils.LANG_DESC_NL),
    TAT("tat", StringResUtils.LANG_DESC_TAT),
    KUR("kur", StringResUtils.LANG_DESC_KUR),

    /**
     * dead languages
     */
    LAT("lat", StringResUtils.LANG_DESC_LAT),
    SAN("san", StringResUtils.LANG_DESC_SAN),
    ARG("arg", StringResUtils.LANG_DESC_ARG),
    GRA("gra", StringResUtils.LANG_DESC_GRA),
    KLI("kli", StringResUtils.LANG_DESC_KLI),
    HEB("heb", StringResUtils.LANG_DESC_HEB),
    ENO("eno", StringResUtils.LANG_DESC_ENO),
    FRM("frm", StringResUtils.LANG_DESC_FRM),
    PER("per", StringResUtils.LANG_DESC_PER);

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