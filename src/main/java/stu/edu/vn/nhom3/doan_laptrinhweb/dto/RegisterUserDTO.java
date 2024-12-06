package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterUserDTO {
    private int id;
    private String fullName;
    private String password;
    private String email;
    private int role_id;
    private boolean status;

}
