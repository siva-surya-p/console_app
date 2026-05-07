package com.sivasuryap.miniTwitter.data.model;

import com.sivasuryap.miniTwitter.data.enums.CommentStatus;

public class Comment {

    private long id;
    private long userId;
    private long postId;
    private String content;
    private long createdAt;
    private CommentStatus status;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getPostId() { return postId; }
    public void setPostId(long postId) { this.postId = postId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public CommentStatus getStatus() { return status; }
    public void setStatus(CommentStatus status) { this.status = status; }
}
