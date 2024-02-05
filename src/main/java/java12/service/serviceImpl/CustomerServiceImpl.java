package java12.service.serviceImpl;

import java12.dao.CustomerDao;
import java12.dao.daoImpl.CustomerDaoImpl;
import java12.entities.Customer;
import java12.entities.Owner;
import java12.entities.RentInfo;
import java12.service.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = new CustomerDaoImpl();
    @Override
    public String saveCustomerOzu(Customer customer) {
        return customerDao.saveCustomerOzu(customer);

    }




    @Override
    public List<Customer> getaAllCusromer() {
        return customerDao.getaAllCusromer();
    }

    @Override
    public void DeleteCustomerById(Long id) {
        customerDao.DeleteCustomerById(id);

    }

    @Override
    public String updateCustomerById(Long id, Customer newOwner) {
        return customerDao.updateCustomerById(id,newOwner);
    }

    @Override
    public Optional <Customer> findByCustomerId(Long id) {
        return customerDao.findByCustomerId(id);
    }

    @Override
    public String saveCustomerRentInto(Customer newCustomer, Long houseId, Long agencyId, LocalDate checkin, LocalDate checkout) {
        return customerDao.saveCustomerRentInto(newCustomer,houseId,agencyId,checkin,checkout);
    }
}
