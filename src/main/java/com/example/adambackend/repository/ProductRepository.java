package com.example.adambackend.repository;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.product.ProductTop10Create;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleValue;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p where p.status = 1")
    Page<Product> findAll(Pageable pageable);

    @Query(value = "select p from Product p where p.status = 1 and p.productName like concat('%',:name,'%') ")
    Page<Product> findAll(@Param("name") String name, Pageable pageable);

    @Query(value = "select p from Product p where p.status = 1 order by p.createDate desc")
    List<Product> findAll();

    @Query(value = "select distinct(p.id) as id, p.product_name as productName, p.description as description, p.status as status," +
            "p.image as image,p.vote_average as VoteAverage, p.create_date as CreateDate," +
            "MIN(dp.price_export) as  minPrice, MAX(dp.price_export)as maxPrice" +

            "   from products p join detail_products dp on p.id = dp.product_id where p.status = 1 " +
            " and dp.price_export !=0 GROUP BY p.id,p.product_name,p.description,p.status,p.image,p.vote_average,p.create_date" +
            " order by p.create_date desc limit 10", nativeQuery = true)
    List<ProductTop10Create> findTop10productByCreateDate();


    @Query(value = "select  p.id as id, p.productName as productName,p.image as image, " +
            " p.createDate as createDate,p.description as Description ,min(dp.priceExport) as " +
            " minPrice, max (dp.priceExport)as maxPrice " +
            "            from Product p " +
            "            join DetailProduct dp on p.id = dp.product.id " +
            "            join Category ca on p.category.id = ca.id " +
            "            join Size s on dp.size.id = s.id " +
            "            join TagProduct tp on tp.product.id = p.id " +
            "            join Tag t on t.id = tp.tag.id " +
            "            join MaterialProduct mp on mp.product.id = p.id " +
            "            join Material m on m.id = mp.material.id  " +
            "            join Color co on co.id = dp.color.id " +
            "where p.status = 1 and " +
            " ca.id in (?1)  or ?1 is null and s.id in (?2)  or ?2 is null " +
            " and co.id in (?3) or ?3 is null and  m.id in (?4) or ?4 is null " +
            " and t.id in (?5) or ?5 is null  and dp.priceExport BETWEEN ?6  and ?7  " +
            " GROUP BY p.productName,p.image,p.createDate,p.id" +
            " order by p.id ")
    Page<CustomProductFilterRequest> findPageableByOption(List<Integer> categoryId, List<Integer> sizeId, List<Integer> colorId,
                                                          List<Integer> materialId, List<Integer> tagId,
                                                          Double bottomPrice, Double topPrice, Pageable pageable);

    @Query(value = "select p from products p join tag_products tp on p.id= tp.product_id " +
            "join tags t on t.id=tp.tag_id where p.status = 1 and  t.tag_name=?1 ", nativeQuery = true)
    List<Product> findAllByTagName(String tagName);

    @Query(value = "select p.id,p.product_name,p.create_date,p.image,p.description,p.status,p.category_id,p.vote_average from products p \n" +
            "join detail_products dp on p.id=dp.product_id join detail_orders dos on dos.detail_product_id=dp.id where p.status = 1 " +
            "group by p.id,dos.quantity,p.product_name,p.create_date,p.image,p.description,p.category_id,p.vote_average\n" +
            " ORDER BY count(dos.quantity) limit 10", nativeQuery = true)
    List<Product> findTop10ProductBestSale();

    @Query(value = "select p from Product p join DetailProduct dp on p.id=dp.product.id " +
            "where dp.id=?1")
    Product findByDetailProductId(Integer detalId);

    @Query(value = "select p.id as id,p.product_name as productName,p.status as status,p.description as description,min(price_export) as minPrice, " +
            "             max(price_export) as maxPrice from detail_products dp join products p " +
            "            ON p.id=dp.product_id where p.status = 1 and p.id=?1 " +
            "group by p.product_name, p.status, p.id  ", nativeQuery = true)
    Optional<ProductHandleValue> findOptionByProductId(Integer productId);

    @Query(value = "select p.id as id,p.product_name as productName,p.status as status,p.description as description,min(price_export) as minPrice, " +
            "             max(price_export) as maxPrice,p.vote_average as voteAverage  from detail_products dp join products p \n" +
            "            ON p.id=dp.product_id  join favorites fa on fa.product_id=p.id join accounts a on a.id=fa.account_id \n" +
            " where p.status = 1 and p.id=?1 and (a.id=?2 or ?2 is null) \n" +
            "group by p.product_name,p.status,p.id,p.vote_average ", nativeQuery = true)
    Optional<ProductHandleWebsite> findOptionWebsiteByProductId(Integer productId, Integer accountId);

    @Query(value = "select fa.product_id from favorites fa join products p on p.id=fa.product_id join accounts a on a.id=fa.account_id where p.id=?1 and a.id=?2", nativeQuery = true)
    List<Integer> checkFavorite(Integer productId, Integer accountId);

    @Modifying
    @Transactional
    @Query(value = "update products set status = 0 where id = ?1", nativeQuery = true)
    void updateProductsDeleted(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update products set status = ?1 where id = ?2", nativeQuery = true)
    void updateStatusProduct(Integer status, Integer id);

    @Query("select p from Product  p where p.status = 1 and p.id = ?1")
    Optional<Product> findById(Integer id);

}
