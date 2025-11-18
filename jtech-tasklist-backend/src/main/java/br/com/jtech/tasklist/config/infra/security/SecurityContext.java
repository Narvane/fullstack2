package br.com.jtech.tasklist.config.infra.security;

import br.com.jtech.tasklist.application.core.domains.User;

import java.util.UUID;

public class SecurityContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static UUID getCurrentUserId() {
        User user = currentUser.get();
        return user != null ? user.getId() : null;
    }

    public static void clear() {
        currentUser.remove();
    }

}

