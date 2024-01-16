package com.ra.service.order;

import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.exception.CategoryNotFoundException;
import com.ra.exception.OrderNotFoundException;
import com.ra.exception.UserNotFoundException;
import com.ra.model.Category;
import com.ra.model.Orders;
import com.ra.model.Product;
import com.ra.model.User;
import com.ra.repository.CategoryRepository;
import com.ra.repository.OrderRepository;
import com.ra.service.category.CategoryService;
import com.ra.service.user.UserService;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Transactional
    @Override
    public List<OrderResponseDTO> findAllById(Long userId) throws UserNotFoundException {
        User user=userService.findById(userId);
        List<Orders>orders=orderRepository.findAllByUserOrderByOrderDateDesc(user);
        return orders.stream().map((OrderResponseDTO::new)).toList();
    }

    @Override
    public List<OrderResponseDTO> findAllByOrderId(Long userId, Long orderId) throws UserNotFoundException, OrderNotFoundException {
        User user=userService.findById(userId);
        Orders orders1=findById(orderId);
        List<Orders>list=orderRepository.findALLOrdersByUserId(user.getId(),orders1.getId());
        return list.stream().map(OrderResponseDTO::new).toList();
    }

    @Override
    public OrderResponseDTO findByOrderId(Long orderId) throws UserNotFoundException {
        Optional<Orders> ordersOptional=orderRepository.findById(orderId);
        if (ordersOptional.isPresent()) {
            Orders orders = ordersOptional.get();
            return new OrderResponseDTO(orders);
        } else {
            throw new UserNotFoundException("Order not found with id: " + orderId);
        }
    }

    @Override
    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        Page<Orders>list=orderRepository.findAll(pageable);
        return list.map((OrderResponseDTO::new));
    }


    @Override
    public OrderResponseDTO updateStatus(Long orderId, Integer status) throws OrderNotFoundException {
        Optional<Orders> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            if (order.getStatus()==0){
                orderRepository.updateOrderStatus(orderId,status);
                return new OrderResponseDTO(order);
            }else {
                if (order.getStatus()==1){
                    throw new  OrderNotFoundException("đơn hàng đã xác nhận " + orderId);
                }else if(order.getStatus()==2){
                    throw new OrderNotFoundException("đơn hàng đã bị hủy " + orderId);
                }

            }
        }
        throw new OrderNotFoundException("Order not found with ID: " + orderId);
    }

    @Override
    public Orders findById(Long orderId) throws OrderNotFoundException {
        Orders orders=orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("order not found with ID: " + orderId));
        return orders;
    }

    @Override
    public BigDecimal getTotal() {
        return orderRepository.getTotal();
    }

    @Override
    public BigDecimal getTotalSalesByMonth(int month) {
        return orderRepository.getTotalSalesByMonth(month);
    }

    @Override
    public BigDecimal getTotalByCategoryId(Category category) {

        return orderRepository.getTotalByCategory(category);
    }

    @Override
    public BigDecimal getTotalByProduct(Product product) {
        return orderRepository.getTotalByProduct(product);
    }
}
