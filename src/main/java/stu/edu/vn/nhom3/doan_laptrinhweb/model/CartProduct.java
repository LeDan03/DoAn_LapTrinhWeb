package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
@IdClass(CartProductId.class)
public class CartProduct implements Serializable {
    //tuần tự hóa (convert thành stream byte)

    //Sử dụng composite khóa chính
    @ManyToOne
    @JsonBackReference("cart-cartProduct")
    @JoinColumn(name = "cart_id", nullable = false,insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne
    @JsonBackReference("cartProduct-product")
    @JoinColumn(name = "product_id", nullable = false,insertable = false, updatable = false)
    private Product product;

    @Column
    private int quantity;

    @Column
    private double total;

    @Id
    @Column(name = "cart_id", insertable = false, updatable = false)
    private int cartId;

    @Id
    @Column(name = "product_id", insertable = false, updatable = false)
    private int productId;

    @PostLoad
    private void populateTransientIds() {
        if (this.cart != null) {
            this.cartId = this.cart.getId();
        }
        if (this.product != null) {
            this.productId = this.product.getId();
        }
    }//tự động load
}
