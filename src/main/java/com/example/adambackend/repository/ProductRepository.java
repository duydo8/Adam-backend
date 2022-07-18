package com.example.adambackend.repository;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleValue;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p where p.isDelete=false  and p.isActive=true ")
    Page<Product> findAll(Pageable pageable);
@Query(value = "select * from products where product_name like '%?1%'",nativeQuery = true)
List<Product>  findByName(String name);
    @Query(value = "select * from products p where p.is_completed=1 and p.is_active=1 and p.is_deleted=0   order by p.create_date desc limit 10", nativeQuery = true)
    List<Product> findTop10productByCreateDate();



    @Query(value = "select p from products p join tag_products tp on p.id= tp.product_id " +
            "join tags t on t.id=tp.tag_id where p.is_completed=1 " +
            "and p.is_active=1 and p.is_deleted=0 and  t.tag_name=?1 ", nativeQuery = true)
    List<Product> findAllByTagName(String tagName);


    @Query(value = "select p from Product p join DetailProduct dp on p.id=dp.product.id " +
            "where dp.id=?1")
    Product findByDetailProductId(Integer detalId);


    @Query(value = "select fa.product_id from favorites fa join products p on p.id=fa.product_id join accounts a on a.id=fa.account_id where p.id=?1 and a.id=?2", nativeQuery = true)
    List<Integer> checkFavorite(Integer productId, Integer accountId);

    @Modifying
    @Transactional
    @Query(value = "update products set is_deleted=1 , is_active=0 where id=?1", nativeQuery = true)
    void updateProductsDeleted(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update products set is_active=?1 where id=?2", nativeQuery = true)
    void updateProductsIsActive(Boolean isActive, Integer id);
}
