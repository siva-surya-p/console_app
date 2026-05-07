package com.sivasuryap.miniTwitter.features.profile;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;
import com.sivasuryap.miniTwitter.util.ParseHelper;

import java.util.Scanner;

public class ProfileView {

    private final ProfileModel model;
    private final User currentUser;
    private final Scanner scanner;

    public ProfileView(User currentUser) {
        this.model = new ProfileModel(this);
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        while (true) {
            printProfile();
            System.out.println();
            System.out.println("1. Edit Profile");
            System.out.println("2. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    editProfile();
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void printProfile() {
        System.out.println();
        System.out.println("══════════════════════════════════════");
        System.out.println("  @" + currentUser.getUsername());
        System.out.println("  Email  : " + currentUser.getEmail());
        System.out.println("  Bio    : " + ParseHelper.orDash(currentUser.getBio()));
        System.out.println("  Joined : " + ParseHelper.formatDateTime(currentUser.getCreatedAt()));
        System.out.println("  Posts  : " + model.getPostCount(currentUser.getId())
                + "   Followers: " + model.getFollowerCount(currentUser.getId())
                + "   Following: " + model.getFollowingCount(currentUser.getId()));
        System.out.println("══════════════════════════════════════");
    }

    private void editProfile() {
        System.out.println();
        System.out.println("=== Edit Profile ===");
        System.out.println("(Press Enter to keep current value)");

        // Username
        String newUsername;
        while (true) {
            System.out.print("Username [" + currentUser.getUsername() + "]: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                newUsername = currentUser.getUsername();
                break;
            }
            String error = model.validateUsername(input, currentUser.getId());
            if (error == null) {
                newUsername = input;
                break;
            }
            System.out.println("Error: " + error);
        }

        // Bio
        String newBio;
        while (true) {
            System.out.print("Bio [" + ParseHelper.orDash(currentUser.getBio()) + "]: ");
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                newBio = currentUser.getBio();
                break;
            }
            String error = model.validateBio(input);
            if (error == null) {
                newBio = input;
                break;
            }
            System.out.println("Error: " + error);
        }

        model.updateProfile(currentUser, newUsername, newBio);
    }

    void onProfileUpdated(User user) {
        System.out.println("Profile updated successfully!");
    }
}
