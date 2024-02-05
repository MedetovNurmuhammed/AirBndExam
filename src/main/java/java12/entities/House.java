package java12.entities;

import jakarta.persistence.*;
import java12.enums.HouseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "houses")
@NoArgsConstructor
@SequenceGenerator(name = "base_id_gen",sequenceName = "house_id_gen",allocationSize = 1)
public class House extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    private BigDecimal price;
    private double rating;
    private String description;
    private int room;
    private boolean furniture;

    public House(HouseType houseType, BigDecimal price, double rating, String description, int room, boolean furniture) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
    }

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private Address address;
    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    private RentInfo rentInfo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Owner owner;


}

