package stu.edu.vn.nhom3.doan_laptrinhweb.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private int id;
    private String email;
    private String fullname;
    private int roleId;
    private String token;
    private long expiresIn;
    private boolean status;
}
