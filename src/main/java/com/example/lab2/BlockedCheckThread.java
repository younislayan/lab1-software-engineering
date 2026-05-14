package com.example.lab2;

public class BlockedCheckThread extends Thread {
    private User user;
    private LoginSecurityManager securityManager;
    private boolean blocked;

    public BlockedCheckThread(User user, LoginSecurityManager securityManager) {
        this.user = user;
        this.securityManager = securityManager;
        this.blocked = false;
    }

    @Override
    public void run() {
        blocked = securityManager.isUserBlocked(user);
    }

    public boolean isBlocked() {
        return blocked;
    }
}