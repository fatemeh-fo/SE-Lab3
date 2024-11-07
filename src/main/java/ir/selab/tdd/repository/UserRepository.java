package ir.selab.tdd.repository;

import ir.selab.tdd.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    private final Map<String, User> usersByUserName;
    private final Map<String, User> usersByEmail;

    public UserRepository(List<User> users) {
        this.usersByUserName = users
                                .stream()
                                .collect(Collectors.toMap(User::getUsername, u -> u, (u1, u2) -> {
            throw new IllegalArgumentException("Two users can not have the same username");
        }));

        this.usersByEmail = users
                                .stream()
                                .filter((u) -> u.getEmail() != null)
                                .collect(Collectors.toMap(User::getEmail, u -> u, (u1, u2) -> {
            throw new IllegalArgumentException("Two users can not have the same email");
        }));
    }

    public User getUserByUsername(String username) {
        return usersByUserName.get(username);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public boolean addUser(User user) {
        if (usersByUserName.containsKey(user.getUsername())) {
            return false;
        }
        if (usersByEmail.containsKey(user.getEmail())) {
            return false;
        }
        usersByUserName.put(user.getUsername(), user);
        if (user.getEmail() != null) {
            usersByEmail.put(user.getEmail(), user);
        }
        return true;
    }

    public boolean removeUser(String username) {
        if (getUserByUsername(username) == null) 
            return false;
        usersByUserName.remove(username);
        return true;
    }

    public int getUserCount() {
        return usersByUserName.size();
    }

    public List<User> getAllUsers() {
        return List.copyOf(usersByUserName.values());
    }

    public boolean changeUserEmail(String username, String newEmail) {
        User user = getUserByUsername(username);
        if (user == null) return false;
        if (getUserByEmail(newEmail) != null) return false;
        usersByEmail.remove(user.getEmail());
        usersByEmail.put(newEmail, user);
        return getUserByEmail(newEmail) != null;
    }
}
