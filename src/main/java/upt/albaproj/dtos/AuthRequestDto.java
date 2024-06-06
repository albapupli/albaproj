package upt.albaproj.dtos;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String emailOrPhone;
    private String password;
}
