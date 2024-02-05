package java12.service;

import java12.entities.Agency;

import java.util.List;

public interface AgencyService {
  //  CRUD
    String saveAgency(Agency agency);
    List<Agency>getaAllAgency();
    void DeleteAgencyById(Long id);
    String updateAgencyById(Long id,Agency agency);
  Agency findByAgencyId(Long id);
    //    Agency тузулуп жатканда адреси кошо тузулсун!
//    Agency очкондо адреси жана rent_info кошо очсун
//    Constraint: phoneNumber (+996) and length(13)
}
