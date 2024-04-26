package com.example.adambackend.repository;

import com.example.adambackend.entities.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetailProductRepository extends JpaRepository<DetailProduct, Integer> {
	@Query(value = "select * from detail_products  dp join products p on dp.product_id=p.id " +
			"where p.id=?1 and p.is_active=true and p.is_deleted=false and p.is_completed=true", nativeQuery = true)
	List<DetailProduct> findAllByProductId(Integer id);

	@Transactional
	@Modifying
	@Query(value = "delete from detail_products where product_id = ?1", nativeQuery = true)
	void deleteByProductId(Integer productId);

	@Modifying
	@Transactional
	@Query(value = "update detail_products set status = 0 where id = ?1", nativeQuery = true)
	void updateDetailProductsDeleted(Integer id);

	@Query("select dp from DetailProduct dp where dp.status = 1 and dp.id = ?1")
	Optional<DetailProduct> findById(Integer id);
}
