package com.example.adambackend.repository;

import com.example.adambackend.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("select count(fa) from Favorite fa join Account a on fa.account.id= a.id join Product  p on fa.product.id= p.id where a.id=?1 and p.id=?2")
    Integer countFavoriteByAccountIdAndProductId(int idAccount,int idProduct);
}
