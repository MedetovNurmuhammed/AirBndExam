package java12;

import java12.config.HibernateConfig;
import java12.entities.*;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import java12.enums.HouseType;
import java12.service.*;
import java12.service.serviceImpl.*;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConfig.getSession();
        while (true) {
            System.out.println("""
                              AGENCY
                    1.Save
                    2.GetallAgency
                    3.FindByIDAgency
                    4.UpdateAgency
                    5.DeleteAgencyById
                              ADDRESS
                    6.GetAllAddress
                    7.FindAddressbyId
                    8.UpdateAddress
                    9.GetAlladdressAgency
                    10.CountAgencyByCityName
                    11.GroupByRegion
                             Customer
                    12.SaveCustomerOzu
                    13.GetAllCustomer
                    14.FindByCustomerId
                    15.UpdateCustomerById
                    16.DeleteCustomerById
                    17.SaveCustomerWithRent
                         Owner
                    18.AssignOwnerToAgency
                    19.SaveOwnerWithHouse
                    20. saveOwner
                    21.FindByOwnerId
                    22.GetAllOwner
                    23.DeleteOwnerById
                    24.UpdateOwnerById
                    25.getOwnersByAgencyId
                        House
                    26.SaveHouseWithOwner
                    27.FindHouseByID
                    28.FindAllHouse
                    29.UpdateHouseById
                    30.DeleteHouseById
                    31.GetHousesInRegion
                    32.AllHousesByAgencyId
                    33.AllHousesByOwnerId
                    34.HousesBetweenDates
                        RentInfo
                    35.RentInFoBetweenDate
                    36.HousesByAgencyIDAndDate
                    Выберите команду:
                    """);
            Scanner scannerString = new Scanner(System.in);
            Scanner scannerLong = new Scanner(System.in);
            String comand = scannerString.nextLine();
            AgencyService agencyService = new AgencyServiceImpl();
            AddressService addressService = new AddressServiceImpl();
            CustomerService customerService = new CustomerServiceImpl();
            OwnerService ownerService = new OwnerServiceImpl();
            HouseService houseService = new HouseServiceImpl();
            RentInfoService rentInfoService = new RentInfoServiceImpl();
            //TODO save
            //  System.out.println(agencyService.saveAgency(new Agency("Iprofi", "+996099878979", address1)));
            // System.out.println(agencyService.saveAgency(new Agency("GX", "+996099878979", address1)));
//        System.out.println(agencyService.saveAgency(new Agency("Aizix", "+996099878979", address1)))
            switch (comand) {
                case "1"->{
                    Address address1 = new Address("Бишкек","Джал","Токтоналиева");
                    System.out.println(agencyService.saveAgency(new Agency("Aizix", "+996099878979", address1)));
                }

                case "2" -> {
                    System.out.println(agencyService.getaAllAgency());
                }
                case "3" -> {
                    System.out.println("Введите id Agency: ");
                    Long id = scannerLong.nextLong();
                    System.out.println(agencyService.findByAgencyId(id));
                }
                case "4" -> {
                    System.out.println("Введите ID Agency которое хотите изменить:");
                    Long idAgency = scannerLong.nextLong();
                    Agency agency = new Agency();
                    System.out.println("Введите Новое имя:");
                    String name = scannerString.nextLine();
                    agency.setName(name);
                    System.out.println(agencyService.updateAgencyById(idAgency, agency));
                }
                case "5" -> {
                    System.out.println("Введите id которое хотите удалить:");
                    Long id = scannerLong.nextLong();
                    agencyService.DeleteAgencyById(id);
                }
                case "6" -> {
                    System.out.println(addressService.getallAddress());
                }
                case "7" -> {
                    System.out.println("Введите id Address: ");
                    Long id = scannerLong.nextLong();
                    System.out.println(addressService.findAddressById(id));
                }
                case "8" -> {
                    System.out.println("Введите id Address которое хотите изменить:");
                    Long id = scannerLong.nextLong();
                    System.out.println("Введите название города:");
                    String city = scannerString.nextLine();
                    System.out.println("Введите Region:");
                    String region = scannerString.nextLine();
                    System.out.println("Введите название улицы:");
                    String street = scannerString.nextLine();
                    Address address = new Address();
                    address.setCity(city);
                    address.setStreet(street);
                    address.setRegion(region);
                    System.out.println(addressService.updateAddress(id, address));

                }
                case "9"->{
                    System.out.println(addressService.getallAgencyAddressById());
                }
                case "10"->{
                    System.out.println("Введите Название города:");
                    String city = scannerString.nextLine();
                    System.out.println(addressService.countAgencynameCity(city));
                }
                case "11"->{
                    System.out.println(addressService.groupByRegion());
                }
                case "12"->{
                    Customer customer = new Customer("Искак","Turdumambetov","I@gmail.com", Gender.MALE,"Kyrgyz", FamilyStatus.HOLOST);
                    System.out.println(customerService.saveCustomerOzu(customer));
                }
                case "13"->{
                    System.out.println(customerService.getaAllCusromer());
                }
                case "14"->{
                    System.out.println("Введите id Customer:");
                    Long id = scannerLong.nextLong();
                    System.out.println(customerService.findByCustomerId(id));
                }
                case "15"->{
                    System.out.println("Введите ID customer:");
                    Long id = scannerLong.nextLong();
                    System.out.println("Введите новое имя  для Customer :");
                    String name = scannerString.nextLine();
                    Customer customer = new Customer();
                    customer.setFirstName(name);
                    System.out.println(customerService.updateCustomerById(id, customer));
                }
                case "16"->{
                    System.out.println("Введите ID customer которое хотите удалить:");
                    Long id = scannerLong.nextLong();
                    customerService.DeleteCustomerById(id);
                }
                case "17"->{
                    System.out.println(customerService.saveCustomerRentInto(new Customer("Нурмухаммед", "Медетов", "n@gmail.com", Gender.MALE, "Kyrgyz", FamilyStatus.HOLOST), 1L, 1L, LocalDate.of(2024, 5, 25), LocalDate.of(2024, 6, 2)));
                }
                case "18"->{
                    ownerService.assignOwnerToAgency(1L,1L);
                }
                case "19"->{
                    ownerService.saveOwner(new Owner("Нурпери","Medetova","nn@gmail.com",LocalDate.of(2001,12,27),Gender.FEMALE),new House(HouseType.BUSINNESS,BigDecimal.valueOf(2500.1),4.6,"Зынк",3,true));
                }
                case "20"->{
                    Owner owner = new Owner("Kamchybek","Turdakunov","1@gmail.com",LocalDate.of(2000,11,11),Gender.MALE);
                    System.out.println(ownerService.saveOwner(owner));
//
                }
                case "21"->{
                    System.out.println("Введите ID Owner:");
                    Long id = scannerLong.nextLong();
                    System.out.println(ownerService.findOwnerById(id));
                }
                case "22"->{
                    System.out.println(ownerService.getaAllOwner());

                }
                case "23"->{
                    System.out.println("Введите ID:");
                    Long id  = scannerLong.nextLong();
                    System.out.println(ownerService.deleteOwnerById(id));
                }
                case "24"->{
                    System.out.println("Введите Id Owner:");
                    Long id = scannerLong.nextLong();
                    Owner owner = new Owner();
                    System.out.println("Введите новое имя Owner:");
                    String name = scannerString.nextLine();
                    owner.setFirstName(name);
                    System.out.println(ownerService.updateOwnerById(id, owner));
                }
                case "25"->{
                    System.out.println("Введите id Agency:");
                    Long id =  scannerLong.nextLong();
                    System.out.println(ownerService.getOwnersByAgencyId(id));
                }
                case "26"->{
                    System.out.println("Введите ID Owner:");
                    Long id = scannerLong.nextLong();
                    System.out.println(houseService.saveHouse(id, new House(HouseType.BUSINNESS, BigDecimal.valueOf(25000), 4.5, "Lux", 5, true)));
                }
                case "27"->{
                    System.out.println("Введите ID:");
                    Long id = scannerLong.nextLong();
                    System.out.println(houseService.findHouseById(id));
                }
                case "28"->{
                    System.out.println(houseService.findAllHouse());
                }
                case "29"->{
                    System.out.println("Введите ID House:");
                    Long id = scannerLong.nextLong();
                    System.out.println(houseService.updateHouseById(id, new House(HouseType.LUX, BigDecimal.valueOf(15000), 3.4, "ORTO", 2, false)));
                }
                case "30"->{
                    System.out.println("Введите ID которое хотите удалить:");
                    long Id = scannerLong.nextLong();
                    System.out.println(houseService.deleteHouseById(Id));
                }
                case "31"->{
                    System.out.println("Введите Region:");
                    String region  = scannerString.nextLine();
                    System.out.println(houseService.getHousesInRegion(region));
                }
                case "32"->{
                    System.out.println("Введите ID Agency:");
                    Long id = scannerLong.nextLong();
                    System.out.println(houseService.allHousesByAgencyId(id));
                }
                case "33"->{
                    System.out.println("Введите ID Owner:");
                    long id = scannerLong.nextLong();
                    System.out.println(houseService.allHousesByOwnerId(id));
                }
                case "34"->{
                    System.out.println(houseService.housesBetweenDates(LocalDate.of(2000, 11, 11), LocalDate.of(2024, 12, 12)));
                }
                case "35"->{
                    System.out.println(rentInfoService.rentInfoBetweenDates(LocalDate.of(2018, 12, 12), LocalDate.of(2023, 12, 12)));
                }case "36"->{
                    System.out.println("Введите Id Agency:");
                    Long id = scannerLong.nextLong();
                    System.out.println(rentInfoService.housesByAgencyIdAndDate(id));

                }


            }
        }
    }
}
