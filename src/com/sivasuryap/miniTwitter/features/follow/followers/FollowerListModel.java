package com.sivasuryap.miniTwitter.features.follow.followers;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class FollowerListModel {

    List<User> getFollowers(long userId) {
        return MiniTwitterDB.getInstance().getFollowers(userId);
    }
}
