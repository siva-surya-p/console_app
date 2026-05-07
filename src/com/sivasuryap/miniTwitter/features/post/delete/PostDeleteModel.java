package com.sivasuryap.miniTwitter.features.post.delete;

import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class PostDeleteModel {

    List<Post> getMyPosts(long userId) {
        List<Post> posts = MiniTwitterDB.getInstance().getPostsByUser(userId);
        posts.sort((a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
        return posts;
    }

    boolean deletePost(long postId, long userId) {
        return MiniTwitterDB.getInstance().deletePost(postId, userId);
    }
}
