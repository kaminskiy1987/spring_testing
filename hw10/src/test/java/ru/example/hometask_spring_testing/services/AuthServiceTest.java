package ru.example.hometask_spring_testing.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.example.hometask_spring_testing.entities.Session;
import ru.example.hometask_spring_testing.entities.User;
import ru.example.hometask_spring_testing.repositories.SessionRepository;
import ru.example.hometask_spring_testing.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionRepository sessionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister(){
        User newUser = new User("usr1", "pwd1");

        authService.register(newUser);

        Mockito.verify(userRepository, Mockito.atMost(1)).save(newUser);
    }

    @Test
    public void testLogin(){
        User newUser = new User("usr2", "pwd2");

        authService.register(newUser);

        newUser = userRepository.findUserByName(newUser.getName());

        Session newSession = authService.login("usr2", "pwd2");

        Mockito.verify(sessionRepository, Mockito.atMost(1)).save(newSession);
    }

    @Test
    public void testLogout(){

        User newUser = new User("usr3", "pwd3");

        authService.register(newUser);

        Session newSession = authService.login("usr3", "pwd3");

        authService.logout(newUser.getId());

        Mockito.verify(sessionRepository, Mockito.atMost(1)).delete(newSession);
    }
}