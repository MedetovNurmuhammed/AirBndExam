package java12.dao;

import java12.entities.House;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HouseDao {
    //CRUD
    //House-тузулуп жатканда бир owner-ге тиешелуу болуп тузулсун
    //House-ту очуруп жатканда адреси чогу очсун . Бирок ижарасы жок болсо очсун,
    //эгерде ижарасы бар болсо checkout датасын текшерсин. Учурдагы датадан
    //мурун болсо rent_info менен чогу очуп кетсин.
    //Бир региондогу бардык уйлор чыксын
    //Бир агентсвога тиешелуу бардык уйлор чыксын
    //Бир owner-ге тиешелуу бардык уйлор чыксын
    //Эки датанын ортосундагы checkiIn болгон бардык уйлор чыксын
    String saveHouse(Long ownerId, House newHouse);
    Optional<House> findHouseById(Long houseId);
    List<House> findAllHouse();
    String updateHouseById(Long houseId, House newHouse);
    String deleteHouseById(Long houseId);
    List<House> getHousesInRegion(String region);
    List<House> allHousesByAgencyId(Long agencyId);
    List<House> allHousesByOwnerId(Long ownerId);
    List<House> housesBetweenDates(LocalDate fromDate, LocalDate toDate);
}
