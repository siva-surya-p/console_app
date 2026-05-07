package com.sivasuryap.miniTwitter;

import com.sivasuryap.miniTwitter.features.signin.SignInView;
import com.sivasuryap.miniTwitter.features.signup.SignUpView;
import com.sivasuryap.miniTwitter.util.ConsoleInput;

import java.util.Scanner;

public class MiniTwitterApplication {

    public static final String VERSION_NAME = "1.0.0";

    public static void main(String[] args) {
//        System.out.println("╔══════════════════════════════╗");
//        System.out.println("║       Mini Twitter           ║");
//        System.out.println("║       Version " + VERSION_NAME + "          ║");
//        System.out.println("╚══════════════════════════════╝");

        System.out.println("       Mini Twitter           ");
       System.out.println("       Version " + VERSION_NAME + "          ║");
        showLandingMenu();
    }

    private static void showLandingMenu() {
        Scanner scanner = ConsoleInput.getScanner();
        while (true) {
            System.out.println();
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    new SignUpView().init();
                    break;
                case "2":
                    new SignInView().init();
                    break;
                case "3":
                    System.out.println("Thank you for using Mini Twitter. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
