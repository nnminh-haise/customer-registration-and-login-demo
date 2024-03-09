package com.example.customer_login_demo.dto;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import com.example.customer_login_demo.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phonenumber;

    private String dob;

    public static ResponseDto<Customer> toModel(CustomerDto dto) {
        ResponseDto<Customer> res = new ResponseDto<>(null, "", HttpStatus.BAD_REQUEST);

        Customer customers = new Customer();

        if (dto == null) {
            return res.setMessage("Invalid DTO!");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank() || dto.getEmail().isEmpty()
                || !dto.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            return res.setMessage("Invalid email!");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank() || dto.getPassword().isEmpty()) {
            return res.setMessage("Invalid password!");
        }

        if (dto.getDob() == null || dto.getDob().isEmpty() || dto.getDob().isBlank()) {
            return res.setMessage("Invalid date of birth!");
        }

        try {
            LocalDate validDob = LocalDate.parse(dto.getDob());
            customers.setDob(validDob);
        } catch (Exception e) {
            return res.setMessage(e.getMessage());
        }

        if (dto.getFirstName() == null || dto.getFirstName().isEmpty() || dto.getFirstName().isBlank()) {
            return res.setMessage("Invalid first name!");
        }

        if (dto.getLastName() == null || dto.getLastName().isEmpty() || dto.getLastName().isBlank()) {
            return res.setMessage("Invalid last name!");
        }

        if (dto.getPhonenumber() == null || dto.getPhonenumber().isEmpty() || dto.getPhonenumber().isBlank()) {
            return res.setMessage("Invalid phonenumber!");
        }

        customers.setEmail(dto.getEmail());
        customers.setPassword(dto.getPassword());
        customers.setFirstName(dto.getFirstName());
        customers.setLastName(dto.getLastName());
        customers.setPhonenumber(dto.getPhonenumber());

        return res
                .setData(customers)
                .setMessage("Valid DTO!")
                .setStatus(HttpStatus.OK);
    }
}
