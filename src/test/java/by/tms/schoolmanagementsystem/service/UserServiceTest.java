package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.UnconfirmedUser;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UnconfirmedUserRepository unconfirmedUserRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private MarkRepository markRepository;
    @Mock
    private LessonRepository lessonRepository;
    private UserService userService;


    @BeforeEach
    void beforeEach(){
        this.userService = new UserService(userRepository, unconfirmedUserRepository, roleRepository, markRepository, lessonRepository);
    }

    @Test
    @DisplayName("Finding user by id")
    void findById() {
        User user = new User(100L, null, null, null, null, null);
        Mockito.when(userRepository.findById(100L)).thenReturn(Optional.of(user));
        Optional<User> byId = userRepository.findById(100L);
        if (byId.isEmpty()) {
            fail("User is null");
        }
        assertEquals(user, byId.get());
    }

    @Test
    @DisplayName("Finding user by name")
    void findByUsername() {
        User user = new User(100L, null, "Test", null, null, null);

        Mockito.when(userRepository.getByUsername(eq("Test"))).thenReturn(Optional.of(user));
        if (userService.findByUsername("Test").isEmpty()) {
            fail("user is null");
        }
        User testUser = userService.findByUsername("Test").get();
        assertEquals(user, testUser);

        Mockito.when(userRepository.getByUsername(anyString())).thenReturn(Optional.empty());
        Optional<User> testUser2 = userService.findByUsername("test");
        assertTrue(testUser2.isEmpty());
    }

    @Test
    @DisplayName("User exists by username")
    void existsByUsername() {
        User user = new User(100L, null, "Test", null, null, null);
        Mockito.when(userRepository.getByUsername("Test")).thenReturn(Optional.of(user));
        Optional<User> test = userService.findByUsername("Test");
        if(test.isEmpty()){
            fail("User is null");
        }
        assertEquals(user, test.get());

        Mockito.when(userRepository.getByUsername(anyString())).thenReturn(Optional.empty());
        Optional<User> test1 = userService.findByUsername("test");
        assertTrue(test1.isEmpty());
    }


    @Test
    @DisplayName("Confirm user")
    void confirm() {
        User user = new User(100L, null, "Test", null, null, null);
        UnconfirmedUser unconfirmedUser = new UnconfirmedUser(user);
        Mockito.when(unconfirmedUserRepository.getByUser(user)).thenReturn(unconfirmedUser);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService.confirm(user);

        Mockito.when(unconfirmedUserRepository.getByUser(user)).thenReturn(null);
        assertNull(unconfirmedUserRepository.getByUser(user));
    }

    @Test
    void setRole() {
        User user = new User(100L, null, "Test", null, null, new UserRole(Role.Student));
        Mockito.when(userRepository.getById(100L)).thenReturn(user);
        Mockito.when(roleRepository.getByRole(Role.Admin)).thenReturn(new UserRole(Role.Admin));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.setRole(100L, Role.Admin);
        assertEquals(Role.Admin, user.getRole());
    }

}