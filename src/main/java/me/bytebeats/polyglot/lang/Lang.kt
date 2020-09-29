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

enum class Lang(val lang: String, val desc: String, val descEN: String = "") {
    AUTO("auto", StringResUtils.LANG_DESC_AUTO, StringResUtils.LANG_DESC_AUTO_EN),
    ZH("zh", StringResUtils.LANG_DESC_ZH, StringResUtils.LANG_DESC_ZH_EN),
    EN("en", StringResUtils.LANG_DESC_EN, StringResUtils.LANG_DESC_EN_EN),
    JP("jp", StringResUtils.LANG_DESC_JP, StringResUtils.LANG_DESC_JP_EN),
    KOR("kor", StringResUtils.LANG_DESC_KR, StringResUtils.LANG_DESC_KR_EN),
    FRA("fra", StringResUtils.LANG_DESC_FR, StringResUtils.LANG_DESC_FR_EN),
    DE("de", StringResUtils.LANG_DESC_DE, StringResUtils.LANG_DESC_DE_EN),
    RU("ru", StringResUtils.LANG_DESC_RU, StringResUtils.LANG_DESC_RU_EN),
    CHT("cht", StringResUtils.LANG_DESC_CHT, StringResUtils.LANG_DESC_CHT_EN),
    SPA("spa", StringResUtils.LANG_DESC_SPA, StringResUtils.LANG_DESC_SPA_EN),
    PT("pt", StringResUtils.LANG_DESC_PT, StringResUtils.LANG_DESC_PT_EN),
    ARA("ara", StringResUtils.LANG_DESC_ARA, StringResUtils.LANG_DESC_ARA_EN),
    TR("tr", StringResUtils.LANG_DESC_TR, StringResUtils.LANG_DESC_TR_EN),
    PL("pl", StringResUtils.LANG_DESC_PL, StringResUtils.LANG_DESC_PL_EN),
    DAN("dan", StringResUtils.LANG_DESC_DAN, StringResUtils.LANG_DESC_DAN_EN),
    YUE("yue", StringResUtils.LANG_DESC_YUE, StringResUtils.LANG_DESC_YUE_EN),
    TH("th", StringResUtils.LANG_DESC_TH, StringResUtils.LANG_DESC_TH_EN),
    VIE("vie", StringResUtils.LANG_DESC_VIE, StringResUtils.LANG_DESC_VIE_EN),
    ID("id", StringResUtils.LANG_DESC_ID, StringResUtils.LANG_DESC_ID_EN),
    MS("ms", StringResUtils.LANG_DESC_MS, StringResUtils.LANG_DESC_MS_EN),
    HI("hi", StringResUtils.LANG_DESC_HI, StringResUtils.LANG_DESC_HI_EN),
    IR("ir", StringResUtils.LANG_DESC_IR, StringResUtils.LANG_DESC_IR_EN),
    WYW("wyw", StringResUtils.LANG_DESC_WYW, StringResUtils.LANG_DESC_WYW_EN),
    IT("it", StringResUtils.LANG_DESC_IT, StringResUtils.LANG_DESC_IT_EN),
    KK("kk", StringResUtils.LANG_DESC_KK, StringResUtils.LANG_DESC_KK_EN),
    FIL("fil", StringResUtils.LANG_DESC_FIL, StringResUtils.LANG_DESC_FIL_EN),
    ICE("ice", StringResUtils.LANG_DESC_ICE, StringResUtils.LANG_DESC_ICE_EN),
    FIN("fin", StringResUtils.LANG_DESC_FIN, StringResUtils.LANG_DESC_FIN_EN),
    NL("nl", StringResUtils.LANG_DESC_NL, StringResUtils.LANG_DESC_NL_EN),
    TAT("tat", StringResUtils.LANG_DESC_TAT, StringResUtils.LANG_DESC_TAT_EN),
    KUR("kur", StringResUtils.LANG_DESC_KUR, StringResUtils.LANG_DESC_KUR_EN),

    /**
     * dead languages
     */
    LAT("lat", StringResUtils.LANG_DESC_LAT, StringResUtils.LANG_DESC_LAT_EN),
    SAN("san", StringResUtils.LANG_DESC_SAN, StringResUtils.LANG_DESC_SAN_EN),
    ARG("arg", StringResUtils.LANG_DESC_ARG, StringResUtils.LANG_DESC_ARG_EN),
    GRA("gra", StringResUtils.LANG_DESC_GRA, StringResUtils.LANG_DESC_GRA_EN),
    KLI("kli", StringResUtils.LANG_DESC_KLI, StringResUtils.LANG_DESC_KLI_EN),
    HEB("heb", StringResUtils.LANG_DESC_HEB, StringResUtils.LANG_DESC_HEB_EN),
    ENO("eno", StringResUtils.LANG_DESC_ENO, StringResUtils.LANG_DESC_ENO_EN),
    FRM("frm", StringResUtils.LANG_DESC_FRM, StringResUtils.LANG_DESC_FRM_EN),
    PER("per", StringResUtils.LANG_DESC_PER, StringResUtils.LANG_DESC_PER_EN);

    companion object {
        fun from(desc: String): Lang {
            for (lang in values()) {
                if (lang.desc == desc || lang.descEN == desc) {
                    return lang
                }
            }
            return ZH
        }
    }
}