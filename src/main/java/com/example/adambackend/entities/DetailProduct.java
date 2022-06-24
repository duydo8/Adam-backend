package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_products")
@Entity
public class DetailProduct {
    @JsonIgnore
    @OneToMany(mappedBy = "detailProduct")
    List<CartItems> cartItems = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    @Column(name = "price_import")
    private Double priceImport;
    @Column(name = "price_export")
    private Double priceExport;
    @Column(name = "is_deleted")
    private Boolean isDelete;
    @Column(name = "image_product")
    private String productImage;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
