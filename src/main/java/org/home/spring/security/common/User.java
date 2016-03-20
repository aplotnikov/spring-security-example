package org.home.spring.security.common;

import javax.annotation.Nonnull;

public class User {
    public final String name;

    public User(@Nonnull String name) {
        this.name = name;
    }
}
