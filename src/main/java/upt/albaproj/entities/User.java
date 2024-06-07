package upt.albaproj.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String email;
    private String password;
    private String profilePhotoPath;
    private boolean isAdmin = false;
}
