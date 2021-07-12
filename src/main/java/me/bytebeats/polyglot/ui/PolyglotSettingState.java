package me.bytebeats.polyglot.ui;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import me.bytebeats.polyglot.dict.meta.DictService;
import me.bytebeats.polyglot.lang.Lang;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @author bytebeats
 * @version 1.0
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/7 11:41
 * @description PolyglotSettingState offers settings for Polyglot
 */

@State(name = "me.bytebeats.polyglot.ui.PolyglotSettingState", storages = {@Storage("polyglot_setting.xml")})
public class PolyglotSettingState implements PersistentStateComponent<PolyglotSettingState> {
    private String from = StringResUtils.LANG_DESC_ZH;
    private String to = StringResUtils.LANG_DESC_EN;
    private String dailyQuoter = StringResUtils.QUOTOR_SCALLOP;
    private String preferredLang = StringResUtils.PREFERRED_LANG_EN;
    private boolean isDailyQuoterOn = true;

    private String appID = "";
    private String appKey = "";
    private boolean isSelectWordToConsultOn = true;
    private String dict = StringResUtils.POLYGLOT_YOUDAO;//字典, 有道, 谷歌, 百度或者必应
    private String dictLang = Lang.ZH.getDesc();//字典中要查询的目标语言, 比如英语, 法语, 日语等
    private HashMap<String, DictService> dictServer = new HashMap();

    public static PolyglotSettingState getInstance() {
        return ServiceManager.getService(PolyglotSettingState.class);
    }

    @Nullable
    @Override
    public PolyglotSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PolyglotSettingState polyglotSettingState) {
        XmlSerializerUtil.copyBean(polyglotSettingState, this);
    }

    public void reset() {
        from = StringResUtils.LANG_DESC_ZH;
        to = StringResUtils.LANG_DESC_EN;
        dailyQuoter = StringResUtils.QUOTOR_SCALLOP;
        isDailyQuoterOn = true;
        isSelectWordToConsultOn = true;
        appID = "";
        appKey = "";
        dictLang = Lang.ZH.getDesc();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDailyQuoter() {
        return dailyQuoter;
    }

    public void setDailyQuoter(String dailyQuoter) {
        this.dailyQuoter = dailyQuoter;
    }

    public boolean isDailyQuoterOn() {
        return isDailyQuoterOn;
    }

    public void setDailyQuoterOn(boolean dailyQuoterOn) {
        isDailyQuoterOn = dailyQuoterOn;
    }

    public String getPreferredLang() {
        return preferredLang;
    }

    public void setPreferredLang(String preferredLang) {
        this.preferredLang = preferredLang;
    }

    public boolean isCnPreferred() {
        return StringResUtils.PREFERRED_LANG_CN.equals(getPreferredLang());
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isSelectWordToConsultOn() {
        return isSelectWordToConsultOn;
    }

    public void setSelectWordToConsultOn(boolean selectWordToConsultOn) {
        isSelectWordToConsultOn = selectWordToConsultOn;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getDictLang() {
        return dictLang;
    }

    public void setDictLang(String dictLang) {
        this.dictLang = dictLang;
    }
}
