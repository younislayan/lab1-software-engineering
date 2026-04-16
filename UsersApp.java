import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersApp {

    private static Scanner openInput() throws FileNotFoundException {
        String[] possibleNames = {
            "users.txt",
            "Users.txt",
            "Users.txt.txt",
            "txt.users",
            "users"
        };

        for (String name : possibleNames) {
            File file = new File(name);
            if (file.exists() && file.isFile()) {
                return new Scanner(file);
            }
        }

        return new Scanner(System.in);
    }

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<User>();
        Pattern linePattern = Pattern.compile("^(\\S+)\\s+(.+)$");
        Scanner reader = null;

        try {
            reader = openInput();

            while (reader.hasNextLine()) {
                String originalLine = reader.nextLine();
                String line = originalLine.trim();

                if (line.isEmpty()) {
                    continue;
                }

                Matcher matcher = linePattern.matcher(line);

                if (!matcher.matches()) {
                    System.err.println(line + " Please enter a valid password");
                    continue;
                }

                String username = matcher.group(1);
                String password = matcher.group(2).trim();

                try {
                    User user = new User(username, password);
                    users.add(user);
                } catch (IllegalArgumentException e) {
                    System.err.println(line + " " + e.getMessage());
                }
            }

            Collections.sort(users);

            for (User user : users) {
                System.out.println(user);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Input file was not found.");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}