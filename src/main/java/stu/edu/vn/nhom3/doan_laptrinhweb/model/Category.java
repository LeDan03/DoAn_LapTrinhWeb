package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cate_id;

    @Column(columnDefinition = "varchar(100)",nullable = false)
    private String cate_name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {
    }

    public Category(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getCate_id() {
        return cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
