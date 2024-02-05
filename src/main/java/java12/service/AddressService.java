package java12.service;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Address> getallAddress();
    Address findAddressById(Long id);
    String updateAddress(Long id,Address newAddress);
    Map<Address,Agency> getallAgencyAddressById();
    int countAgencynameCity(String Cityname);
    //Бир метод ар бир адрести агентсвосу менен чыгарсын.
    //Колдонуучу бир шаардын атын жазса ошол шаарда канча агентсво бар экенин
    //эсептеп чыгарсын
    //Ар бир регион жана ошол региондун агентсволары баары чыксын
    Map<String, List<Agency>> groupByRegion();
}
