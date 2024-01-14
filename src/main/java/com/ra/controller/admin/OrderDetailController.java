package com.ra.controller.admin;

import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.exception.OrderNotFoundException;
import com.ra.exception.UserNotFoundException;
import com.ra.service.email.EmailService;
import com.ra.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class OrderDetailController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailService emailService;
    @GetMapping("/orders")
    private ResponseEntity<?>finAll(@RequestParam(name = "limit" ,defaultValue = "5")Integer limit
            ,@RequestParam(name = "page" ,defaultValue = "0")Integer noPage){
        Pageable pageable= PageRequest.of(noPage,limit);
        Page<OrderResponseDTO>list=orderService.findAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PatchMapping("/order/{orderId}/status")
    public ResponseEntity<?>updateStatus(@PathVariable("orderId") Long orderId) throws OrderNotFoundException, UserNotFoundException {
        OrderResponseDTO orderResponseDTO=orderService.updateStatus(orderId,1);
//        OrderResponseDTO orderResponseDTO1=orderService.findByOrderId(orderResponseDTO.getId());
        emailService.senEmailOrder(orderResponseDTO);
        return new ResponseEntity<>("đã duyệt đơn hàng thành công",HttpStatus.OK);
    }
    @PatchMapping("/order/{orderId}/cancel")
    public ResponseEntity<?>cancel(@PathVariable("orderId") Long orderId) throws OrderNotFoundException {
        OrderResponseDTO orderResponseDTO=orderService.updateStatus(orderId,2);
        return new ResponseEntity<>("đã hủy đơn hàng thành công",HttpStatus.OK);
    }
}
