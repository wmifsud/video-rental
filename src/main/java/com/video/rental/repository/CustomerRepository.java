package com.video.rental.repository;

import com.video.rental.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by waylon on 06/04/2017.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByIdCard(String idCard);
}
