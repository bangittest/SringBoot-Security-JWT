package com.ra.service.orderdetail;

import com.ra.dto.respose.orders.OrderDetailResponseDTO;
import com.ra.exception.OrderNotFoundException;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponseDTO>findAll(Long orderId) throws OrderNotFoundException;
}
