package com.sivasuryap.miniTwitter.features.notification;

import com.sivasuryap.miniTwitter.data.model.Notification;
import com.sivasuryap.miniTwitter.data.model.User;
import com.sivasuryap.miniTwitter.util.ConsoleInput;
import com.sivasuryap.miniTwitter.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class NotificationView {

    private final NotificationModel model;
    private final User currentUser;
    private final Scanner scanner;

    public NotificationView(User currentUser) {
        this.model = new NotificationModel();
        this.currentUser = currentUser;
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        System.out.println();
        System.out.println("=== Notifications ===");
        List<Notification> notifications = model.getNotifications(currentUser.getId());

        if (notifications.isEmpty()) {
            System.out.println("No notifications yet.");
            return;
        }

        for (Notification n : notifications) {
            String readMark = n.isRead() ? "  " : "* ";
            String postInfo = (n.getPostId() != null) ? "  [Post #" + n.getPostId() + "]" : "";
            System.out.println(readMark + "[" + n.getType() + "] " + n.getMessage()
                    + postInfo + "  (" + ParseHelper.formatDateTime(n.getCreatedAt()) + ")");
        }

        System.out.println();
        System.out.print("Mark all as read? (Y/N): ");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("Y")) {
            int count = model.markAllRead(currentUser.getId());
            System.out.println(count + " notification" + (count != 1 ? "s" : "") + " marked as read.");
        }
    }
}
