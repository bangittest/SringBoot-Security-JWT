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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "color_id",referencedColumnName = "id")
    private Color color;
    @ManyToOne
    @JoinColumn(name = "size_id",referencedColumnName = "id")
    private Size size;

}
