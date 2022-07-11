package com.example.adambackend.repository;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query("select count(fa) from Favorite fa join Account a on fa.account.id= a.id join Product  p on fa.product.id= p.id where a.id=?1 and p.id=?2")
    Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct);

    @Query(value = "select p.id,p.product_name,p.image,MAX(dp.price_export) as maxPrice,MIN(dp.price_import) " +
            "as minPrice from favorites  fa join products p on fa.product_id=p.id " +
            "            join detail_products dp on dp.product_id=p.id " +
            "            join accounts a on a.id=fa.account_id where p.is_active=1 " +
            "and p.is_deleted=0 and is_completed=1 and a.id=?1 " +
            "            GROUP BY p.id,p.product_name,p.image ",nativeQuery = true)
    List<ProductHandleWebsite> findProductFavoriteByAccountId(Integer accountId);

    @Query(value = "select top(10) p from products p join favorites fa on p.id=fa.product_id group by p.id,fa.product_id orderby count(fa) desc ", nativeQuery = true)
    List<ProductHandleWebsite> findTop10FavoriteProduct();

    @Query("select fa from Favorite  fa join Account  a on fa.account.id=a.id join Product  p on p.id=fa.product.id where a.id=?1 and p.id=?2")
    Favorite findByAccountIdAndProductId(Integer accountId, Integer productId);

    @Transactional
    @Modifying
    @Query(value = "delete from favorites  where account_id=?1 and product_id=?2", nativeQuery = true)
    void deleteFavoriteByAccountIdAndProductId(Integer accountId, Integer productId);

    @Transactional
    @Modifying
    @Query(value = "delete from favorites where product_id=?1", nativeQuery = true)
    void deleteByProductId(Integer productId);
}
