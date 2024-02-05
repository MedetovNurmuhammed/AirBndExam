package java12.dao;

import java12.entities.Customer;
import java12.entities.Owner;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    //CRUD
    String saveCustomerOzu(Customer customer);
    String saveCustomerRentInto(Customer newCustomer, Long houseId, Long agencyId, LocalDate checkin,LocalDate checkout);
    List<Customer> getaAllCusromer();
    void DeleteCustomerById(Long id);
    String updateCustomerById(Long id,Customer newOwner);
    Optional<Customer> findByCustomerId(Long id);


    //Customer эки жол менен тузулсун.

    //Биринчисинде озу эле тузулот, экинчисинде customer тузулуп жатканда бир
    //уйду ижарага алып тузулот(rent_info).
    //Customer уйду ижарага алса болот. Ижарага алып жатканда customer id, house
    //id, agency id жана check in check out жазышы керек.
    //Customer-ди очуруп жатканда. Ижарасы жок Customer-лер очсун, эгерде
    //ижарасы бар болсо checkout датасын текшерсин. Учурдагы датадан мурун
    //болсо rent_info customer-менен чогу очуп кетсин.
}
