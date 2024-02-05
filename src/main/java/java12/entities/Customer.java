package java12.entities;

import jakarta.persistence.*;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "customer_id_gen",allocationSize = 1)
public class Customer extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String nationality;
   @Enumerated(EnumType.STRING)
    private FamilyStatus familyStatus;

    public Customer(String firstName, String lastName, String email, Gender gender, String nationality, FamilyStatus familyStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.nationality = nationality;
        this.familyStatus = familyStatus;
    }
    @OneToMany(mappedBy = "customers",cascade = {CascadeType.REMOVE,CascadeType.PERSIST},orphanRemoval = true)//,cascade = CascadeType.PERSIST
    private List<RentInfo> rentInfo;



    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", nationality='" + nationality + '\'' +
                ", familyStatus=" + familyStatus +
                "} " ;
    }





}
