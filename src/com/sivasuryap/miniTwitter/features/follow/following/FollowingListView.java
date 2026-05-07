package com.sivasuryap.miniTwitter.features.follow.following;

import com.sivasuryap.miniTwitter.data.model.User;

import java.util.List;

public class FollowingListView {

    private final FollowingListModel model;
    private final User currentUser;

    public FollowingListView(User currentUser) {
        this.model = new FollowingListModel();
        this.currentUser = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("=== People I Follow ===");
        List<User> following = model.getFollowing(currentUser.getId());
        if (following.isEmpty()) {
            System.out.println("You are not following anyone yet.");
            return;
        }
        for (int i = 0; i < following.size(); i++) {
            User u = following.get(i);
            String bio = (u.getBio() != null && !u.getBio().isEmpty()) ? "  — " + u.getBio() : "";
            System.out.println((i + 1) + ". @" + u.getUsername() + bio);
        }
        System.out.println("Total: " + following.size() + " following");
    }
}
