package com.example.adambackend.repository;

import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("select count(fa) from Favorite fa join Account a on fa.account.id= a.id join Product  p on fa.product.id= p.id where a.id=?1 and p.id=?2")
    Integer countFavoriteByAccountIdAndProductId(int idAccount,int idProduct);
    @Query("select p from Favorite  fa join Product p on fa.product.id=p.id join Account a on a.id=fa.account.id where a.id=?1")
    Product findProductFavoriteByAccountId (Long accountId);
    @Query(value = "select top(10) p from products p join favorites fa on p.id=fa.product_id group by p.id,fa.product_id orderby count(fa) desc ",nativeQuery = true)
    List<Product> findTop10FavoriteProduct();
    @Query("select fa from Favorite  fa join Account  a on fa.account.id=a.id join Product  p on p.id=fa.product.id where a.id=?1 and p.id=?2")
    Favorite findByAccountIdAndProductId(Long accountId,Long productId);
    @Transactional
    @Modifying
    @Query( value = "delete from favorites fa join accounts a on a.id=fa.account_id join products p on p.id=fa.product_id where a.id=?1 and p.id=?2",nativeQuery = true)
    void deleteFavoriteByAccountIdAndProductId(Long accountId,Long productId);
}
