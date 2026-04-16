public class User implements Comparable<User> {
    private String username;
    private String password;

    public User(String username, String password) {
        validateUsername(username);
        validatePassword(password);

        this.username = username;
        this.password = password;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (username.length() > 50) {
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }

        int atIndex = username.indexOf('@');

        if (atIndex <= 0 || atIndex != username.lastIndexOf('@') || atIndex == username.length() - 1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        String firstPart = username.substring(0, atIndex);
        String afterAt = username.substring(atIndex + 1);

        int lastDotIndex = afterAt.lastIndexOf('.');

        if (lastDotIndex <= 0 || lastDotIndex == afterAt.length() - 1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        String secondPart = afterAt.substring(0, lastDotIndex);
        String thirdPart = afterAt.substring(lastDotIndex + 1);

        if (!firstPart.matches("[A-Za-z0-9._%+\\-]+")) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (!secondPart.matches("[A-Za-z0-9][A-Za-z0-9.\\-]*")) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (!thirdPart.matches("[A-Za-z]{2,}")) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter a valid password");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Your password is too short, add more characters");
        }

        if (password.length() > 12) {
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }

        if (!password.matches("[A-Za-z0-9!@#$%^&*()+.,%_\\-]+")) {
            throw new IllegalArgumentException("Please enter a valid password");
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isLetter(ch)) {
                hasLetter = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSymbol = true;
            }
        }

        if (!hasLetter || !hasDigit || !hasSymbol) {
            throw new IllegalArgumentException("Please enter a valid password");
        }
    }

    @Override
    public String toString() {
        return username + " " + password;
    }

    @Override
    public int compareTo(User other) {
        return this.username.compareTo(other.username);
    }
}