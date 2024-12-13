package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column
    private int product_id;

    @ManyToOne
    @JsonBackReference("image-product")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}
