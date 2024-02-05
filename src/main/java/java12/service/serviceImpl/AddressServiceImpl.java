package java12.service.serviceImpl;

import java12.dao.AddressDao;
import java12.dao.daoImpl.AddressDaoImpl;
import java12.entities.Address;
import java12.entities.Agency;
import java12.service.AddressService;
import java12.service.AgencyService;

import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {
    AddressDao addressDao = new AddressDaoImpl();

    @Override
    public List<Address> getallAddress() {
        return addressDao.getallAddress();
    }

    @Override
    public Address findAddressById(Long id) {
        return addressDao.findAddressById(id);
    }

    @Override
    public String updateAddress(Long id, Address newAddress) {
        return addressDao.updateAddress(id,newAddress);
    }

    @Override
    public Map<Address,Agency> getallAgencyAddressById() {return addressDao.getallAgencyAddressById();

    }

    @Override
    public int countAgencynameCity(String Cityname) {
        return addressDao.countAgencynameCity(Cityname);
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return addressDao.groupByRegion();
    }
}
