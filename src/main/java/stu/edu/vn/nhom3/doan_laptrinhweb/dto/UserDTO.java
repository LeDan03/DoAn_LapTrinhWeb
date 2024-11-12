package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int id;
    private String name;
    private String email;
    private String password;
    private Date createBy;
    private Date updateBy;
    private boolean status;
    private int role_id;
}
