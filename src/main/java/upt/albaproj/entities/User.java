package upt.albaproj.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String phoneNumber;
    private String password;
}
