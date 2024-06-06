package upt.albaproj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upt.albaproj.entities.User;
import upt.albaproj.repos.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String emailOrPhone, String password) {
        User user = userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
