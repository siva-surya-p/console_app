package com.sivasuryap.miniTwitter.features.home;

import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.features.follow.follow.FollowView;
import com.sivasuryap.miniTwitter.features.follow.followers.FollowerListView;
import com.sivasuryap.miniTwitter.features.follow.following.FollowingListView;
import com.sivasuryap.miniTwitter.features.notification.NotificationView;
import com.sivasuryap.miniTwitter.features.post.create.PostCreateView;
import com.sivasuryap.miniTwitter.features.post.delete.PostDeleteView;
import com.sivasuryap.miniTwitter.features.post.detail.PostDetailView;
import com.sivasuryap.miniTwitter.features.post.feed.FeedView;
import com.sivasuryap.miniTwitter.features.profile.ProfileView;
import com.sivasuryap.miniTwitter.features.search.SearchView;
import com.sivasuryap.miniTwitter.util.ConsoleInput;

import java.util.Scanner;

public class HomeView {

    private final HomeModel model;
    private final User currentUser;
    private final Scanner scanner;

    public HomeView(User currentUser) {
        this.model = new HomeModel();
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        while (true) {
            int unread = model.getUnreadCount(currentUser.getId());
            String notifLabel = unread > 0
                    ? "9.  Notifications (" + unread + " unread)"
                    : "9.  Notifications";

            System.out.println();
            System.out.println("─── @" + currentUser.getUsername() + " ───────────────────");
            System.out.println("1.  My Feed");
            System.out.println("2.  Create Post");
            System.out.println("3.  My Posts");
            System.out.println("4.  View Post Details");
            System.out.println("5.  Delete My Post");
            System.out.println("6.  Follow / Unfollow a User");
            System.out.println("7.  My Followers");
            System.out.println("8.  People I Follow");
            System.out.println(notifLabel);
            System.out.println("10. My Profile");
            System.out.println("11. Search Users");
            System.out.println("12. Sign Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    new FeedView(currentUser).init();
                    break;
                case "2":
                    new PostCreateView(currentUser).init();
                    break;
                case "3":
                    new FeedView(currentUser).initMyPosts();
                    break;
                case "4":
                    new PostDetailView(currentUser).init();
                    break;
                case "5":
                    new PostDeleteView(currentUser).init();
                    break;
                case "6":
                    new FollowView(currentUser).init();
                    break;
                case "7":
                    new FollowerListView(currentUser).init();
                    break;
                case "8":
                    new FollowingListView(currentUser).init();
                    break;
                case "9":
                    new NotificationView(currentUser).init();
                    break;
                case "10":
                    new ProfileView(currentUser).init();
                    break;
                case "11":
                    new SearchView(currentUser).init();
                    break;
                case "12":
                    System.out.println("Signed out. See you soon, @" + currentUser.getUsername() + "!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
