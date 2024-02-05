package java12.service.serviceImpl;

import java12.dao.HouseDao;
import java12.dao.daoImpl.HouseDaoImpl;
import java12.entities.House;
import java12.service.HouseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class HouseServiceImpl implements HouseService {
    HouseDao houseDao = new HouseDaoImpl();
    @Override
    public String saveHouse(Long ownerId, House newHouse) {
        return houseDao.saveHouse(ownerId,newHouse);
    }

    @Override
    public Optional<House> findHouseById(Long houseId) {
        return houseDao.findHouseById(houseId);
    }

    @Override
    public List<House> findAllHouse() {
       return houseDao.findAllHouse();
    }

    @Override
    public String updateHouseById(Long houseId, House newHouse) {
        return houseDao.updateHouseById(houseId,newHouse);
    }

    @Override
    public String deleteHouseById(Long houseId) {
        return houseDao.deleteHouseById(houseId);
    }

    @Override
    public List<House> getHousesInRegion(String region) {
        return houseDao.getHousesInRegion(region);
    }

    @Override
    public List<House> allHousesByAgencyId(Long agencyId) {
        return houseDao.allHousesByAgencyId(agencyId);
    }

    @Override
    public List<House> allHousesByOwnerId(Long ownerId) {
        return houseDao.allHousesByOwnerId(ownerId);
    }

    @Override
    public List<House> housesBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return houseDao.housesBetweenDates(fromDate,toDate);
    }
}
