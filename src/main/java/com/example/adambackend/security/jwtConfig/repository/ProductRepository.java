package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p")
    List<Product> findAll(Sort sort);

    @Query(value = "select top(10) p from Product p order by p.createDate", nativeQuery = true)
    List<Product> findTop10productByCreateDate();

    @Query(value = "select p from products p join detail_products dp on p.id=dp.product_id " +
            "join colors c on c.id=dp.color_id " +
            "join size s on s.id=dp.size_id " +
            "join brand b on b.id=p.brand_id " +
            "join material m on m.id=p.material_id " +
            "where 1=1 and (c.color_name like ?1 or ?1 is null) " +
            "and (s.size_name like ?2 or ?2 is null)" +
            "and (b.brand_name like ?3 or ?3 is null)" +
            "and (m.material_name like ?4 or ?4 is null)" +
            "and p.product_export between ISNULL(?5) or IsNull(?6)",nativeQuery = true)
    List<Product> findByColorSizePriceBrandAndMaterial(String colorName,String sizeName,String brand,String material,double bottomPrice,double topPrice);
   @Query(value = "select p from products p join tag_products tp on p.id= tp.product_id join tags t on t.id=tp.tag_id where t.tag_name=?1 ",nativeQuery = true)
    List<Product> findAllByTagName(String tagName);
}
