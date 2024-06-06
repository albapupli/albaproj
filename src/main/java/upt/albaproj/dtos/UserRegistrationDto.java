package upt.albaproj.dtos;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String email;
    private String password;
    private String confirmPassword;
}