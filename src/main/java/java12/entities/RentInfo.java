package java12.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

@SequenceGenerator(name = "base_id_gen",sequenceName = "rent_into_idgen",allocationSize = 1)
public class RentInfo extends BaseEntity {
    private LocalDate checkout;
    private LocalDate checkin;
    @OneToOne(mappedBy = "rentInfo",cascade = CascadeType.PERSIST)
    private House house;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Owner owner;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Agency agency;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private  Customer customers;


    public RentInfo(LocalDate checkout, LocalDate checkin) {
        this.checkout = checkout;
        this.checkin = checkin;
    }

    @Override
    public String toString() {
        return "RentInfo{" +
                "checkout=" + checkout +
                ", checkin=" + checkin +
                "} " ;
    }
}

