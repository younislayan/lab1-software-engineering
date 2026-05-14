package com.example.lab2;

public class FailedLoginThread extends Thread {
    private User user;
    private LoginSecurityManager securityManager;

    public FailedLoginThread(User user, LoginSecurityManager securityManager) {
        this.user = user;
        this.securityManager = securityManager;
    }

    @Override
    public void run() {
        securityManager.increaseFailedAttempts(user);
    }
}