package com.sivasuryap.miniTwitter.features.post.create;

import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;

import java.util.Scanner;

public class PostCreateView {

    private final PostCreateModel model;
    private final User currentUser;
    private final Scanner scanner;

    public PostCreateView(User currentUser) {
        this.model = new PostCreateModel(this);
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Create Post ===");

        String title;
        while (true) {
            System.out.print("Title: ");
            title = scanner.nextLine();
            String error = model.validateTitle(title);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        String content;
        while (true) {
            System.out.print("Content (max 280 chars): ");
            content = scanner.nextLine();
            String error = model.validateContent(content);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        model.createPost(currentUser, title, content);
    }

    void showError(String message) {
        System.out.println("Error: " + message);
    }

    void onPostCreated(Post post) {
        System.out.println("Post #" + post.getId() + " published successfully!");
    }
}
