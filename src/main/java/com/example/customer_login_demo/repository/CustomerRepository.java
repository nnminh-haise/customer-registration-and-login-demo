package com.example.customer_login_demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.customer_login_demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @SuppressWarnings("null")
    @Query("SELECT c FROM Customer AS c WHERE c.deleteAt IS null")
    public List<Customer> findAll();

    @SuppressWarnings("null")
    @Query("SELECT c FROM Customer AS c WHERE c.deleteAt IS null AND c.id = :id")
    public Optional<Customer> findById(@Param("id") UUID id);

    @SuppressWarnings({ "null", "unchecked" })
    public Customer save(Customer customers);

    @Query("SELECT COUNT(c) FROM Customer AS c WHERE c.deleteAt IS null AND c.phonenumber = :phonenumber")
    public Integer countCustomerWithPhonenumber(@Param("phonenumber") String phonenumber);

    @Query("SELECT COUNT(c) FROM Customer AS c WHERE c.deleteAt IS null AND c.email = :email")
    public Integer countCustomerWithEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer AS c WHERE c.deleteAt IS null AND c.email = :email AND c.password = :password")
    public Optional<Customer> validateCustomer(@Param("email") String email, @Param("password") String password);
}
