package com.ra.service.email;

import com.ra.dto.respose.orders.OrderResponseDTO;
import com.ra.model.User;

public interface EmailService {
    String sendEmail();
    void sendEmailPass(User user,String token);
    void senEmailOrder(OrderResponseDTO orderResponseDTO);
}
