package com.sivasuryap.miniTwitter.features.profile;

import com.sivasuryap.miniTwitter.data.model.Post;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;

import java.util.List;
import java.util.regex.Pattern;

class ProfileModel {

    private static final int MAX_BIO = 160;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,20}$");

    private final ProfileView view;

    ProfileModel(ProfileView view) {
        this.view = view;
    }

    int getPostCount(long userId) {
        return MiniTwitterDB.getInstance().getPostsByUser(userId).size();
    }

    int getFollowerCount(long userId) {
        return MiniTwitterDB.getInstance().getFollowerCount(userId);
    }

    int getFollowingCount(long userId) {
        return MiniTwitterDB.getInstance().getFollowingCount(userId);
    }

    String validateUsername(String username, long currentUserId) {
        if (username == null || username.trim().isEmpty()) return "Username cannot be empty";
        if (!USERNAME_PATTERN.matcher(username.trim()).matches()) {
            return "Username must be 3-20 characters (letters, numbers, underscores only)";
        }
        User existing = MiniTwitterDB.getInstance().getUserByUsername(username.trim());
        if (existing != null && existing.getId() != currentUserId) {
            return "Username is already taken";
        }
        return null;
    }

    String validateBio(String bio) {
        if (bio == null || bio.trim().isEmpty()) return null;
        if (bio.trim().length() > MAX_BIO) return "Bio cannot exceed " + MAX_BIO + " characters";
        return null;
    }

    void updateProfile(User user, String newUsername, String newBio) {
        user.setUsername(newUsername.trim());
        user.setBio(newBio != null && !newBio.trim().isEmpty() ? newBio.trim() : null);
        view.onProfileUpdated(user);
    }
}
