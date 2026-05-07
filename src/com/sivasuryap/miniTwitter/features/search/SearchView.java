package com.sivasuryap.miniTwitter.features.search;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;
import com.sivasuryap.miniTwitter.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class SearchView {

    private final SearchModel model;
    private final User currentUser;
    private final Scanner scanner;

    public SearchView(User currentUser) {
        this.model = new SearchModel();
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Search Users ===");
        System.out.print("Search by username: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        List<User> results = model.searchUsers(query);
        if (results.isEmpty()) {
            System.out.println("No users found matching \"" + query + "\".");
            return;
        }

        System.out.println("Found " + results.size() + " user" + (results.size() != 1 ? "s" : "") + ":");
        for (int i = 0; i < results.size(); i++) {
            User u = results.get(i);
            if (u.getId() == currentUser.getId()) continue;
            String followStatus = model.isFollowing(currentUser.getId(), u.getId()) ? " [Following]" : "";
            String bio = ParseHelper.orDash(u.getBio());
            System.out.println((i + 1) + ". @" + u.getUsername() + followStatus);
            System.out.println("     Bio: " + bio
                    + "  |  Followers: " + model.getFollowerCount(u.getId())
                    + "  |  Posts: " + model.getPostCount(u.getId()));
        }
    }
}
