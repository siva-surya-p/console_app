package com.sivasuryap.miniTwitter.features.follow.follow;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;

import java.util.Scanner;

public class FollowView {

    private final FollowModel model;
    private final User currentUser;
    private final Scanner scanner;

    public FollowView(User currentUser) {
        this.model = new FollowModel();
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Follow / Unfollow ===");
        System.out.print("Enter username to follow/unfollow: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        User target = model.findUser(username);
        if (target == null) {
            System.out.println("User @" + username + " not found.");
            return;
        }
        if (target.getId() == currentUser.getId()) {
            System.out.println("You cannot follow yourself.");
            return;
        }

        boolean alreadyFollowing = model.isFollowing(currentUser.getId(), target.getId());
        System.out.println("User: @" + target.getUsername()
                + "  |  Followers: " + model.getFollowerCount(target.getId())
                + "  |  Following: " + model.getFollowingCount(target.getId()));
        System.out.println("You are currently " + (alreadyFollowing ? "following" : "not following") + " this user.");
        System.out.print("Confirm " + (alreadyFollowing ? "unfollow" : "follow") + "? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Cancelled.");
            return;
        }

        boolean followed = model.toggleFollow(currentUser, target);
        if (followed) {
            System.out.println("You are now following @" + target.getUsername() + "!");
        } else {
            System.out.println("You unfollowed @" + target.getUsername() + ".");
        }
    }
}
