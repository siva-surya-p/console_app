package com.sivasuryap.miniTwitter.data.model;

import com.sivasuryap.miniTwitter.data.enums.NotificationType;

public class Notification {

    private long id;
    private long userId;
    private long actorId;
    private Long postId;
    private String message;
    private NotificationType type;
    private boolean isRead;
    private long createdAt;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getActorId() { return actorId; }
    public void setActorId(long actorId) { this.actorId = actorId; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
