package com.halykbank.usermanagment.dto;

import com.halykbank.usermanagment.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "User Response", description = "information about user while getting response")
public class UserResponseDTO {

    @Schema(description = "User's ID")
    private Long id;

    @Schema(description = "User's name that must not be blank")
    private String name;

    @Schema(description = "User's surname that must not be blank")
    private String surname;

    @Schema(description = "User's email that must be a valid email address and must be unique")
    private String email;

    @Schema(description = "User's birthday that should not be in the past")
    private LocalDate birthDay;

    @Schema(description = "User's gender: male,female or not specified")
    private Gender gender;
}
