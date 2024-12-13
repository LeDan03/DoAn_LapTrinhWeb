package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductOrderId implements Serializable {

    private int productId;
    private long orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrderId that = (ProductOrderId) o;
        return productId == that.productId && orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
