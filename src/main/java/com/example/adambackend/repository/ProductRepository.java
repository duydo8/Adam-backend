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


    @Query(value = "select pro.id as id, pro.productName as productName,pro.image as productImage, " +
            " pro.createDate as createDate,pro.description as Description ,min(dp.priceExport) as " +
            " minPrice, max (dp.priceExport)as maxPrice " +
            "            from Product pro " +
            "            join Category ca on pro.category.id=ca.id " +
            "            join DetailProduct dp on pro.id=dp.product.id " +
            "            join Size s on dp.size.id=s.id " +
            "            join TagProduct tp on tp.product.id=pro.id " +
            "            join Tag t on t.id=tp.tag.id " +
            "            join MaterialProduct mp on mp.product.id=pro.id " +
            "            join Material m on m.id=mp.material.id  " +
            "            join Color co on co.id=dp.color.id " +
            "where pro.isActive=true and pro.isDelete=false and " +

            " ca.id=?1  or ?1 is null or dp.size.id=?2  or ?2 is null \n" +
            "or dp.color.id=?3 or ?3 is null or  m.id=?4  or ?4 is null \n" +
            "or t.id=?5  or ?5 is null or dp.priceExport BETWEEN ?6  and ?7  " +
            "GROUP BY pro.productName,pro.image,pro.createDate,pro.id order by pro.id ")
    Page<CustomProductFilterRequest> findPageableByOption(Integer categoryId, Integer sizeId, Integer colorId, Integer materialId, Integer tagId,
                                                          Double bottomPrice, Double topPrice, Pageable pageable);

    @Query(value = "select p from products p join tag_products tp on p.id= tp.product_id " +
            "join tags t on t.id=tp.tag_id where p.is_completed=1 " +
            "and p.is_active=1 and p.is_deleted=0 and  t.tag_name=?1 ", nativeQuery = true)
    List<Product> findAllByTagName(String tagName);

    @Query(value = "select p.id,p.product_name,p.create_date,p.image,p.description,p.is_active,p.is_completed,p.is_deleted,p.category_id,p.vote_average from products p \n" +
            "join detail_products dp on p.id=dp.product_id join detail_orders dos on dos.detail_product_id=dp.id where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 " +
            "group by p.id,dos.quantity,p.product_name,p.create_date,p.image,p.description,p.is_deleted,p.category_id,p.vote_average\n" +
            " ORDER BY count(dos.quantity) limit 10", nativeQuery = true)
    List<Product> findTop10ProductBestSale();

    @Query(value = "select p from Product p join DetailProduct dp on p.id=dp.product.id " +
            "where dp.id=?1")
    Product findByDetailProductId(Integer detalId);

    @Query(value = "select p.id as id,p.product_name as productName,p.is_active as isActive,p.description as description,min(price_export) as minPrice, " +
            "             max(price_export) as maxPrice from detail_products dp join products p " +
            "            ON p.id=dp.product_id where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 and p.id=?1 " +
            "group by p.product_name,p.is_active,p.is_completed,p.is_deleted,p.id  ", nativeQuery = true)
    ProductHandleValue findOptionByProductId(Integer productId);

    @Query(value = "select p.id as id,p.product_name as productName,p.is_active as isActive,p.description as description,min(price_export) as minPrice, " +
            "             max(price_export) as maxPrice,p.vote_average as voteAverage  from detail_products dp join products p \n" +
            "            ON p.id=dp.product_id left join favorites fa on fa.product_id=p.id join accounts a on a.id=fa.account_id \n" +
            " where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 and p.id=?1 and (a.id=?2 or ?2 is null) \n" +
            "group by p.product_name,p.is_active,p.is_completed,p.is_deleted,p.id,fa.is_active,p.vote_average ", nativeQuery = true)
    Optional<ProductHandleWebsite> findOptionWebsiteByProductId(Integer productId, Integer accountId);

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
