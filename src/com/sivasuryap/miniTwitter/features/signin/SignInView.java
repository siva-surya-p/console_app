package com.sivasuryap.miniTwitter.features.signin;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.features.home.HomeView;
import com.sivasuryap.miniTwitter.util.ConsoleInput;

import java.util.Scanner;

public class SignInView {

    private final SignInModel model;
    private final Scanner scanner;

    public SignInView() {
        this.model = new SignInModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Sign In ===");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        model.signIn(email, password);
    }

    void showError(String message) {
        System.out.println("Error: " + message);
    }

    void onSignInSuccess(User user) {
        System.out.println("Welcome back, @" + user.getUsername() + "!");
        new HomeView(user).init();
    }
}
