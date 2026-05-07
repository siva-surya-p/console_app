package com.sivasuryap.miniTwitter.features.follow.following;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class FollowingListModel {

    List<User> getFollowing(long userId) {
        return MiniTwitterDB.getInstance().getFollowing(userId);
    }
}
