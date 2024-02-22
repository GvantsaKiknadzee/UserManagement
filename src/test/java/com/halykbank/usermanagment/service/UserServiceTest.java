package com.halykbank.usermanagment.service;

import com.halykbank.usermanagment.domain.Gender;
import com.halykbank.usermanagment.domain.User;
import com.halykbank.usermanagment.dto.UserRequestDTO;
import com.halykbank.usermanagment.dto.UserResponseDTO;
import com.halykbank.usermanagment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private User user1;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void init() {
        user1 = new User();
        user1.setId(1L);
        user1.setName("Gvantsa");
        user1.setSurname("Kiknadze");
        user1.setGender(Gender.FEMALE);
        user1.setEmail("gvantsa@gmail.com");
        user1.setPassword("Password123");
        user1.setBirthDay(LocalDate.of(2011,11,11));

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Gvantsa");
        userRequestDTO.setSurname("Kiknadze");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("gvantsa@gmail.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2011,11,11));

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName("Gvantsa");
        userResponseDTO.setSurname("Kiknadze");
        userResponseDTO.setGender(Gender.FEMALE);
        userResponseDTO.setEmail("gvantsa@gmail.com");
        userResponseDTO.setBirthDay(LocalDate.of(2011,11,11));

    }
    @Test
    public void testSaveUser() {
        when(modelMapper.map(userRequestDTO,User.class)).thenReturn(user1);
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(user1)).thenReturn(user1);
        when(modelMapper.map(user1,UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO actualResponseDTO = userService.saveUser(userRequestDTO);

        assertNotNull(actualResponseDTO);
        assertEquals(userResponseDTO.getSurname(), actualResponseDTO.getSurname());
        assertEquals(userResponseDTO.getName(), actualResponseDTO.getName());
        assertEquals(userResponseDTO.getEmail(), actualResponseDTO.getEmail());

        verify(modelMapper, times(1)).map(userRequestDTO, User.class);
        verify(passwordEncoder, times(1)).encode(userRequestDTO.getPassword());
        verify(modelMapper, times(1)).map(user1, UserResponseDTO.class);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void testFindAllUser() {
        User user2 = new User();
        user2.setName("Bob");
        user2.setSurname("Johnson");
        user2.setGender(Gender.MALE);
        user2.setEmail("bob@gmail.com");
        user2.setPassword("Password456");
        user2.setBirthDay(LocalDate.of(1985, 9, 22));

        User user3 = new User();
        user3.setName("Alice");
        user3.setSurname("Smith");
        user3.setGender(Gender.FEMALE);
        user3.setEmail("alice@gmail.com");
        user3.setPassword("Password123");
        user3.setBirthDay(LocalDate.of(1990, 5, 15));

        UserResponseDTO userResponseDTO2 = new UserResponseDTO();
        userResponseDTO2.setName(user2.getName());
        userResponseDTO2.setSurname(user2.getSurname());
        userResponseDTO2.setEmail(user2.getEmail());

        UserResponseDTO userResponseDTO3 = new UserResponseDTO();
        userResponseDTO3.setName(user3.getName());
        userResponseDTO3.setSurname(user3.getSurname());
        userResponseDTO3.setEmail(user3.getEmail());

        Set<User> users = new HashSet<>(Set.of(user1,user2,user3));

        when(userRepository.findAll()).thenReturn(users);
        when(modelMapper.map(user1, UserResponseDTO.class)).thenReturn(userResponseDTO);
        when(modelMapper.map(user2, UserResponseDTO.class)).thenReturn(userResponseDTO2);
        when(modelMapper.map(user3, UserResponseDTO.class)).thenReturn(userResponseDTO3);

        Set<UserResponseDTO> actualResponse = userService.findAllUsers();

        assertEquals(3, actualResponse.size());
        assertNotNull(actualResponse);

        Set<String> actualEmails = actualResponse.stream()
                .map(UserResponseDTO::getEmail)
                .collect(Collectors.toSet());

        assertTrue(actualEmails.contains("bob@gmail.com"));
        assertTrue(actualEmails.contains("alice@gmail.com"));
        assertTrue(actualEmails.contains("gvantsa@gmail.com"));

        assertTrue(actualResponse.stream().anyMatch(dto -> dto.getName().equals("Bob")));
        assertTrue(actualResponse.stream().anyMatch(dto -> dto.getName().equals("Alice")));
        assertTrue(actualResponse.stream().anyMatch(dto -> dto.getName().equals("Gvantsa")));

        verify(userRepository, times(1)).findAll();


        verify(modelMapper, times(1)).map(user1, UserResponseDTO.class);
        verify(modelMapper, times(1)).map(user2, UserResponseDTO.class);
        verify(modelMapper, times(1)).map(user3, UserResponseDTO.class);


        assertTrue(actualResponse.contains(userResponseDTO));
        assertTrue(actualResponse.contains(userResponseDTO2));
        assertTrue(actualResponse.contains(userResponseDTO3));
    }


    @Test
    public void testFindUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user1));
        when(modelMapper.map(user1, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO actualResponse = userService.findUserById(user1.getId());

        assertNotNull(actualResponse);
        assertEquals(userResponseDTO, actualResponse);
        assertEquals(user1.getName(), actualResponse.getName());
        assertEquals(user1.getSurname(), actualResponse.getSurname());
        assertEquals(user1.getEmail(), actualResponse.getEmail());

        verify(userRepository, times(1)).findById(user1.getId());
    }


    @Test
    void testFindUserById_InvalidId() {
        Long invalidUserId = 999L;
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findUserById(invalidUserId);
        });

        assertEquals("User with id " + invalidUserId + " not found", exception.getMessage());
        verify(userRepository, times(1)).findById(eq(invalidUserId));
    }

    @Test
    void testFindUsersByName() {
        when(userRepository.findAllByNameIgnoreCase(any(String.class))).thenReturn(Set.of(user1));
        when(modelMapper.map(user1, UserResponseDTO.class)).thenReturn(userResponseDTO);

        Set<UserResponseDTO> actualResponseDTOs = userService.findUsersByName("Gvantsa");

        assertEquals(actualResponseDTOs.size(),1);
        assertTrue(actualResponseDTOs.stream().anyMatch(response -> response.getName().equals("Gvantsa")));
    }

    @Test
    void testFindUserByEmail() {
        when(userRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.of(user1));
        when(modelMapper.map(user1,UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO actualResponse = userService.findUsersByEmail("gvantsa@gmail.com");

        assertNotNull(actualResponse);
        assertEquals(userResponseDTO, actualResponse);
        assertEquals(user1.getName(), actualResponse.getName());
        assertEquals(user1.getSurname(), actualResponse.getSurname());
        assertEquals(user1.getEmail(), actualResponse.getEmail());
    }

    @Test
    void testUserByEmail_NotExistedEmail() {
        String invalidEmail = "nonexistent@example.com";

        when(userRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findUsersByEmail(invalidEmail);
        });

        assertEquals("User with email " + invalidEmail + " not found", exception.getMessage());

        verify(userRepository, times(1)).findByEmailIgnoreCase(eq(invalidEmail));
    }

    @Test
    public void testDeleteUserById() {
        Long userId = 1L;

        when(userRepository.existsById(eq(userId))).thenReturn(true);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(eq(userId));
    }

    @Test
    public void testDeleteUserById_UserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.existsById(eq(userId))).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUserById(userId);
        });
        assertEquals("User with id " + userId + " not found", exception.getMessage());

        verify(userRepository, never()).deleteById(any());
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(eq(user1.getId()))).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(modelMapper.map(user1,UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO updatedUserDTO = userService.updateUser(user1.getId(), userRequestDTO);

        verify(userRepository, times(1)).findById(eq(user1.getId()));
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(userRequestDTO.getName(), updatedUserDTO.getName());
        assertEquals(userRequestDTO.getSurname(), updatedUserDTO.getSurname());
        assertEquals(userRequestDTO.getEmail(), updatedUserDTO.getEmail());
        assertEquals(userRequestDTO.getGender(), updatedUserDTO.getGender());
        assertEquals(userRequestDTO.getBirthDay(), updatedUserDTO.getBirthDay());
    }

    @Test
    public void testUpdateUser_UserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(userId, userRequestDTO);
        });

        assertEquals("User with id " + userId + " not found", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }


}
