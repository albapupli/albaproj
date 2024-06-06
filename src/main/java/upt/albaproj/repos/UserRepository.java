package upt.albaproj.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upt.albaproj.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailOrPhoneNumber(String email, String phoneNumber);
}
