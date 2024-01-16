package com.ra.service.wishlist;

import com.ra.dto.request.wishlist.WishListRequestDTO;
import com.ra.dto.respose.wishlist.WishListResponseDTO;
import com.ra.exception.ProductNotFoundExceptions;
import com.ra.exception.QuantityException;
import com.ra.exception.UserNotFoundException;
import com.ra.exception.WishListException;
import com.ra.model.Product;
import com.ra.model.User;
import com.ra.model.WishList;
import com.ra.repository.ProductRepository;
import com.ra.repository.UserRepository;
import com.ra.repository.WishListRepository;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceImpl implements WishListService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserService userService;
    @Override
    public void addWishList(Long userId, WishListRequestDTO wishListRequestDTO) throws UserNotFoundException, WishListException, QuantityException {
        try {
            Long productId= Long.valueOf(wishListRequestDTO.getProductId());
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundExceptions("Product not found"));

            if (wishListRepository.existsWishListByUserAndProduct(user,product)){
                throw new WishListException("san pham yeu thich da ton tai in the wish list");
            } else {
                WishList wishList = new WishList();
                wishList.setUser(user);
                wishList.setProduct(product);
                wishListRepository.save(wishList);
            }
        }catch (Exception e){
            throw new QuantityException("vui long nhap so error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteWishList(Long userId, Long wishListId) throws UserNotFoundException, WishListException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new WishListException("wishlist not found "  +wishListId));

        if (!wishList.getUser().equals(user)) {
            throw new WishListException("wish List not found for the given user");
        }
        wishListRepository.deleteById(wishListId);
    }

    @Override
    public List<WishListResponseDTO> findByUser(Long userId) throws UserNotFoundException {
        User user=userService.findById(userId);
        List<WishList>wishLists=wishListRepository.findAllByUser(user);
        return wishLists.stream().map(WishListResponseDTO::new).toList();
    }
}
