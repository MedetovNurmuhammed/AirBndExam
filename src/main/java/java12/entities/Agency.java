package java12.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter

@Table(name = "agencies")
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "agency_id_gen",allocationSize = 1)
public class Agency extends BaseEntity{
    private String name;
    private String phoneNumber;

    public Agency(String name, String phoneNumber, Address address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    @ManyToMany
    private List<Owner>owners;
    @OneToOne(cascade ={ CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private Address address;
    @OneToMany(cascade ={ CascadeType.REMOVE,CascadeType.PERSIST},orphanRemoval = true)
    private List<RentInfo>rentInfos;

    @Override
    public String toString() {
        return "Agency{" +
                "name= " + name + "\n" +
                "phoneNumber= " + phoneNumber + "\n" ;
              //  "address= " + address +"\n";
    }
}
