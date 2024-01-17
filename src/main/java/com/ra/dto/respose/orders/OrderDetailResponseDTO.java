package com.ra.dto.respose.orders;

import com.ra.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class OrderDetailResponseDTO {
    private String productName;
    private String sizeName;
    private String colorName;
    private String image;
    private Long quantity;
    private Float price;
    private Float totalPrice;

    public OrderDetailResponseDTO(OrderDetail orderDetail) {
        this.productName = orderDetail.getProduct().getProductName();
        this.sizeName=orderDetail.getSizeName();
        this.colorName=orderDetail.getColorName();
        this.image = orderDetail.getProduct().getImage();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
        this.totalPrice=orderDetail.getPrice()*orderDetail.getQuantity();
    }
}
