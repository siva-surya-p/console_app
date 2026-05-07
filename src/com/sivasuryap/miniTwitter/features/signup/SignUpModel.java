package com.sivasuryap.miniTwitter.features.signup;

import com.sivasuryap.miniTwitter.data.enums.UserStatus;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;
import com.sivasuryap.miniTwitter.util.HashHelper;

import java.util.regex.Pattern;

class SignUpModel {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[A-Za-z0-9_]{3,20}$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");

    private static final int MAX_BIO = 160;

    private final SignUpView signUpView;

    SignUpModel(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) return "Username cannot be empty";
        if (!USERNAME_PATTERN.matcher(username.trim()).matches()) {
            return "Username must be 3-20 characters (letters, numbers, underscores only)";
        }
        if (MiniTwitterDB.getInstance().getUserByUsername(username.trim()) != null) {
            return "Username is already taken";
        }
        return null;
    }

    String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) return "Email cannot be empty";
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) return "Enter a valid email address";
        if (MiniTwitterDB.getInstance().getUserByEmail(email.trim()) != null) {
            return "This email is already registered";
        }
        return null;
    }

    String validatePassword(String password) {
        if (password == null || password.isEmpty()) return "Password cannot be empty";
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return "Password must be at least 8 characters and contain letters and numbers";
        }
        return null;
    }

    String validateConfirmPassword(String password, String confirm) {
        if (confirm == null || !confirm.equals(password)) return "Passwords do not match";
        return null;
    }

    String validateBio(String bio) {
        if (bio == null || bio.trim().isEmpty()) return null; // bio is optional
        if (bio.trim().length() > MAX_BIO) return "Bio cannot exceed " + MAX_BIO + " characters";
        return null;
    }

    void register(String username, String email, String password, String bio) {
        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(HashHelper.hashPassword(password));
        user.setBio(bio != null && !bio.trim().isEmpty() ? bio.trim() : null);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(System.currentTimeMillis());

        User saved = MiniTwitterDB.getInstance().addUser(user);
        if (saved == null) {
            signUpView.showError("Could not create account. Please try again.");
            return;
        }
        signUpView.onSignUpSuccess(saved);
    }
}
