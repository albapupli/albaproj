package upt.albaproj.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import upt.albaproj.entities.Project;
import upt.albaproj.entities.User;
import upt.albaproj.repos.ProjectRepository;
import upt.albaproj.repos.UserRepository;
import upt.albaproj.services.UserService;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User testUser = new User();
            testUser.setId(1L);
            testUser.setFirstName("Alba");
            testUser.setMiddleName("");
            testUser.setLastName("Pupli");
            testUser.setPhoneNumber("+355692020200");
            testUser.setDateOfBirth("10.10.2002");
            testUser.setEmail("alba@gmail.com");
            testUser.setPassword(passwordEncoder.encode("alba"));
            testUser.setAdmin(true);
            userRepository.save(testUser);
        };
    }
/*
    @Bean
    public CommandLineRunner init(ProjectRepository projectRepository) {
        return args -> {

            Project project1 = new Project();
            project1.setName("Project A");

            Project project2 = new Project();
            project2.setName("Project B");


            projectRepository.save(project1);
            projectRepository.save(project2);
        };
    }*/
}