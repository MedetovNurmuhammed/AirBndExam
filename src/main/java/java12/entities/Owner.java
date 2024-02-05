package java12.entities;

import jakarta.persistence.*;
import java12.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "owners")
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "owner_id_gen",allocationSize = 1)

public class Owner extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Owner(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    @ManyToMany(mappedBy = "owners")
    private List<Agency>agencies;
@OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<House>houseList;
@OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE,orphanRemoval = true)
private List<RentInfo>rentInfos;
    public void addHouse(House house) {
        if (houseList == null) {
            houseList = new ArrayList<>();
        }
        houseList.add(house);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "firstName='" + firstName + "\n" +
                "lastName='" + lastName + "\n" +
                "email='" + email + "\n" +
                "dateOfBirth=" + dateOfBirth +"\n"+
                "gender=" + gender +"\n"+
                "} " ;
    }
}

