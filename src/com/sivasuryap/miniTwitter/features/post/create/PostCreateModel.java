package com.sivasuryap.miniTwitter.features.post.create;

import com.sivasuryap.miniTwitter.data.enums.PostStatus;
import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

class PostCreateModel {

    private static final int MIN_TITLE   = 3;
    private static final int MAX_TITLE   = 100;
    private static final int MAX_CONTENT = 280;

    private final PostCreateView view;

    PostCreateModel(PostCreateView view) {
        this.view = view;
    }

    String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) return "Title cannot be empty";
        int len = title.trim().length();
        if (len < MIN_TITLE || len > MAX_TITLE) {
            return "Title must be between " + MIN_TITLE + " and " + MAX_TITLE + " characters";
        }
        return null;
    }

    String validateContent(String content) {
        if (content == null || content.trim().isEmpty()) return "Content cannot be empty";
        if (content.trim().length() > MAX_CONTENT) {
            return "Content cannot exceed " + MAX_CONTENT + " characters";
        }
        return null;
    }

    void createPost(User author, String title, String content) {
        Post post = new Post();
        post.setUserId(author.getId());
        post.setTitle(title.trim());
        post.setContent(content.trim());
        post.setStatus(PostStatus.ACTIVE);
        post.setCreatedAt(System.currentTimeMillis());

        Post saved = MiniTwitterDB.getInstance().addPost(post);
        if (saved == null) {
            view.showError("Could not create post. Please try again.");
            return;
        }
        view.onPostCreated(saved);
    }
}
