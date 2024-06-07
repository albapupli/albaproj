package upt.albaproj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import upt.albaproj.dtos.UserProfileDto;
import upt.albaproj.dtos.UserRegistrationDto;
import upt.albaproj.entities.User;
import upt.albaproj.repos.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "uploads/";


    public void save(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setMiddleName(registrationDto.getMiddleName());
        user.setLastName(registrationDto.getLastName());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setDateOfBirth(registrationDto.getDateOfBirth());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        userRepository.save(user);
    }

    public UserProfileDto getUserProfile(String emailOrPhoneNumber) {
        User user = userRepository.findByEmailOrPhoneNumber(emailOrPhoneNumber,emailOrPhoneNumber);
        return mapToUserProfileDto(user);
    }

    public UserProfileDto getUserProfileById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserProfileDto(user);
    }

    public void updateUserProfile(UserProfileDto userProfileDto, MultipartFile profilePhoto) {
        User user = userRepository.findById(userProfileDto.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userProfileDto.getFirstName());
        user.setMiddleName(userProfileDto.getMiddleName());
        user.setLastName(userProfileDto.getLastName());
        user.setPhoneNumber(userProfileDto.getPhoneNumber());
        user.setDateOfBirth(userProfileDto.getDateOfBirth());
        user.setEmail(userProfileDto.getEmail());

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String photoPath = saveProfilePhoto(profilePhoto);
            user.setProfilePhotoPath(photoPath);
        }

        userRepository.save(user);
    }

    public void removeProfilePhoto(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePhotoPath(null);
        userRepository.save(user);
    }

    public List<UserProfileDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserProfileDto).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private UserProfileDto mapToUserProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setMiddleName(user.getMiddleName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setEmail(user.getEmail());
        dto.setProfilePhotoPath(user.getProfilePhotoPath());
        return dto;
    }

    private String saveProfilePhoto(MultipartFile profilePhoto) {
        try {
            if (!Files.exists(Paths.get(UPLOAD_DIR))) {
                Files.createDirectories(Paths.get(UPLOAD_DIR));
            }
            String originalFilename = profilePhoto.getOriginalFilename();
            String newFilename = UUID.randomUUID() + "_" + originalFilename;
            Path filePath = Paths.get(UPLOAD_DIR + newFilename);
            Files.write(filePath, profilePhoto.getBytes());
            return newFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile photo", e);
        }
    }
}