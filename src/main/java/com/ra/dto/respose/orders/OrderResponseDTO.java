package com.ra.dto.respose.orders;

import com.ra.model.OrderDetail;
import com.ra.model.Orders;
import com.ra.model.Product;
import com.ra.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String notes;
    private Float total;
    private Integer status;
    private String userName;
    List<OrderDetailResponseDTO> orderDetailResponseDTOS;
    private String OrderDate;

    public OrderResponseDTO(Orders orders) {
        this.id = orders.getId();
        this.userName = orders.getUser().getUserName();
        this.fullName = orders.getFullName();
        this.address = orders.getAddress();
        this.phone = orders.getPhone();
        this.email = orders.getEmail();
        this.notes = orders.getNotes();
        this.total = orders.getTotal();
        this.status = orders.getStatus();
        this.orderDetailResponseDTOS = orders.getOrderDetails().stream().map(OrderDetailResponseDTO::new)
                .collect(Collectors.toList());

        this.OrderDate = formatOrderDate(orders.getOrderDate());
    }

    private String formatOrderDate(Date orderDate) {
        if (orderDate != null) {
            LocalDateTime localDateTime = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return formatter.format(localDateTime);
        }
        return null;
    }
}
