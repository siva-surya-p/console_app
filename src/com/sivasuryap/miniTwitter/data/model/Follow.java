package com.sivasuryap.miniTwitter.data.model;

public class Follow {

    private long id;
    private long followerId;
    private long followingId;
    private long createdAt;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getFollowerId() { return followerId; }
    public void setFollowerId(long followerId) { this.followerId = followerId; }

    public long getFollowingId() { return followingId; }
    public void setFollowingId(long followingId) { this.followingId = followingId; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
