package com.ra.service.order;

import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.exception.OrderNotFoundException;
import com.ra.exception.UserNotFoundException;
import com.ra.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO>findAllById(Long userId) throws UserNotFoundException;
    List<OrderResponseDTO>findAllByOrderId(Long userId,Long orderId) throws UserNotFoundException, OrderNotFoundException;
    OrderResponseDTO findByOrderId(Long orderId) throws UserNotFoundException;
    Page<OrderResponseDTO> findAll(Pageable pageable);
    OrderResponseDTO updateStatus(Long orderId,Integer status) throws OrderNotFoundException;
    Orders findById(Long orderId) throws OrderNotFoundException;
}