package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

//    @OneToMany(mappedBy = "us")
//    private List<Orders> orders;
//
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    private Role role;

}
