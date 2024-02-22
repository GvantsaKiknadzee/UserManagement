package com.halykbank.usermanagment.controller;

import com.halykbank.usermanagment.dto.UserRequestDTO;
import com.halykbank.usermanagment.dto.UserResponseDTO;
import com.halykbank.usermanagment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@PreAuthorize("hasRole('MANAGER')")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Creates User", description = "To create user successfully name, surname, email and" +
            " password must not be null or blank also you should provide valid mail and password", responses = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid email or password, name, surname birthday " +
                         "or password is null or blank")})
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody  @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.saveUser(userRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieves all the users", description = "Gets all the users and retrieves his/her " +
            "name, surname and email",
            responses = {@ApiResponse(responseCode = "200", description = "Users retrieved ")})
    public ResponseEntity<Set<UserResponseDTO>> getAllUsers() {
        Set<UserResponseDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieves the user by id", responses = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "user with given id does not exist"
            )})
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/name")
    @Operation(summary = "Retrieves all users by name", responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")})
    public ResponseEntity<Set<UserResponseDTO>> getUsersByName(@RequestParam String name) {
        Set<UserResponseDTO> users = userService.findUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email")
    @Operation(summary = "Retrieves the user by email", responses = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "user with given email does not exist"
                    )})
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        UserResponseDTO user = userService.findUsersByEmail(email);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes the user by id", responses = {
            @ApiResponse(responseCode = "204", description = "User with the given ID has been successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User with given id does not exist"
                    )})
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates user by given id", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or user data"),
            @ApiResponse(responseCode = "404", description = "User with the given ID not found") })
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
