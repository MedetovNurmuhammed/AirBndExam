package java12.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "addresses")
//@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "address_id_gen",allocationSize = 1)
public class Address extends BaseEntity{
    private String city;
    private String region;
    private String street;

    public Address(String city, String region, String street) {
        this.city = city;
        this.region = region;
        this.street = street;
    }

    @OneToOne(mappedBy = "address")
    private Agency agency;
    @OneToOne
    private House house;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", street='" + street + '\'' +
                ", agency=" + agency +
                ", house=" + house +
                "} ";
    }
}
