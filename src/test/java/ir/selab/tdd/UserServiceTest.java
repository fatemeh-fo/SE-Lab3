package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import ir.selab.tdd.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        UserRepository userRepository = new UserRepository(List.of());
        userService = new UserService(userRepository);
        userService.registerUser("admin", "1234");
        userService.registerUser("ali", "qwert");
    }

    @Test
    public void createNewValidUser__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUser__ShouldFail() {
        String username = "ali";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertFalse(b);
    }

    @Test
    public void loginWithValidUsernameAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithUsername("admin", "1234");
        assertTrue(login);
    }

    @Test
    public void loginWithValidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("admin", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("ahmad", "abcd");
        assertFalse(login);
    }
    
    @Test
    public void getAllUsersInitiallyEmpty__ShouldReturnEmptyList() {
        UserRepository userRepository = new UserRepository(List.of());
        userService = new UserService(userRepository);

        assertEquals(List.of(), userService.getAllUsers());
    }
    
    @Test
    public void getAllUsersAfterRegister__ShouldReturnCorrectList() {
        final List<User> actualUsers = userService.getAllUsers();
        assertEquals(2, actualUsers.size());
        final User user1 = actualUsers.get(0);
        final User user2 = actualUsers.get(1);

        assertEquals("admin", user1.getUsername());
        assertEquals("1234", user1.getPassword());
        assertNull(user1.getEmail());

        assertEquals("ali", user2.getUsername());
        assertEquals("qwert", user2.getPassword());
        assertNull(user2.getEmail());
    }

    @Test
    public void createNewUserWithDupliteEmail__ShouldReturnFalse() {
        assertTrue(userService.registerUser("user1", "pass1", "user1@mail.com"));
        assertFalse(userService.registerUser("user2", "pass2", "user1@mail.com"));
    }

    @Test
    public void removeUserNotFound__ShouldReturnFalse() {
        assertFalse(userService.removeUser("user1"));
    }

    @Test
    public void removeUserValid__ShouldReturnTrue() {
        assertTrue(userService.removeUser("admin"));
    }

    @Test
    public void loginWithEmailNotFound__ShouldReturnFalse() {
        assertFalse(userService.loginWithEmail("user1@mail.com", "pass1"));
    }
}
