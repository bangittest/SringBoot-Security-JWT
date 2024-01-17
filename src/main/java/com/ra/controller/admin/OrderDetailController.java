package com.ra.controller.admin;

import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.exception.OrderNotFoundException;
import com.ra.exception.UserNotFoundException;
import com.ra.model.Category;
import com.ra.model.Color;
import com.ra.service.email.EmailService;
import com.ra.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @PatchMapping("/order/{orderId}/status/{status}")
    public ResponseEntity<?>updateStatus(@PathVariable("orderId") String orderId ,@PathVariable("status")String status) throws OrderNotFoundException, UserNotFoundException {
        try {
            Long id= Long.valueOf(orderId);
            Long changeStatus= Long.valueOf(status);
            if (changeStatus < 0 || changeStatus > 2) {
                return new ResponseEntity<>("Trạng thái chỉ có ( 1 là xác nhận , 2 là hủy )", HttpStatus.BAD_REQUEST);
            }
            if (changeStatus==1){
                OrderResponseDTO orderResponseDTO=orderService.updateStatus(id,1);
                emailService.senEmailOrder(orderResponseDTO);
                return new ResponseEntity<>("đã duyệt đơn hàng thành công " + orderResponseDTO,HttpStatus.OK);
            }else {
                OrderResponseDTO orderResponseDTO=orderService.updateStatus(id,2);
            return new ResponseEntity<>("đã hủy đơn hàng thành công ",HttpStatus.OK);
            }
        }catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid number", HttpStatus.BAD_REQUEST);
        }
    }
}
