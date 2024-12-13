package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CartProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.OrderDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UpdateUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.CartProduct;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Order;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.ProductOrder;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductOrderSevice productOrderSevice;

    @PostMapping(value = "/loadCart")
    public List<CartProduct> getUserCartProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return cartProductService.getUserCartProducts(user.getId());
    }

    @PostMapping(value = "/addProduct/{product_id}")
    public ResponseEntity<Object> addNewProductIntoCart(@PathVariable("product_id") int product_id
            , @RequestBody CartProductDTO cartProductDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int userCartId = cartService.findCartByUserId(user.getId()).getId();
        CartProduct cartProduct = cartProductService.addNewProductIntoCart(product_id, cartProductDTO, userCartId);
        if(cartProduct == null) {
            cartProductService.updateProductInCart(product_id,cartProductDTO, userCartId);
            return ResponseEntity.ok().body("Updated successfully");
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
            return ResponseEntity.ok().body("Added successfully");
        }
        return ResponseEntity.ok().body(cartProduct);
    }

    @PutMapping(value = "/updateAccount")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDTO userDTO, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        String token = request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer "))
        {
            token = token.substring(7);
            try{
                String emailFromToken = jwtService.extractUsername(token);
                if(emailFromToken.equals(authenticatedUser.getEmail()) && emailFromToken.equals(userDTO.getEmail()))
                    return userService.updateUser(emailFromToken, userDTO);
                else
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }catch (Exception e)
            {
                System.out.println("Error extracting username from token: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @Transactional
    @GetMapping(value = "/getAllOrder/{customer_id}")
    public ResponseEntity<List<Order>> getAllUserOrders(@PathVariable("customer_id") int customer_id)
    {
        User user = userService.findUserById(customer_id);
        if(user==null)
            return ResponseEntity.badRequest().body(null);
        List<Order> orders = orderService.getAllUserOrders(user.getId());
        if(orders == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        else
            return ResponseEntity.ok().body(orders);
    }

    @PostMapping(value = "/createNewOrder")
    public ResponseEntity<Order> createNewOrder(@RequestBody OrderDTO orderDTO)
    {
        Order order = new Order();
        order = orderService.createOrder(orderDTO);
        Order result = orderService.save(order);

        for (ProductOrder productOrder : order.getProductOrders()) {
            productOrder.setOrderId(result.getId());
        }
        productOrderSevice.saveAll(order.getProductOrders());

        return ResponseEntity.ok().body(result);
    }
}
