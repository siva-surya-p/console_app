package com.sivasuryap.miniTwitter.features.post.delete;

import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;
import com.sivasuryap.miniTwitter.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class PostDeleteView {

    private final PostDeleteModel model;
    private final User currentUser;
    private final Scanner scanner;

    public PostDeleteView(User currentUser) {
        this.model = new PostDeleteModel();
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Delete Post ===");
        List<Post> posts = model.getMyPosts(currentUser.getId());
        if (posts.isEmpty()) {
            System.out.println("You have no posts to delete.");
            return;
        }
        for (Post p : posts) {
            System.out.println("[#" + p.getId() + "] " + p.getTitle()
                    + "  |  " + ParseHelper.formatDateTime(p.getCreatedAt()));
        }
        System.out.print("Enter Post ID to delete (or 0 to cancel): ");
        String input = scanner.nextLine().trim();
        long postId;
        try {
            postId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        if (postId == 0) {
            System.out.println("Cancelled.");
            return;
        }
        System.out.print("Are you sure you want to delete Post #" + postId + "? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Cancelled.");
            return;
        }
        boolean deleted = model.deletePost(postId, currentUser.getId());
        System.out.println(deleted ? "Post deleted." : "Post not found or you don't own it.");
    }
}
