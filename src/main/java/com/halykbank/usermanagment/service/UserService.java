package com.halykbank.usermanagment.service;

import com.halykbank.usermanagment.domain.User;
import com.halykbank.usermanagment.dto.UserRequestDTO;
import com.halykbank.usermanagment.dto.UserResponseDTO;
import com.halykbank.usermanagment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User save = userRepository.save(user);
        return modelMapper.map(save, UserResponseDTO.class);
    }

    public Set<UserResponseDTO> findAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toSet());
    }

    public UserResponseDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public Set<UserResponseDTO> findUsersByName(String name) {
        return StreamSupport.stream(userRepository.findAllByNameIgnoreCase(name.trim()).spliterator(), false)
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toSet());
    }

    public UserResponseDTO findUsersByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        user.setName(userRequestDTO.getName());
        user.setSurname(userRequestDTO.getSurname());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setGender(userRequestDTO.getGender());
        user.setBirthDay(userRequestDTO.getBirthDay());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

}
