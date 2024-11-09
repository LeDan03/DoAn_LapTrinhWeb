package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmt_id;

    @Column(nullable = false,columnDefinition = "text")
    private String cmt_description;

    @Column(nullable = false,columnDefinition = "varchar(100)")
    private String prod_id;

    @Column
    private int user_id;

    @ManyToOne
    @JoinColumn(name="user_id",insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="prod_id",insertable = false, updatable = false)
    private Product product;

    public Comment(String cmt_description, String prod_id, int user_id) {
        this.cmt_description = cmt_description;
        this.prod_id = prod_id;
        this.user_id = user_id;
    }
    public Comment() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCmt_id() {
        return cmt_id;
    }

    public String getCmt_description() {
        return cmt_description;
    }

    public void setCmt_description(String cmt_description) {
        this.cmt_description = cmt_description;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
