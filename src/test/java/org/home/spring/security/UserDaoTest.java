package org.home.spring.security;

import org.home.spring.security.common.User;
import org.home.spring.security.common.UserDao;
import org.home.spring.security.context.ApplicationContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class UserDaoTest {
    private static final String USER_NAME = "Bob";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Inject
    private UserDao userDao;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(USER_NAME);
    }

    @Test
    public void shouldAuthenticationCredentialsNotFoundExceptionBeThrownWhenTheUserHasNotProperRole() {
        expectedException.expect(AuthenticationCredentialsNotFoundException.class);

        userDao.addUser(user);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldUserBeSavedInDaoWhenTheUserHasNeededRole() {
        int sizeBeforeAddingUser = userDao.getUsers().size();

        userDao.addUser(user);

        assertThat(userDao.getUsers()).hasSize(sizeBeforeAddingUser + 1)
                                      .contains(user);
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldPreAuthorizationAndPostAuthorizationChecksBePassed() {
        User transformedUser = userDao.transformUser(user);

        assertThat(user).isNotEqualTo(transformedUser);
    }

    @Test
    public void shouldAuthenticationCredentialsNotFoundExceptionBeThrownWhenInputParameterDoesNotMatchExpression() {
        assertThatThrownBy(
                () -> userDao.transformUser(new User("Li"))
        ).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }
}
