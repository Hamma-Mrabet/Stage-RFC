package tn.esprit.espritgather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveUser() {
        User user = new User();
        user.setIdUser(1L);
        user.setFirstName("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.retrieveUser(1L);

        assertEquals("John Doe", result.getFirstName());
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setFirstName("Jane Doe");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertEquals("Jane Doe", result.getFirstName());
    }

    @Test
    public void testModifyUser() {
        User user = new User();
        user.setFirstName("Jane Doe");
        user.setPassword("plainpassword");

        String hashedPassword = "hashedpassword";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(hashedPassword);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.modifyUser(user);

        assertEquals(hashedPassword, result.getPassword());
    }

    @Test
    public void testRetrieveUserByMail() {
        User user = new User();
        user.setMail("test@example.com");

        when(userRepository.findBymail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.retrieveUserByMail("test@example.com");

        assertEquals("test@example.com", result.getMail());
    }
}
