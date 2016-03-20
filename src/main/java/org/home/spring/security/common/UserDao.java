package org.home.spring.security.common;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Repository
public class UserDao {
    private final List<User> users;

    public UserDao() {
        users = new ArrayList<>();
    }

    @Secured("ROLE_USER")
    public void addUser(@Nonnull User user) {
        users.add(user);
    }

    @Nonnull
    public List<User> getUsers() {
        return unmodifiableList(users);
    }
}
