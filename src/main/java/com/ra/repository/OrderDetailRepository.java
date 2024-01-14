package com.ra.repository;

import com.ra.model.OrderDetail;
import com.ra.model.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail>findAllByOrders(Orders orders);
    List<OrderDetail> findByOrders_Id(Long orderId);
    Boolean existsByOrders(Orders orders);
    @Modifying
    @Transactional
    @Query("SELECT od FROM OrderDetail od WHERE od.orders.id = :orderId")
    List<OrderDetail> findAllByOrderId(@Param("orderId") Long orderId);

}
