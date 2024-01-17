package com.ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String token;
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
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Orders>orders;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<WishList>wishLists;
}
