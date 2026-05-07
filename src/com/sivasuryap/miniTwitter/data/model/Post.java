package com.sivasuryap.miniTwitter.data.model;

import com.sivasuryap.miniTwitter.data.enums.PostStatus;

public class Post {

    private long id;
    private long userId;
    private String title;
    private String content;
    private int likeCount;
    private long createdAt;
    private PostStatus status;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public PostStatus getStatus() { return status; }
    public void setStatus(PostStatus status) { this.status = status; }
}
