package java12.service.serviceImpl;

import java12.dao.AgencyDao;
import java12.dao.daoImpl.AgencyDaoImpl;
import java12.entities.Agency;
import java12.service.AgencyService;

import java.util.List;

public class AgencyServiceImpl implements AgencyService {
    AgencyDao agencyDao = new AgencyDaoImpl();
    @Override
    public String saveAgency(Agency agency) {
        return agencyDao.saveAgency(agency);
    }

    @Override
    public List<Agency> getaAllAgency() {
        return agencyDao.getaAllAgency();
    }

    @Override
    public void DeleteAgencyById(Long id) {
        agencyDao.DeleteAgencyById(id);

    }

    @Override
    public String updateAgencyById(Long id,Agency agency) {

        return agencyDao.updateAgencyById(id,agency);
    }

    @Override
    public Agency findByAgencyId(Long id) {
        return agencyDao.findByAgencyId(id);
    }
}
