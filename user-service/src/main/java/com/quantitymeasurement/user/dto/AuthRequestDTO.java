package com.quantitymeasurement.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequestDTO {

	@NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50)
    @JsonProperty("username") // Ensures mapping from React 'username'
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @JsonProperty("email")    // Ensures mapping from React 'email'
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4)
    @JsonProperty("password") // Ensures mapping from React 'password'
    private String password;

    @JsonProperty("phoneNumber") // Ensures mapping from React 'phoneNumber'
    private String phoneNumber;
}