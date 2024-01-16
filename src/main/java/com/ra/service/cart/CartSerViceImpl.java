package com.ra.service.cart;

import com.ra.dto.request.cart.AddToCartRequestDTO;
import com.ra.dto.request.cart.CartRequestDTO;
import com.ra.dto.respose.cart.CartResponseDTO;
import com.ra.exception.*;
import com.ra.model.*;
import com.ra.repository.*;
import com.ra.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartSerViceImpl implements CartService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<CartResponseDTO> findAllByUser(Long userId) throws UserNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("user id not found"));
        List<Cart>list=cartRepository.findAllByUser(user);
        return list.stream().map((CartResponseDTO::new)).toList();
    }

    @Override
    public void addToCart(Long userId, AddToCartRequestDTO addToCartRequestDTO) throws UserNotFoundException, QuantityException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        try {
            Long productId= Long.valueOf(addToCartRequestDTO.getProductId());
            Integer quantity= Integer.valueOf(addToCartRequestDTO.getQuantity());
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundExceptions("Product not found"));
            if (cartRepository.existsCartByProductAndUser(product,user)) {
                Cart cart = cartRepository.findByUser_IdAndProduct_Id(userId, product.getId());
                cart.setQuantity(cart.getQuantity() + quantity);
                cartRepository.save(cart);
            } else {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setProduct(product);
                newCart.setQuantity(quantity);
                cartRepository.save(newCart);
            }
        }catch (Exception e){
            throw new QuantityException("vui long nhap so" ,HttpStatus.BAD_REQUEST);
        }


    }

    @Transactional
    @Override
    public void updateProductQuantity(Long userId, CartRequestDTO cartRequestDTO)
            throws ProductIDNotFoundException, QuantityException {
       try {
           Long productId= Long.valueOf(cartRequestDTO.getProductId());
           Integer quantity = Integer.valueOf(cartRequestDTO.getQuantity());
           Cart cart=cartRepository.findByUserIdAndProductId(userId,productId);
           if (cart == null) {
               throw new ProductIDNotFoundException("Product not found");
           } else {
               if (quantity == 0) {
                   cartRepository.deleteCartById(cart.getId());
               } else {
                   cart.setQuantity(quantity);
                   cartRepository.save(cart);
               }
           }
       }catch (Exception e) {
           throw new QuantityException("vui long nhap so error", HttpStatus.BAD_REQUEST);
       }

    }

    @Override
    public void deleteCartItem(Long userId, Long cartId) throws UserNotFoundException, CartNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart item not found"));

        if (!cart.getUser().equals(user)) {
            throw new CartNotFoundException("Cart item not found for the given user");
        }
        cartRepository.deleteById(cartId);
    }



    @Transactional
    @Override
    public void checkout(Long userId) throws UserNotFoundException, CartEmptyException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getCarts().isEmpty()) {
            throw new CartEmptyException("Shopping cart is empty");
        }

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setOrderDate(new Date());
        orders.setNotes("");
        orders.setAddress(user.getAddress());
        orders.setEmail(user.getEmail());
        orders.setFullName(user.getFullName());
        orders.setPhone(user.getPhone());
        orders.setStatus(0);
        orders = orderRepository.save(orders);
        float total = 0;
        for (Cart cartItem : user.getCarts()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(Long.valueOf(cartItem.getQuantity()));
            orderDetail.setPrice(cartItem.getProduct().getUnitPrice());
            orderDetail.setOrders(orders);

            orderDetailRepository.save(orderDetail);
            total += cartItem.getProduct().getUnitPrice() * Long.valueOf(cartItem.getQuantity());
            cartRepository.deleteCartById(cartItem.getId());
        }
        orders.setTotal(total);
        orderRepository.save(orders);
    }

}
