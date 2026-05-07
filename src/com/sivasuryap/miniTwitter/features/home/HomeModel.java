package com.sivasuryap.miniTwitter.features.home;

import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

class HomeModel {

    int getUnreadCount(long userId) {
        return MiniTwitterDB.getInstance().getUnreadNotificationCount(userId);
    }
}
