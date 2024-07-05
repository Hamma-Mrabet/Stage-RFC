package tn.esprit.espritgather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

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

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

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
    public void testRetrieveUserByMail() {
        User user = new User();
        user.setMail("test@example.com");

        when(userRepository.findBymail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.retrieveUserByMail("test@example.com");

        assertEquals("test@example.com", result.getMail());
    }
}
