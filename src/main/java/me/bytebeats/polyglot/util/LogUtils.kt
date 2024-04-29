package me.bytebeats.polyglot.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType


/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 15:32
 * @version 1.0
 * @description LogUtils notifies users on EventLog window
 */

object LogUtils {
    private const val NOTIFICATION_GROUP_ID = "Polyglot"
    fun info(message: String?) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(NOTIFICATION_GROUP_ID)
            .createNotification(message ?: "", NotificationType.INFORMATION)
    }
}