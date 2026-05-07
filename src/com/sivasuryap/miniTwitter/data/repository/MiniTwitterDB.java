package com.sivasuryap.miniTwitter.data.repository;

import com.sivasuryap.miniTwitter.data.enums.CommentStatus;
import com.sivasuryap.miniTwitter.data.enums.NotificationType;
import com.sivasuryap.miniTwitter.data.enums.PostStatus;
import com.sivasuryap.miniTwitter.data.enums.UserStatus;
import com.sivasuryap.miniTwitter.data.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MiniTwitterDB {

    private static MiniTwitterDB instance = null;

    private MiniTwitterDB() {
    }

    public static MiniTwitterDB getInstance() {
        if (instance == null) {
            instance = new MiniTwitterDB();
        }
        return instance;
    }

    // ── Storage

    private final List<User>         users         = new ArrayList<>();
    private final List<Post>         posts         = new ArrayList<>();
    private final List<Comment>      comments      = new ArrayList<>();
    private final List<Like>         likes         = new ArrayList<>();
    private final List<Follow>       follows       = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();

    private long userPk         = 0L;
    private long postPk         = 0L;
    private long commentPk      = 0L;
    private long likePk         = 0L;
    private long followPk       = 0L;
    private long notificationPk = 0L;

    // ── User

    public User addUser(User user) {
        if (user == null) return null;
        userPk++;
        user.setId(userPk);
        if (user.getCreatedAt() == 0) user.setCreatedAt(System.currentTimeMillis());
        if (user.getStatus() == null) user.setStatus(UserStatus.ACTIVE);
        users.add(user);
        return user;
    }

    public User getUserById(long id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public User getUserByEmail(String email) {
        if (email == null) return null;
        String key = email.trim().toLowerCase(Locale.ROOT);
        for (User u : users) {
            if (u.getEmail() != null && u.getEmail().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return u;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        if (username == null) return null;
        String key = username.trim().toLowerCase(Locale.ROOT);
        for (User u : users) {
            if (u.getUsername() != null && u.getUsername().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return u;
            }
        }
        return null;
    }

    public List<User> searchUsersByUsername(String query) {
        List<User> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return result;
        String key = query.trim().toLowerCase(Locale.ROOT);
        for (User u : users) {
            if (u.getStatus() != UserStatus.ACTIVE) continue;
            if (u.getUsername() != null && u.getUsername().toLowerCase(Locale.ROOT).contains(key)) {
                result.add(u);
            }
        }
        return result;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // ── Post

    public Post addPost(Post post) {
        if (post == null) return null;
        postPk++;
        post.setId(postPk);
        if (post.getCreatedAt() == 0) post.setCreatedAt(System.currentTimeMillis());
        if (post.getStatus() == null) post.setStatus(PostStatus.ACTIVE);
        posts.add(post);
        return post;
    }

    public Post getPostById(long id) {
        for (Post p : posts) {
            if (p.getId() == id && p.getStatus() == PostStatus.ACTIVE) return p;
        }
        return null;
    }

    public boolean deletePost(long postId, long requesterId) {
        for (Post p : posts) {
            if (p.getId() == postId && p.getUserId() == requesterId
                    && p.getStatus() == PostStatus.ACTIVE) {
                p.setStatus(PostStatus.DELETED);
                return true;
            }
        }
        return false;
    }

    public List<Post> getPostsByUser(long userId) {
        List<Post> result = new ArrayList<>();
        for (Post p : posts) {
            if (p.getUserId() == userId && p.getStatus() == PostStatus.ACTIVE) result.add(p);
        }
        return result;
    }

     public List<Post> getFeedForUser(long followerId) {
        List<Long> followingIds = new ArrayList<>();
        for (Follow f : follows) {
            if (f.getFollowerId() == followerId) followingIds.add(f.getFollowingId());
        }
        List<Post> result = new ArrayList<>();
        for (Post p : posts) {
            if (p.getStatus() == PostStatus.ACTIVE && followingIds.contains(p.getUserId())) {
                result.add(p);
            }
        }
        // Sort newest first
        result.sort((a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
        return result;
    }

    // ── Comment

    public Comment addComment(Comment comment) {
        if (comment == null) return null;
        commentPk++;
        comment.setId(commentPk);
        if (comment.getCreatedAt() == 0) comment.setCreatedAt(System.currentTimeMillis());
        if (comment.getStatus() == null) comment.setStatus(CommentStatus.ACTIVE);
        comments.add(comment);
        return comment;
    }

    public List<Comment> getCommentsForPost(long postId) {
        List<Comment> result = new ArrayList<>();
        for (Comment c : comments) {
            if (c.getPostId() == postId && c.getStatus() == CommentStatus.ACTIVE) result.add(c);
        }
        return result;
    }

    public boolean deleteComment(long commentId, long requesterId) {
        for (Comment c : comments) {
            if (c.getId() == commentId && c.getUserId() == requesterId
                    && c.getStatus() == CommentStatus.ACTIVE) {
                c.setStatus(CommentStatus.DELETED);
                return true;
            }
        }
        return false;
    }

    // ── Like

    public boolean hasLiked(long userId, long postId) {
        for (Like l : likes) {
            if (l.getUserId() == userId && l.getPostId() == postId) return true;
        }
        return false;
    }

    /** Returns true if liked, false if unliked. */
    public boolean toggleLike(long userId, long postId) {
        for (int i = 0; i < likes.size(); i++) {
            Like l = likes.get(i);
            if (l.getUserId() == userId && l.getPostId() == postId) {
                likes.remove(i);
                Post post = getPostById(postId);
                if (post != null) post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
                return false; // unliked
            }
        }
        likePk++;
        Like like = new Like();
        like.setId(likePk);
        like.setUserId(userId);
        like.setPostId(postId);
        like.setCreatedAt(System.currentTimeMillis());
        likes.add(like);
        Post post = getPostById(postId);
        if (post != null) post.setLikeCount(post.getLikeCount() + 1);
        return true; // liked
    }

    // ── Follow

    public boolean isFollowing(long followerId, long followingId) {
        for (Follow f : follows) {
            if (f.getFollowerId() == followerId && f.getFollowingId() == followingId) return true;
        }
        return false;
    }

    /** Returns true if followed, false if unfollowed. */
    public boolean toggleFollow(long followerId, long followingId) {
        for (int i = 0; i < follows.size(); i++) {
            Follow f = follows.get(i);
            if (f.getFollowerId() == followerId && f.getFollowingId() == followingId) {
                follows.remove(i);
                return false; // unfollowed
            }
        }
        followPk++;
        Follow follow = new Follow();
        follow.setId(followPk);
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        follow.setCreatedAt(System.currentTimeMillis());
        follows.add(follow);
        return true; // followed
    }

    public List<User> getFollowers(long userId) {
        List<User> result = new ArrayList<>();
        for (Follow f : follows) {
            if (f.getFollowingId() == userId) {
                User u = getUserById(f.getFollowerId());
                if (u != null) result.add(u);
            }
        }
        return result;
    }

    public List<User> getFollowing(long userId) {
        List<User> result = new ArrayList<>();
        for (Follow f : follows) {
            if (f.getFollowerId() == userId) {
                User u = getUserById(f.getFollowingId());
                if (u != null) result.add(u);
            }
        }
        return result;
    }

    public int getFollowerCount(long userId) {
        int count = 0;
        for (Follow f : follows) {
            if (f.getFollowingId() == userId) count++;
        }
        return count;
    }

    public int getFollowingCount(long userId) {
        int count = 0;
        for (Follow f : follows) {
            if (f.getFollowerId() == userId) count++;
        }
        return count;
    }

    // ── Notification

    public Notification addNotification(Notification notification) {
        if (notification == null) return null;
        notificationPk++;
        notification.setId(notificationPk);
        if (notification.getCreatedAt() == 0) notification.setCreatedAt(System.currentTimeMillis());
        notifications.add(notification);
        return notification;
    }

    public List<Notification> getNotificationsForUser(long userId) {
        List<Notification> result = new ArrayList<>();
        for (Notification n : notifications) {
            if (n.getUserId() == userId) result.add(n);
        }
        // Newest first
        result.sort((a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
        return result;
    }

    public int markAllNotificationsRead(long userId) {
        int count = 0;
        for (Notification n : notifications) {
            if (n.getUserId() == userId && !n.isRead()) {
                n.setRead(true);
                count++;
            }
        }
        return count;
    }

    public int getUnreadNotificationCount(long userId) {
        int count = 0;
        for (Notification n : notifications) {
            if (n.getUserId() == userId && !n.isRead()) count++;
        }
        return count;
    }
}
