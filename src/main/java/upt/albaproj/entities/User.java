package upt.albaproj.entities;

import jakarta.persistence.*;
import lombok.Data;
import upt.albaproj.enums.Experience;
import upt.albaproj.enums.Skill;

import java.util.HashSet;
import java.util.Set;


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
    private boolean developer = false;
    private boolean isAdmin = false;
    @Column(name = "experience")
    private Experience experience;

    @Column(name = "skills")
    private Skill skills;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();
}
