package com.sivasuryap.miniTwitter.features.search;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class SearchModel {

    List<User> searchUsers(String query) {
        return MiniTwitterDB.getInstance().searchUsersByUsername(query);
    }

    boolean isFollowing(long followerId, long followingId) {
        return MiniTwitterDB.getInstance().isFollowing(followerId, followingId);
    }

    int getFollowerCount(long userId) {
        return MiniTwitterDB.getInstance().getFollowerCount(userId);
    }

    int getPostCount(long userId) {
        return MiniTwitterDB.getInstance().getPostsByUser(userId).size();
    }
}
