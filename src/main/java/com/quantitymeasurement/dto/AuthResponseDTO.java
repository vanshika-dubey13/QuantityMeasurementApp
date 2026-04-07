package com.quantitymeasurement.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * =========================================================
 * AuthResponseDTO
 * =========================================================
 *
 * Purpose:
 * This class represents the response sent back to the client
 * after successful authentication (login).
 *
 * It contains:
 * - JWT token (used for accessing secured APIs)
 *
 * Flow:
 * Controller -> creates this DTO -> returned as JSON to client
 *
 * Example JSON Response:
 * {
 *   "token": "eyJhbGciOiJIUzI1NiJ9..."
 * }
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponseDTO {

    /*
     * JWT Token
     *
     * This token is generated after successful login.
     * Client must send this token in Authorization header
     * for accessing secured APIs.
     */
    private String token;
}
