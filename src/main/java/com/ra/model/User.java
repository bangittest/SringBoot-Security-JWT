package com.ra.model;

import com.ra.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String token;
    private LocalDateTime resetTokenExpiry;
    @Column(unique = true)
    private String email;
    private String fullName;
    @Column(columnDefinition = "boolean default true")
    private Boolean status=true;
    private String phone;
    private String address;
    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns =@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role>roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private Set<Orders>orders;
    @OneToMany(mappedBy = "user")
    private Set<WishList>wishLists;
}
