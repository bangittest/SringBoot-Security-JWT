package com.ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String productName;
    private String description;
    private Float unitPrice;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id" ,referencedColumnName = "id")
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Cart> carts;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<OrderDetail>orderDetails;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<WishList>wishLists;
    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(name = "product_color",joinColumns = @JoinColumn(name = "product_id")
            ,inverseJoinColumns =@JoinColumn(name = "color_id") )
    Set<Color>colors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_size",joinColumns = @JoinColumn(name = "product_id")
            ,inverseJoinColumns =@JoinColumn(name = "size_id") )
    Set<Size>sizes;
}
