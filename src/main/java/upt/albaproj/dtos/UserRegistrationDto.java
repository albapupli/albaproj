package upt.albaproj.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "First Name is mandatory")
    @Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "First Name cannot contain numbers")
    private String firstName;

    @NotBlank(message = "Middle Name is mandatory")
    @Size(min = 3, max = 50, message = "Middle Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "Middle Name cannot contain numbers")
    private String middleName;

    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[^0-9]*$", message = "Last Name cannot contain numbers")
    private String lastName;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+355[7-9]{7,25}$", message = "Phone number is invalid")
    private String phoneNumber;

    private String dateOfBirth;

    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String confirmPassword;

    private String experience;
    private boolean isDeveloper;


    @NotBlank(message = "Skills is mandatory")
    private String skills;
}