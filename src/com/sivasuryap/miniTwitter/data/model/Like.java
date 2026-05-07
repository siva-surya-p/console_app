package com.sivasuryap.miniTwitter.data.model;

public class Like {

    private long id;
    private long userId;
    private long postId;
    private long createdAt;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getPostId() { return postId; }
    public void setPostId(long postId) { this.postId = postId; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
