package me.bytebeats.polyglot.ui

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import me.bytebeats.polyglot.util.StringResUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/6 10:58
 * @version 1.0
 * @description AppSettingState offers settings for Polyglot.
 */
@State(
    name = "me.bytebeats.polyglot.ui.AppSettingState",
    storages = [Storage("polyglot_setting.xml")]
)
class AppSettingState : PersistentStateComponent<AppSettingState> {
    var from: String = ""
    var to: String = ""

    init {
        from = StringResUtils.LANG_DESC_ZH
        to = StringResUtils.LANG_DESC_EN
    }

    override fun getState(): AppSettingState? = this

    override fun loadState(state: AppSettingState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): AppSettingState {
            return ServiceManager.getService(AppSettingState::class.java)
        }
    }
}