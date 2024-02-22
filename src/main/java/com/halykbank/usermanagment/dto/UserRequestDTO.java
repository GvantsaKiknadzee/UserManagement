package com.halykbank.usermanagment.dto;

import com.halykbank.usermanagment.domain.Gender;
import com.halykbank.usermanagment.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "User Request", description = "information about user while sending request")
public class UserRequestDTO {

    @NotBlank
    @Schema(description = "User's name that must not be blank", example = "Ann")
    private String name;

    @NotBlank
    @Schema(description = "User's surname that must not be blank", example= "Smith")
    private String surname;

    @NotBlank
    @Email(message = "Provide valid email address")
    @Schema(description = "User's email that must be a valid email address and must be unique",
            example = "ann@gmail.com")
    private String email;

    @NotBlank
    @ValidPassword(message = "Provide password that contains at least one uppercase letter and one number and is" +
            " at least 8 characters long ")
    @Schema(description = "User's password that must contain at least one numeric digit" +
            "It must contain at least one uppercase alphabetical character" +
            "It must be at least 8 characters in length.", example = "Password123")
    private String password;

    @Past(message = "Birthday must be in past")
    @NotNull(message = "Please provide your BirthDate")
    @Schema(description = "User's birthday that should not be in the past", example = "2001-01-12")
    private LocalDate birthDay;

    @Schema(description = "User's gender: male,female or not specified", example = "FEMALE")
    private Gender gender;
}
