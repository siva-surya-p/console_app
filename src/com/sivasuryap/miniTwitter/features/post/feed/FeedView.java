package com.sivasuryap.miniTwitter.features.post.feed;

import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ParseHelper;

import java.util.List;

public class FeedView {

    private final FeedModel model;
    private final User currentUser;

    public FeedView(User currentUser) {
        this.model = new FeedModel();
        this.currentUser = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("=== My Feed ===");
        List<Post> posts = model.getFeed(currentUser.getId());
        if (posts.isEmpty()) {
            System.out.println("Your feed is empty. Follow some users to see their posts!");
            return;
        }
        printPosts(posts);
    }

    public void initMyPosts() {
        System.out.println();
        System.out.println("=== My Posts ===");
        List<Post> posts = model.getMyPosts(currentUser.getId());
        if (posts.isEmpty()) {
            System.out.println("You haven't posted anything yet.");
            return;
        }
        printPosts(posts);
    }

    private void printPosts(List<Post> posts) {
        for (Post post : posts) {
            User author = model.getAuthor(post.getUserId());
            String authorName = (author != null) ? "@" + author.getUsername() : "Unknown";
            String liked = model.hasLiked(currentUser.getId(), post.getId()) ? " [Liked]" : "";
            System.out.println();
            System.out.println("─────────────────────────────────────");
            System.out.println("Post #" + post.getId() + " by " + authorName + "  |  " + ParseHelper.formatDateTime(post.getCreatedAt()));
            System.out.println("Title  : " + post.getTitle());
            System.out.println("Content: " + post.getContent());
            System.out.println("Likes  : " + post.getLikeCount() + liked);
        }
        System.out.println("─────────────────────────────────────");
        System.out.println("(" + posts.size() + " post" + (posts.size() != 1 ? "s" : "") + ")");
    }
}
