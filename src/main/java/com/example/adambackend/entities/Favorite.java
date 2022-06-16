package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorites")
@Entity
public class Favorite {
    @EmbeddedId
    private FavoriteId favoriteId;
    @Column(name = "time_create")
    private LocalDateTime time_create;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @ManyToOne
    @MapsId("accountId")
    @JsonBackReference
    @JoinColumn(name = "account_id",insertable = false,updatable = false)
    private Account account = new Account();

    @ManyToOne
    @MapsId("productId")
    @JsonBackReference
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product = new Product();



}
