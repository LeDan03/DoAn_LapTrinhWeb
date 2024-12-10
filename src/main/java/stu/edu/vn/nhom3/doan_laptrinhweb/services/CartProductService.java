package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CartProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.CartProduct;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.CartProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;



    public CartProduct getCartProductById(int cartId, int productId) {
        return cartProductRepository.findByCartIdAndProductId(cartId,productId).get();
    }
    public List<CartProduct> getCartProducts() {
        return cartProductRepository.findAll();
    }
    public List<CartProduct> getUserCartProducts(int userId) {
        int cartId = cartService.findCartByUserId(userId).getId();
        List<CartProduct> userCartProducts = new ArrayList<CartProduct>();
        for (CartProduct cartProduct : cartProductRepository.findAll()) {
            if(cartProduct.getCartId() == cartId)
                userCartProducts.add(cartProduct);
        }
        return userCartProducts;
    }

    public CartProduct addNewProductIntoCart(int productId, CartProductDTO cartProductDTO, int cartId) {
        CartProduct cartProduct = getCartProductById(productId, cartId);
        if(cartProduct == null){
            cartProduct.setProductId(productId);
            cartProduct.setQuantity(cartProductDTO.getQuantity());
            cartProduct.setTotal(cartProductDTO.getTotal());
            cartProduct.setCartId(cartId);
            return cartProductRepository.save(cartProduct);
        }
        return null;
    }
    public void removeProductFromCart(int productId, int cartId) {
        CartProduct cartProduct = getCartProductById(productId, cartId);
        if(cartProduct != null)
            cartProductRepository.delete(cartProduct);
    }
    public CartProduct updateProductInCart(int productId, CartProductDTO cartProductDTO, int cartId) {
        CartProduct cartProduct = getCartProductById(productId, cartId);
        if(cartProduct == null)
            return null;
        cartProduct.setQuantity(cartProductDTO.getQuantity());
        cartProduct.setTotal(cartProductDTO.getTotal());
        cartProduct.setCartId(cartId);
        cartProduct.setProductId(productId);
        return cartProductRepository.save(cartProduct);
    }
}
