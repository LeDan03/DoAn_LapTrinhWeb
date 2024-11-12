package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Column(columnDefinition = "varchar(200)")
    private String passwordHash;

    @Column()
    private Date createBy;

    @Column()
    private Date updateDate;

    @Column(columnDefinition = "boolean default true")
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
