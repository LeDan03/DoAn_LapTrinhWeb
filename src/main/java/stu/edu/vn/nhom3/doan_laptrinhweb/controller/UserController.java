package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CartProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.CartProduct;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.CartProductService;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private CartService cartService;

    @PostMapping(value = "/loadCart")
    public List<CartProduct> getUserCartProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return cartProductService.getUserCartProducts(user.getId());
    }

    @PostMapping(value = "/addProduct/{product_id}")
    public ResponseEntity<Object> addNewProductIntoCart(@PathVariable("product_id") int product_id, @RequestBody CartProductDTO cartProductDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int userCartId = cartService.findCartByUserId(user.getId()).getId();
        CartProduct cartProduct = cartProductService.addNewProductIntoCart(product_id, cartProductDTO, userCartId);
        if(cartProduct == null) {
            cartProductService.updateProductInCart(product_id,cartProductDTO, userCartId);
            return ResponseEntity.ok().body("The product was existed in your cart, updated successfully");
        }
        return ResponseEntity.ok().body(cartProduct);
    }
    @DeleteMapping(value = "/removeProduct/{product_id}")
    public ResponseEntity<Object> removeProductFromCart(@PathVariable("product_id") int id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int userCartId = cartService.findCartByUserId(user.getId()).getId();
        cartProductService.removeProductFromCart(id, userCartId);
        return ResponseEntity.ok().body("The product has been successfully removed from your cart");
    }
    @PutMapping(value = "/updateProduct/{product_id}")
    public ResponseEntity<Object> updateProductFromCart(@PathVariable("product_id") int id, @RequestBody CartProductDTO cartProductDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int userCartId = cartService.findCartByUserId(user.getId()).getId();
        CartProduct cartProduct = cartProductService.updateProductInCart(id,cartProductDTO, userCartId);
        if(cartProduct==null)
        {
            cartProductService.addNewProductIntoCart(id,cartProductDTO,userCartId);
            return ResponseEntity.ok().body("The product is not existed, added successfully");
        }
        return ResponseEntity.ok().body(cartProduct);
    }
}
