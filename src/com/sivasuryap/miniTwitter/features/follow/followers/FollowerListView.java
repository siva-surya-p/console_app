package com.sivasuryap.miniTwitter.features.follow.followers;

import com.sivasuryap.miniTwitter.data.model.User;

import java.util.List;

public class FollowerListView {

    private final FollowerListModel model;
    private final User currentUser;

    public FollowerListView(User currentUser) {
        this.model = new FollowerListModel();
        this.currentUser = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("=== My Followers ===");
        List<User> followers = model.getFollowers(currentUser.getId());
        if (followers.isEmpty()) {
            System.out.println("You have no followers yet.");
            return;
        }
        for (int i = 0; i < followers.size(); i++) {
            User u = followers.get(i);
            String bio = (u.getBio() != null && !u.getBio().isEmpty()) ? "  — " + u.getBio() : "";
            System.out.println((i + 1) + ". @" + u.getUsername() + bio);
        }
        System.out.println("Total: " + followers.size() + " follower" + (followers.size() != 1 ? "s" : ""));
    }
}
