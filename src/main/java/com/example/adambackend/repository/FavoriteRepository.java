package com.example.adambackend.repository;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.FavoriteId;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    @Query("select count(fa) from Favorite fa join Account a on fa.account.id= a.id join Product  p on fa.product.id= p.id where a.id=?1 and p.id=?2")
    Integer countFavoriteByAccountIdAndProductId(int idAccount, int idProduct);

        @Query(value = "select p.id as id,p.product_name as productName,p.is_active as isActive,p.image as image," +
                " MAX(dp.price_export) as maxPrice,MIN(dp.price_import) " +
                "as minPrice, p.vote_average as voteAverage from favorites  fa join products p on fa.product_id=p.id " +
                "            join detail_products dp on dp.product_id=p.id " +
                "            join accounts a on a.id=fa.account_id where p.is_active=1 " +
                "and p.is_deleted=0 and is_completed=1 and a.id=?1 " +
                "            GROUP BY p.id,p.product_name,p.image ", nativeQuery = true)
    List<ProductHandleWebsite> findProductFavoriteByAccountId(Integer accountId);
    @Query(value = "select p.id as id,p.product_name as productName,p.is_active as isActive,p.image as image,\n" +
            "    MAX(dp.price_export) as maxPrice,MIN(dp.price_import)\n" +
            "    as minPrice, p.vote_average as voteAverage from products p\n" +
            "    join detail_products dp on dp.product_id=p.id\n" +
            "    where  p.is_deleted=0 and is_completed=1 and p.id=?1\n" +
            "    GROUP BY p.id,p.product_name,p.image  ",nativeQuery = true)
ProductHandleWebsite findProductById(Integer id);
    @Query(value = "select product_id from favorites fa join products p on p.id=fa.product_id  where  p.is_deleted=0 and is_completed=1 \n" +
            "group by product_id ORDER BY count(product_id) desc limit 10 ",nativeQuery = true)
    List<Integer> findTop10FavoriteProductId();


    @Query("select fa from Favorite  fa join Account  a on fa.account.id=a.id join Product  p on p.id=fa.product.id where a.id=?1 and p.id=?2")
    Optional<Favorite> findByAccountIdAndProductId(Integer accountId, Integer productId);

    @Transactional
    @Modifying
    @Query(value = "delete from favorites  where account_id=?1 and product_id=?2", nativeQuery = true)
    void deleteFavoriteByAccountIdAndProductId(Integer accountId, Integer productId);

    @Transactional
    @Modifying
    @Query(value = "delete from favorites where product_id=?1", nativeQuery = true)
    void deleteByProductId(Integer productId);
}
