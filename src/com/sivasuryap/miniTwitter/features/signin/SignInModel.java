package com.sivasuryap.miniTwitter.features.signin;

import com.sivasuryap.miniTwitter.data.enums.UserStatus;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.data.repository.MiniTwitterDB;
import com.sivasuryap.miniTwitter.util.HashHelper;

class SignInModel {

    private final SignInView signInView;

    SignInModel(SignInView signInView) {
        this.signInView = signInView;
    }

    void signIn(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            signInView.showError("Email and password are required.");
            return;
        }
        User user = MiniTwitterDB.getInstance().getUserByEmail(email.trim());
        if (user == null || !HashHelper.verifyPassword(password, user.getPassword())) {
            signInView.showError("Invalid email or password.");
            return;
        }
        if (user.getStatus() == UserStatus.SUSPENDED) {
            signInView.showError("Your account has been suspended. Contact support.");
            return;
        }
        if (user.getStatus() == UserStatus.DEACTIVATED) {
            signInView.showError("This account has been deactivated.");
            return;
        }
        signInView.onSignInSuccess(user);
    }
}
