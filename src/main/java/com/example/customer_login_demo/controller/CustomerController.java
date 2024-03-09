package com.example.customer_login_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.customer_login_demo.dto.CustomerDto;
import com.example.customer_login_demo.dto.LoginDto;
import com.example.customer_login_demo.dto.ResponseDto;
import com.example.customer_login_demo.model.Customer;
import com.example.customer_login_demo.service.CustomerService;

@Controller
@RequestMapping("")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @PostMapping("login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            ModelMap model) {
        ResponseDto<Customer> res = customerService.authentication(new LoginDto(email, password));
        if (!res.getStatus().equals(HttpStatus.OK)) {
            return "index";
        }
        Customer customer = res.getData();
        model.addAttribute("Customer", customer);
        return "success";
    }

    @RequestMapping("register")
    public String routeRegister() {
        return "register";
    }

    @PostMapping("register")
    public String processRegistration(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phonenumber") String phonenumber,
            @RequestParam("dob") String dob,
            ModelMap model) {
        CustomerDto dto = new CustomerDto(email, password, firstName, lastName, phonenumber, dob);
        ResponseDto<Customer> res = customerService.createNewCustomer(dto);
        if (!res.getStatus().equals(HttpStatus.OK)) {
            return "index";
        }

        model.addAttribute("Customer", res.getData());
        return "success";
    }

}
