package java12.dao;

import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoDao {
    List<RentInfo> rentInfoBetweenDates(LocalDate fromDate, LocalDate toDate);
    Long housesByAgencyIdAndDate(Long agencyId);
}
