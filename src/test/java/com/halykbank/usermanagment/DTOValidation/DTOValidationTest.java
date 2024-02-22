package com.halykbank.usermanagment.DTOValidation;

import com.halykbank.usermanagment.domain.Gender;
import com.halykbank.usermanagment.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DTOValidationTest {

    private UserRequestDTO userRequestDTO;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidateUserRequestDTO_CorrectValues() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmail.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);
        assertTrue(violations.isEmpty());

    }

    @Test
    public void testValidateUserRequestDTO_BlankName() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmial.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    public void testValidateUserRequestDTO_BlankSurname() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmial.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("surname")));
    }

    @Test
    public void testValidateUserRequestDTO_InvalidEmail() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Provide valid email address")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testValidateUserRequestDTO_BlankEmail() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testValidateUserRequestDTO_InvalidPassword() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmail.com");
        userRequestDTO.setPassword("pass");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage()
                .contains("Provide password that contains at least one uppercase letter and one number")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    public void testValidateUserRequestDTO_BlankPassword() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmail.com");
        userRequestDTO.setPassword("");
        userRequestDTO.setBirthDay(LocalDate.of(2011, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage()
                .contains("must not be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    public void testValidateUserRequestDTO_InvalidBirthDate() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmail.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(LocalDate.of(2030, 11, 11));

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage()
                .contains("Birthday must be in past")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDay")));
    }

    @Test
    public void testValidateUserRequestDTO_NullBirthDate() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Anna");
        userRequestDTO.setSurname("Smith");
        userRequestDTO.setGender(Gender.FEMALE);
        userRequestDTO.setEmail("smith@gmail.com");
        userRequestDTO.setPassword("Password123");
        userRequestDTO.setBirthDay(null);

        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(userRequestDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage()
                .contains("Please provide your BirthDate")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDay")));
    }
}
