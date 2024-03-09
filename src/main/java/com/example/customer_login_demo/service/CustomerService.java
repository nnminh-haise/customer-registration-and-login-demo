package com.example.customer_login_demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.customer_login_demo.dto.CustomerDto;
import com.example.customer_login_demo.dto.LoginDto;
import com.example.customer_login_demo.dto.ResponseDto;
import com.example.customer_login_demo.model.Customer;
import com.example.customer_login_demo.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository CustomerRepository;

    public ResponseDto<List<Customer>> findAllCustomer() {
        ResponseDto<List<Customer>> res = new ResponseDto<>(null, "", HttpStatus.NOT_FOUND);

        try {
            List<Customer> Customer = CustomerRepository.findAll();
            return res
                    .setData(Customer)
                    .setMessage("Success!")
                    .setStatus(HttpStatus.OK);
        } catch (Exception e) {
            return res
                    .setMessage(e.getMessage())
                    .setStatus(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseDto<Customer> findById(UUID id) {
        ResponseDto<Customer> res = new ResponseDto<>(null, "", HttpStatus.BAD_REQUEST);

        if (id == null) {
            return res.setMessage("Invalid ID!");
        }

        try {
            Optional<Customer> customer = CustomerRepository.findById(id);
            if (!customer.isPresent()) {
                return res
                        .setMessage("Cannot find any customer with the given ID")
                        .setStatus(HttpStatus.NOT_FOUND);
            }

            return res
                    .setData(customer.get())
                    .setMessage("Success!")
                    .setStatus(HttpStatus.OK);
        } catch (Exception e) {
            return res
                    .setMessage(e.getMessage())
                    .setStatus(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseDto<Customer> createNewCustomer(CustomerDto dto) {
        ResponseDto<Customer> res = new ResponseDto<>(null, "", HttpStatus.BAD_REQUEST);

        ResponseDto<Customer> dtoValidation = CustomerDto.toModel(dto);
        if (!dtoValidation.getStatus().equals(HttpStatus.OK)) {
            return res
                    .setMessage(dtoValidation.getMessage())
                    .setStatus(dtoValidation.getStatus());
        }

        int buffer = CustomerRepository.countCustomerWithEmail(dto.getEmail());
        System.out.println("buffer: " + buffer);
        if (CustomerRepository.countCustomerWithEmail(dto.getEmail()) > 0) {
            return res.setMessage("The given email has been used!");
        }

        if (CustomerRepository.countCustomerWithPhonenumber(dto.getPhonenumber()) > 0) {
            return res.setMessage("The given phonenumber has been used!");
        }

        Customer newCustomer = dtoValidation.getData();
        newCustomer.setCreateAt(LocalDate.now());

        try {
            CustomerRepository.save(newCustomer);
            return res
                    .setData(newCustomer)
                    .setMessage("Success!")
                    .setStatus(HttpStatus.OK);
        } catch (Exception e) {
            return res
                    .setMessage(e.getMessage())
                    .setStatus(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseDto<Customer> authentication(LoginDto dto) {
        ResponseDto<Customer> res = new ResponseDto<>(null, "", HttpStatus.BAD_REQUEST);

        ResponseDto<String> loginDtoValidation = LoginDto.validate(dto);
        if (!loginDtoValidation.getStatus().equals(HttpStatus.OK)) {
            return res
                    .setMessage(loginDtoValidation.getMessage())
                    .setStatus(loginDtoValidation.getStatus());
        }

        Optional<Customer> customerValidation = CustomerRepository.validateCustomer(
                dto.getEmail(), dto.getPassword());
        if (!customerValidation.isPresent()) {
            return res
                    .setMessage("Authentication failed!")
                    .setStatus(HttpStatus.UNAUTHORIZED);
        }

        return res
                .setData(customerValidation.get())
                .setMessage("Authenticated!")
                .setStatus(HttpStatus.OK);
    }
}
