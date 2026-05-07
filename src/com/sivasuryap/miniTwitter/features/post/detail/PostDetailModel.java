package com.sivasuryap.miniTwitter.features.post.detail;

import com.sivasuryap.miniTwitter.data.model.Comment;
import com.sivasuryap.miniTwitter.data.model.Notification;
import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.enums.NotificationType;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;

class PostDetailModel {

    private static final int MAX_COMMENT = 280;

    private final PostDetailView view;

    PostDetailModel(PostDetailView view) {
        this.view = view;
    }

    Post getPost(long postId) {
        return MiniTwitterDB.getInstance().getPostById(postId);
    }

    User getUser(long userId) {
        return MiniTwitterDB.getInstance().getUserById(userId);
    }

    List<Comment> getComments(long postId) {
        return MiniTwitterDB.getInstance().getCommentsForPost(postId);
    }

    boolean hasLiked(long userId, long postId) {
        return MiniTwitterDB.getInstance().hasLiked(userId, postId);
    }

    /** Returns true = liked, false = unliked. */
    boolean toggleLike(User actor, Post post) {
        boolean liked = MiniTwitterDB.getInstance().toggleLike(actor.getId(), post.getId());
        if (liked && post.getUserId() != actor.getId()) {
            sendNotification(post.getUserId(), actor.getId(), post.getId(),
                    NotificationType.LIKE,
                    "@" + actor.getUsername() + " liked your post: \"" + post.getTitle() + "\"");
        }
        return liked;
    }

    String validateComment(String content) {
        if (content == null || content.trim().isEmpty()) return "Comment cannot be empty";
        if (content.trim().length() > MAX_COMMENT) {
            return "Comment cannot exceed " + MAX_COMMENT + " characters";
        }
        return null;
    }

    Comment addComment(User actor, Post post, String content) {
        Comment comment = new Comment();
        comment.setUserId(actor.getId());
        comment.setPostId(post.getId());
        comment.setContent(content.trim());
        comment.setCreatedAt(System.currentTimeMillis());

        Comment saved = MiniTwitterDB.getInstance().addComment(comment);
        if (saved != null && post.getUserId() != actor.getId()) {
            sendNotification(post.getUserId(), actor.getId(), post.getId(),
                    NotificationType.COMMENT,
                    "@" + actor.getUsername() + " commented on your post: \"" + post.getTitle() + "\"");
        }
        return saved;
    }

    boolean deleteComment(long commentId, long requesterId) {
        return MiniTwitterDB.getInstance().deleteComment(commentId, requesterId);
    }

    private void sendNotification(long toUserId, long actorId, long postId,
                                  NotificationType type, String message) {
        Notification n = new Notification();
        n.setUserId(toUserId);
        n.setActorId(actorId);
        n.setPostId(postId);
        n.setType(type);
        n.setMessage(message);
        n.setCreatedAt(System.currentTimeMillis());
        MiniTwitterDB.getInstance().addNotification(n);
    }
}
