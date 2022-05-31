package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="favorites")
@Entity
public class Favorite {
    @EmbeddedId
    private FavoriteId favoriteId;
    @Column(name="time_create")
    private LocalDateTime time_create;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name="account_id")
    private Account account = new Account();

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    private Product product = new Product();

}
