package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    @Column
    private String role_name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role( String role_name) {
        this.role_name = role_name;
    }
    public Role() {}

    public int getRole_id() {
        return role_id;
    }


    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
