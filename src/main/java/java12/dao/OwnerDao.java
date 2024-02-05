package java12.dao;

import java12.entities.Agency;
import java12.entities.House;
import java12.entities.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDao {
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

    //Owner эки жол менен тузулсун(1-озу жалгыз 2- house менен чогу ), жашы 18-
    //ден кичине owner-лер тузулбосун
    //Owner-ди agency-ге assign кылган метод болсун
    //Constraint: email unique
    //Owner-ди очуруп жатканда, house-тары чогу очот. Бирок ижарасы жок болсо
    //очсун, эгерде ижарасы бар болсо checkout датасын текшерсин. Учурдагы
    //датадан мурун болсо rent_info менен чогу очуп кетсин. Бирок customer очпосун.
    //Агентсвонун id-си менен owner-лерди ала турган метод туз

