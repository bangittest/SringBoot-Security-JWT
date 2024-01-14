package com.ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String categoryName;
    @Column(columnDefinition = "boolean default true")
    private Boolean status=true;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    List<Product>products;
}
