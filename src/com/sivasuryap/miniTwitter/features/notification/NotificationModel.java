package com.sivasuryap.miniTwitter.features.notification;

import com.sivasuryap.miniTwitter.data.model.Notification;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class NotificationModel {

    List<Notification> getNotifications(long userId) {
        return MiniTwitterDB.getInstance().getNotificationsForUser(userId);
    }

    int markAllRead(long userId) {
        return MiniTwitterDB.getInstance().markAllNotificationsRead(userId);
    }
}
