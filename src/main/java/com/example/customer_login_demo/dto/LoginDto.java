package com.example.customer_login_demo.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String email;

    private String password;

    public static ResponseDto<String> validate(LoginDto dto) {
        ResponseDto<String> res = new ResponseDto<>(null, "", HttpStatus.BAD_REQUEST);

        if (dto == null) {
            return res.setMessage("Invalid DTO!");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank() || dto.getEmail().isEmpty()
                || !dto.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            return res.setMessage("Invalid email!");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank() || dto.getPassword().isEmpty()) {
            return res.setMessage("Invalid email!");
        }

        return res
                .setMessage("Valid!")
                .setStatus(HttpStatus.OK);
    }
}
