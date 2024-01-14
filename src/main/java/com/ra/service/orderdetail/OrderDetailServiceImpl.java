package com.ra.service.orderdetail;

import com.ra.dto.respose.orders.OrderDetailResponseDTO;
import com.ra.exception.OrderNotFoundException;
import com.ra.model.OrderDetail;
import com.ra.model.Orders;
import com.ra.model.Product;
import com.ra.repository.OrderDetailRepository;
import com.ra.repository.OrderRepository;
import com.ra.service.order.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Transactional
    @Override
    public List<OrderDetailResponseDTO> findAll(Long orderId) throws OrderNotFoundException {
//        List<Orders> list = orderRepository.findAll();
//        return list.stream()
//                .map((orders ->
//                new OrderDetailResponseDTO
//                        (orders.getId(),orders.getFullName(),orders.getAddress(),orders.getPhone(),orders.getEmail(),orders.getNotes(),orders.getTotal(),orders.getStatus()))).toList();
    return null;
    }
}
