package stu.edu.vn.nhom3.doan_laptrinhweb.model;

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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "char(150)")
    private String code;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference("category-product")
    private List<Product> products;
}
