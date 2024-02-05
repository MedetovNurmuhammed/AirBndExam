package java12.dao;

import java12.entities.Agency;

import java.util.List;

public interface AgencyDao {
    String saveAgency(Agency agency);
    List<Agency> getaAllAgency();
    void DeleteAgencyById(Long id);
    String updateAgencyById(Long id,Agency newAgency);
    Agency findByAgencyId(Long id);
}
