package org.home.spring.security.common;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("#user.name.length() < 4")
    @PostAuthorize("returnObject.name.length() > 3")
    public User transformUser(@Nonnull User user) {
        return new User(user.name + "100");
    }

    @Nonnull
    public List<User> getUsers() {
        return unmodifiableList(users);
    }
}
