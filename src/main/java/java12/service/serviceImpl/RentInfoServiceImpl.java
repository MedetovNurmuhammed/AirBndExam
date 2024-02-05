package java12.service.serviceImpl;

import java12.dao.RentInfoDao;
import java12.dao.daoImpl.RentInfoDaoImpl;
import java12.entities.RentInfo;
import java12.service.RentInfoService;

import java.time.LocalDate;
import java.util.List;

public class RentInfoServiceImpl implements RentInfoService {
    RentInfoDao rentInfoDao = new RentInfoDaoImpl();

    @Override
    public List<RentInfo> rentInfoBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return rentInfoDao.rentInfoBetweenDates(fromDate,toDate);
    }

    @Override
    public Long housesByAgencyIdAndDate(Long agencyId) {
        return rentInfoDao.housesByAgencyIdAndDate(agencyId);
    }
}
