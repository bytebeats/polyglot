package me.bytebeats.polyglot.util

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.ex.ProjectManagerEx

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:32
 * @version 1.0
 * @description LogUtils notifies users on EventLog window
 */

object LogUtils {
    private val NOTIFICATION_GROUP = NotificationGroup.logOnlyGroup("Polyglot")
    fun info(message: String?) {
        message?.apply {
            if (isNotEmpty()) {
                NOTIFICATION_GROUP.createNotification("Polyglot", this, NotificationType.INFORMATION, null)
                    .notify(ProjectManagerEx.getInstanceEx().defaultProject)
            }
        }
    }
}