package com.ra.repository;

import com.ra.model.Orders;
import com.ra.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders>findAllByUserOrderByOrderDateDesc(User user);
    @Transactional
    @Modifying
    @Query("SELECT c FROM Orders c WHERE c.user.id = :userId and c.id=:orderId ORDER BY c.orderDate DESC" )
    List<Orders> findALLOrdersByUserId(@Param("userId") Long userId,@Param("orderId") Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Orders c SET c.status = :status  WHERE c.id = :id")
    void updateOrderStatus(@Param("id") Long id,@Param("status") Integer status);
}
