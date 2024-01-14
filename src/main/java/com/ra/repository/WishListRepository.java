package com.ra.repository;

import com.ra.model.Product;
import com.ra.model.User;
import com.ra.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {
    List<WishList>findAllByUser(User user);
    Boolean existsWishListByProduct(Product product);
    Boolean existsWishListByUserAndProduct(User user,Product product);
}
