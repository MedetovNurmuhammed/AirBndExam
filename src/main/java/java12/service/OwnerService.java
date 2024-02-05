package java12.service;

import java12.entities.House;
import java12.entities.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    public String saveOwner(Owner owner);
    //CRUD
    List<Owner> getaAllOwner();
    String saveOwner(Owner newOwner, House newHouse);
    String updateOwnerById(Long id,Owner newOwner);
    Optional<Owner> findOwnerById(Long ownerId);
    String deleteOwnerById(Long ownerId);
    String assignOwnerToAgency(Long ownerId, Long agencyId);
    List<Owner> getOwnersByAgencyId(Long agencyId);
}
