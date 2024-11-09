package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,columnDefinition = "varchar(100)")
    private String name;

    @Column(nullable = false,columnDefinition = "varchar(100)")
    private String email;

    @Column(nullable = false,columnDefinition = "varchar(200)")
    private String us_passwordHash;

    @Column(nullable = false)
    private Date createBy;

    @Column(nullable = false)
    private Date updateDate;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private boolean status;

    @Column
    private int role_id;

    @OneToMany(mappedBy = "us")
    private List<Orders> orders;

    @OneToMany(mappedBy = "user")
    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    private Role role;

    public User(String us_name, String us_email, String us_passwordHash, Date createBy, Date updateDate, boolean status, int role_id) {
        this.name = us_name;
        this.email = us_email;
        this.us_passwordHash = us_passwordHash;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.status = status;
        this.role_id = role_id;
    }
    public User() {}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getUs_id() {
        return id;
    }

    public String getUs_name() {
        return name;
    }

    public void setUs_name(String us_name) {
        this.name = us_name;
    }

    public String getUs_email() {
        return email;
    }

    public void setUs_email(String us_email) {
        this.email = us_email;
    }

    public String getUs_passwordHash() {
        return us_passwordHash;
    }

    public void setUs_passwordHash(String us_passwordHash) {
        this.us_passwordHash = us_passwordHash;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", us_passwordHash='" + us_passwordHash + '\'' +
                ", createBy=" + createBy +
                ", updateDate=" + updateDate +
                ", status=" + status +
                ", role_id=" + role_id +
                ", orders=" + orders +
                ", comment=" + comment +
                ", role=" + role +
                '}';
    }
}
