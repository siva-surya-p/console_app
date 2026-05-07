package com.sivasuryap.miniTwitter.features.follow.follow;

import com.sivasuryap.miniTwitter.data.enums.NotificationType;
import com.sivasuryap.miniTwitter.data.model.Notification;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

class FollowModel {

    User findUser(String username) {
        return MiniTwitterDB.getInstance().getUserByUsername(username);
    }

    boolean isFollowing(long followerId, long followingId) {
        return MiniTwitterDB.getInstance().isFollowing(followerId, followingId);
    }

    /** Returns true = now following, false = unfollowed. */
    boolean toggleFollow(User actor, User target) {
        boolean followed = MiniTwitterDB.getInstance().toggleFollow(actor.getId(), target.getId());
        if (followed) {
            Notification n = new Notification();
            n.setUserId(target.getId());
            n.setActorId(actor.getId());
            n.setPostId(null);
            n.setType(NotificationType.FOLLOW);
            n.setMessage("@" + actor.getUsername() + " started following you");
            n.setCreatedAt(System.currentTimeMillis());
            MiniTwitterDB.getInstance().addNotification(n);
        }
        return followed;
    }

    int getFollowerCount(long userId) {
        return MiniTwitterDB.getInstance().getFollowerCount(userId);
    }

    int getFollowingCount(long userId) {
        return MiniTwitterDB.getInstance().getFollowingCount(userId);
    }
}
