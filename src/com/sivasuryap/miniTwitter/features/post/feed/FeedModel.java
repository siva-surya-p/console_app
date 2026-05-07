package com.sivasuryap.miniTwitter.features.post.feed;

import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class FeedModel {

    List<Post> getFeed(long userId) {
        return MiniTwitterDB.getInstance().getFeedForUser(userId);
    }

    List<Post> getMyPosts(long userId) {
        List<Post> posts = MiniTwitterDB.getInstance().getPostsByUser(userId);
        posts.sort((a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
        return posts;
    }

    User getAuthor(long userId) {
        return MiniTwitterDB.getInstance().getUserById(userId);
    }

    boolean hasLiked(long userId, long postId) {
        return MiniTwitterDB.getInstance().hasLiked(userId, postId);
    }
}
