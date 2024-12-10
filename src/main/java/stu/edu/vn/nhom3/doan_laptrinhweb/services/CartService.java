package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Cart;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.CartRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Cart findCarById(int id) {
        return cartRepository.findById(id).get();
    }

    public List<Cart> getAllCart() {return cartRepository.findAll();}

    public Cart findCartByUserId(int userId) {
        for (Cart cart : cartRepository.findAll())
            if (cart.getUser().getId() == userId)
                return cart;
        return null;
    }
    public Cart addNewCart(int userId) {
        Cart cart = findCartByUserId(userId);
        if(cart == null)
        {
            cart = new Cart();
            cart.setTotalAmount(0);
            cart.setUser(userRepository.findById(userId).get());
            return cartRepository.save(cart);
        }
        return null;
    }
}
