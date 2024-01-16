package com.ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
    private Long quantity;
    private Float price;

    public OrderDetail(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.orders = orderDetail.getOrders();
        this.product = orderDetail.getProduct();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
    }
}
