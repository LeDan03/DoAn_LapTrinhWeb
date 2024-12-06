package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UpdateUserDTO {

    private String newFullName;
    private String newPassword;
    private String email;
}
