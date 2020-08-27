package me.bytebeats.polyglot.util

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:32
 * @version 1.0
 * @description LogUtils notifies users on EventLog window
 */

class LogUtils {
    companion object {
        private var project: Project? = null
        fun init(project: Project) {
            Companion.project = project
        }

        fun info(message: String?) {
            if (message != null && project != null) {
                NotificationGroup("polyglot", NotificationDisplayType.NONE, true)
                    .createNotification(message, MessageType.INFO).notify(project)
            }
        }
    }
}