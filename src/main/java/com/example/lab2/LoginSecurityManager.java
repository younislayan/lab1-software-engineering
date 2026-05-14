package com.example.lab2;

public class LoginSecurityManager {
    private int maxAttempts;
    private int blockTimeSeconds;

    public LoginSecurityManager(int maxAttempts, int blockTimeSeconds) {
        this.maxAttempts = maxAttempts;
        this.blockTimeSeconds = blockTimeSeconds;
    }

    public synchronized boolean isUserBlocked(User user) {
        if (!user.isBlocked()) {
            return false;
        }

        long now = System.currentTimeMillis();
        long blockedTime = now - user.getBlockedAt();
        long blockTimeMillis = blockTimeSeconds * 1000L;

        if (blockedTime >= blockTimeMillis) {
            user.setBlocked(false);
            user.setFailedAttempts(0);
            user.setBlockedAt(0);
            return false;
        }

        return true;
    }

    public synchronized void increaseFailedAttempts(User user) {
        int attempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(attempts);

        if (attempts >= maxAttempts) {
            user.setBlocked(true);
            user.setBlockedAt(System.currentTimeMillis());
        }
    }

    public synchronized int getRemainingAttempts(User user) {
        return maxAttempts - user.getFailedAttempts();
    }
}