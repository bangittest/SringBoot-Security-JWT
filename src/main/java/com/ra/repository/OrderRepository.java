package com.ra.repository;

import com.ra.model.Category;
import com.ra.model.Orders;
import com.ra.model.Product;
import com.ra.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders>findAllByUser(User user);
    @Transactional
    @Modifying
    @Query("SELECT c FROM Orders c WHERE c.user.id = :userId and c.id=:orderId ORDER BY c.orderDate DESC" )
    List<Orders> findALLOrdersByUserId(@Param("userId") Long userId,@Param("orderId") Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE Orders c SET c.status = :status  WHERE c.id = :id")
    void updateOrderStatus(@Param("id") Long id,@Param("status") Integer status);

    @Query("SELECT SUM(o.total) FROM Orders o WHERE MONTH(o.orderDate) = :month")
    BigDecimal getTotalSalesByMonth(@Param("month") int month);
    @Query("SELECT SUM(total) FROM Orders")
    BigDecimal getTotal();

    @Query("SELECT SUM(o.total) FROM Orders o JOIN o.orderDetails od JOIN od.product p WHERE p.category = :category")
    BigDecimal getTotalByCategory(@Param("category") Category category);
    @Query("SELECT SUM(o.total) FROM Orders o JOIN o.orderDetails od  WHERE od.product = :product")
    BigDecimal getTotalByProduct(@Param("product") Product product);
}
