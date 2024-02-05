package java12.service;

import java12.entities.Customer;
import java12.entities.Owner;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    String saveCustomerOzu(Customer customer);

    List<Customer> getaAllCusromer();
    void DeleteCustomerById(Long id);
    String updateCustomerById(Long id, Customer newOwner);
    Optional<Customer> findByCustomerId(Long id);
    String saveCustomerRentInto(Customer newCustomer, Long houseId, Long agencyId, LocalDate checkin, LocalDate checkout);
}
