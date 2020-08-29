package me.bytebeats.polyglot.lang

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 14:39
 * @version 1.0
 * @description Lang implies languages.
 */

enum class Lang(val lang: String, val desc: String) {
    AUTO("auto", "自动检测语种"),
    ZH("zh", "中文"),
    EN("en", "英语"),
    JP("jp", "日语"),
    FRA("fra", "法语"),
    SPA("spa", "西班牙语"),
    PT("pt", "葡萄牙语"),
    KOR("kor", "韩语"),
    TR("tr", "土耳其语"),
    DE("de", "德语"),
    RU("ru", "俄语"),
    ARA("ara", "阿拉伯语"),
    PL("pl", "波兰语"),
    DAN("dan", "丹麦语"),
    YUE("yue", "粤语"),
    TH("th", "泰语"),
    VIE("vie", "越南语"),
    ID("id", "印尼语"),
    MS("ms", "马来语"),
    HI("hi", "印地语"),
    IR("ir", "伊朗语"),
    WYW("wyw", "文言文"),
    CHT("cht", "中文繁体"),
    IT("it", "意大利语"),
    KK("kk", "哈萨克语"),
    TL("tl", "菲律宾语"),
    ICE("ice", "冰岛语"),
    FIN("fin", "芬兰语"),
    NL("nl", "荷兰语"),
    TAT("tat", "鞑靼语"),
    KUR("kur", "库尔德语"),
    JPKA("jpka", "日语假名"),
    LAT("lat", "拉丁语"),
    SAN("san", "梵语"),
    ARG("arg", "阿拉贡语"),
    GRA("gra", "古希腊语"),
    KLI("kli", "克林贡语"),
    HEB("heb", "希伯来语"),
    ENO("eno", "古英语"),
    FRM("frm", "中古法语"),
    PER("per", "波斯语");
}