package com.sivasuryap.miniTwitter.features.signup;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.features.home.HomeView;
import com.sivasuryap.miniTwitter.util.ConsoleInput;

import java.util.Scanner;

public class SignUpView {

    private final SignUpModel model;
    private final Scanner scanner;

    public SignUpView() {
        this.model = new SignUpModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Create Account ===");

        // Username
        String username;
        while (true) {
            System.out.print("Username: ");
            username = scanner.nextLine().trim();
            String error = model.validateUsername(username);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        // Email
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            String error = model.validateEmail(email);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        // Password
        String password;
        while (true) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            String error = model.validatePassword(password);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        // Confirm Password
        while (true) {
            System.out.print("Confirm Password: ");
            String confirm = scanner.nextLine();
            String error = model.validateConfirmPassword(password, confirm);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        // Bio (optional)
        String bio;
        while (true) {
            System.out.print("Bio (optional, press Enter to skip): ");
            bio = scanner.nextLine();
            String error = model.validateBio(bio);
            if (error == null) break;
            System.out.println("Error: " + error);
        }

        model.register(username, email, password, bio);
    }

    void showError(String message) {
        System.out.println("Error: " + message);
    }

    void onSignUpSuccess(User user) {
        System.out.println("Account created successfully! Welcome, @" + user.getUsername() + "!");
        new HomeView(user).init();
    }
}
