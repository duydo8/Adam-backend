package com.example.adambackend.repository;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.CustomProductFilterRequest;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p")
    List<Product> findAll(Sort sort);

    @Query(value = "select * from products p where p.is_completed=1 and p.is_active=1 and p.is_deleted=0   order by p.create_date desc limit 10", nativeQuery = true)
    List<Product> findTop10productByCreateDate();


    @Query(value = "select p.id as id, p.product_name as productName,p.image as productImage " +
            "from products p \n" +
            "join categories ca on p.category_id=ca.id \n" +
            "join detail_products dp on p.id=dp.product_id \n" +
            "join sizes s on dp.size_id=s.id \n" +
            "join tag_products tp on tp.product_id=p.id \n" +
            "join tags t on t.id=tp.tag_id \n" +
            "join material_products mp on mp.product_id=p.id\n" +
            "join materials m on m.id=mp.material_id \n" +
            "join colors co on co.id=dp.color_id \n" +
            "where ca.is_active=1 and dp.is_active=1 and p.is_active=1 and p.is_completed=1" +
            "and s.is_active=1 and tp.is_active=1 and t.is_active=1 \n" +
            "and mp.is_active=1 and m.is_active=1 and co.is_active=1 \n" +
            "and ca.is_deleted=0 and dp.is_deleted=0 and p.is_deleted=0 \n" +
            "and s.is_deleted=0 and tp.is_deleted=0 and t.is_deleted=0 \n" +
            "and mp.is_deleted=0 and m.is_deleted=0 and co.is_deleted=0 \n" +
            "and ca.category_id=?1 or ?1 is null and s.id=?2 or ?2 is NULL \n" +
            "and co.id=?3 or ?3 is null and m.id=?4 or ?4 is null  \n" +
            "and t.id=?5 or ?5 is null and dp.price_export BETWEEN ?6 or ?6 is null \n" +
            "and ?7 or ?7 is null ", nativeQuery = true)
    List<CustomProductFilterRequest> findPageableByOption(int categoryId, int sizeId, int colorId, int materialId, int tagId,
                                                          double bottomPrice, double topPrice, Pageable pageable);

    @Query(value = "select p from products p join tag_products tp on p.id= tp.product_id join tags t on t.id=tp.tag_id where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 and  t.tag_name=?1 ", nativeQuery = true)
    List<Product> findAllByTagName(String tagName);

    @Query(value = "select p.id,p.product_name,p.create_date,p.image,p.description,p.is_deleted,p.category_id,p.vote_average from products p \n" +
            "join detail_products dp on p.id=dp.product_id join detail_orders dos on dos.detail_product_id=dp.id where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 " +
            "group by p.id,dos.quantity,p.product_name,p.create_date,p.image,p.description,p.is_deleted,p.category_id,p.vote_average\n" +
            " ORDER BY count(dos.quantity) limit 10", nativeQuery = true)
    List<Product> findTop10ProductBestSale();

    @Query(value = "select * from products p join detail_products dp on p.id=dp.product_id where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 and dp.id=?1", nativeQuery = true)
    Product findByDetailProductId(Integer detalId);

    @Query(value = "select p.id as id,p.product_name as productName,p.is_active as isActive,p.description as description,min(price_export) as minPrice, max(price_export) as maxPrice from detail_products dp join products p \n" +
            "ON p.id=dp.product_id where p.is_completed=1 and p.is_active=1 and p.is_deleted=0 and p.id=?1", nativeQuery = true)
    ProductHandleValue findOptionByProductId(int productId);

}
