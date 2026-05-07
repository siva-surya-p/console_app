package com.sivasuryap.miniTwitter.features.post.detail;

import com.sivasuryap.miniTwitter.data.model.Comment;
import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;
import com.sivasuryap.miniTwitter.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class PostDetailView {

    private final PostDetailModel model;
    private final User currentUser;
    private final Scanner scanner;

    public PostDetailView(User currentUser) {
        this.model = new PostDetailModel(this);
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.print("Enter Post ID: ");
        String input = scanner.nextLine().trim();
        long postId;
        try {
            postId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Post ID.");
            return;
        }

        Post post = model.getPost(postId);
        if (post == null) {
            System.out.println("Post not found.");
            return;
        }

        showPostDetail(post);
    }

    private void showPostDetail(Post post) {
        User author = model.getUser(post.getUserId());
        String authorName = (author != null) ? "@" + author.getUsername() : "Unknown";
        boolean liked = model.hasLiked(currentUser.getId(), post.getId());

        while (true) {
            System.out.println();
            System.out.println("══════════════════════════════════════");
            System.out.println("Post #" + post.getId() + " by " + authorName);
            System.out.println("Posted: " + ParseHelper.formatDateTime(post.getCreatedAt()));
            System.out.println("Title  : " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Likes  : " + post.getLikeCount() + (liked ? " [You liked this]" : ""));
            System.out.println("══════════════════════════════════════");

            List<Comment> comments = model.getComments(post.getId());
            if (comments.isEmpty()) {
                System.out.println("No comments yet.");
            } else {
                System.out.println("Comments (" + comments.size() + "):");
                for (Comment c : comments) {
                    User commenter = model.getUser(c.getUserId());
                    String cName = (commenter != null) ? "@" + commenter.getUsername() : "Unknown";
                    System.out.println("  [#" + c.getId() + "] " + cName + ": " + c.getContent()
                            + "  (" + ParseHelper.formatDateTime(c.getCreatedAt()) + ")");
                }
            }

            System.out.println();
            System.out.println("1. " + (liked ? "Unlike" : "Like"));
            System.out.println("2. Add Comment");
            System.out.println("3. Delete My Comment");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    liked = model.toggleLike(currentUser, post);
                    System.out.println(liked ? "You liked this post!" : "Like removed.");
                    break;
                case "2":
                    addComment(post);
                    break;
                case "3":
                    deleteComment();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addComment(Post post) {
        String content;
        while (true) {
            System.out.print("Your comment: ");
            content = scanner.nextLine();
            String error = model.validateComment(content);
            if (error == null) break;
            System.out.println("Error: " + error);
        }
        Comment saved = model.addComment(currentUser, post, content);
        if (saved != null) {
            System.out.println("Comment added!");
        } else {
            System.out.println("Could not add comment.");
        }
    }

    private void deleteComment() {
        System.out.print("Enter Comment ID to delete: ");
        String input = scanner.nextLine().trim();
        long commentId;
        try {
            commentId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Comment ID.");
            return;
        }
        boolean deleted = model.deleteComment(commentId, currentUser.getId());
        System.out.println(deleted ? "Comment deleted." : "Comment not found or you don't own it.");
    }
}
