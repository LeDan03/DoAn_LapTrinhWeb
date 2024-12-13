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
@IdClass(ProductOrderId.class)
public class ProductOrder implements Serializable {

    @ManyToOne
    @JsonBackReference("product-productOrder")
    @JoinColumn(name = "product_id",insertable = false, updatable = false, nullable = false)
    private Product product;

    @ManyToOne
    @JsonBackReference("order-productOrder")
    @JoinColumn(name = "order_id",insertable = false, updatable = false, nullable = false)
    private Order order;

    @Column
    private int quantity;

    @Id
    @Column(name = "product_id", insertable = false, updatable = false)
    private int productId;

    @Id
    @Column(name = "order_id", insertable = false, updatable = false)
    private long orderId;

    @PostLoad
    private void populateTransientIds() {
        if (this.order != null) {
            this.orderId = this.order.getId();
        }
        if (this.product != null) {
            this.productId = this.product.getId();
        }
    }
}
