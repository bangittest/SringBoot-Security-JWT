package com.ra.repository;

import com.ra.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId and c.product.id=:productId")
    Cart findByUserIdAndProductId(@Param("userId")Long userId,@Param("productId")Long productId);
    List<Cart> findAllByUser(User user);
    Cart findByUser_IdAndProduct_Id(Long user_id, Long product_id);
    Cart findByProductId(Long productId);
    @Transactional
    @Modifying
    @Query("DELETE from Cart c where c.id=:cartId")
    void deleteCartById(@Param("cartId") Long cart);
//    Boolean existsCartByProduct(Product product);
    Boolean existsCartByProductAndUser(Product product, User user);
    Cart findCartByProductAndUser(Product product,User user);
    Cart findCartByProductAndUserAndColorAndSize(Product product, User user, Color color, Size size);
}
