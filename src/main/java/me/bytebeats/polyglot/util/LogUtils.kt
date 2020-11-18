package me.bytebeats.polyglot.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
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
    fun info(message: String?) {
        message?.apply {
            if (isNotEmpty()) {
                Notifications.Bus.notify(
                    Notification("polyglot", "Polyglot", message, NotificationType.INFORMATION),
                    ProjectManagerEx.getInstanceEx().defaultProject
                )
            }
        }
    }
}