package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private String theme;

    @Column
    private String unit;
    //cate-id

    @ManyToOne
    @JsonBackReference("category-product")
    @JoinColumn(name = "cate_id")
    private Category category;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    @JsonManagedReference("image-product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference("cartProduct-product")
    private List<CartProduct> cartProducts;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference("product-productOrder")
    private List<ProductOrder> productOrders;
}
