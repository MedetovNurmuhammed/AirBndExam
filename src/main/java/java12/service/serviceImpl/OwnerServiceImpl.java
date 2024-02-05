package java12.service.serviceImpl;

import java12.dao.OwnerDao;
import java12.dao.daoImpl.OwnerDaoImpl;
import java12.entities.House;
import java12.entities.Owner;
import java12.service.OwnerService;

import java.util.List;
import java.util.Optional;

public class OwnerServiceImpl implements OwnerService {
    OwnerDao ownerDao = new OwnerDaoImpl();

    @Override
    public String saveOwner(Owner owner) {
        return ownerDao.saveOwner(owner);
    }

    @Override
    public List<Owner> getaAllOwner() {
        return ownerDao.getaAllOwner();
    }

    @Override
    public String saveOwner(Owner newOwner, House newHouse) {
        return ownerDao.saveOwner(newOwner,newHouse);
    }

    @Override
    public String updateOwnerById(Long id, Owner newOwner) {
        return ownerDao.updateOwnerById(id,newOwner);
    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        return ownerDao.findOwnerById(ownerId);
    }

    @Override
    public String deleteOwnerById(Long ownerId) {
        return ownerDao.deleteOwnerById(ownerId);
    }

    @Override
    public String assignOwnerToAgency(Long ownerId, Long agencyId) {
        return ownerDao.assignOwnerToAgency(ownerId,agencyId);
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        return ownerDao.getOwnersByAgencyId(agencyId);
    }
}
