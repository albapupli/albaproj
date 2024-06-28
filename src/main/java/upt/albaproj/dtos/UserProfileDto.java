package upt.albaproj.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfileDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String email;
    private MultipartFile profilePhoto;
    private String experience;
    private String skills;
    private String profilePhotoPath;
    private boolean developer;
}
