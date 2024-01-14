package com.ra.repository;

import com.ra.model.Cart;
import com.ra.model.Product;
import com.ra.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser_IdAndProduct_Id(Long user_id, Long product_id);
    Cart findByProductId(Long productId);
    @Transactional
    @Modifying
    @Query("DELETE from Cart c where c.id=:cartId")
    void deleteCartById(@Param("cartId") Long cart);
//    Boolean existsCartByProduct(Product product);
    Boolean existsCartByProductAndUser(Product product, User user);
}
