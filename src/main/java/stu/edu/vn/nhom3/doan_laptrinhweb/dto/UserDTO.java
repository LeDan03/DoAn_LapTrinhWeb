package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private int id;
    private String name;
    private String password;
    private String email;
    private int role_id;
    private boolean status;
}
