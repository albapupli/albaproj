package upt.albaproj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
import upt.albaproj.dtos.UserProfileDto;
import upt.albaproj.dtos.UserRegistrationDto;
import upt.albaproj.entities.User;
import upt.albaproj.enums.Experience;
import upt.albaproj.enums.Skill;
import upt.albaproj.repos.UserRepository;

import javax.swing.text.Document;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/*import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;*/

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
      //  user.setDeveloper(registrationDto.isDeveloper());
   //     user.setSkills(Skill.valueOf(registrationDto.getSkills()));
    //    user.setExperience(Experience.valueOf(registrationDto.getExperience()));

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
        user.setDeveloper(userProfileDto.isDeveloper());
        user.setSkills(Skill.valueOf(userProfileDto.getSkills()));
        user.setExperience(Experience.valueOf(userProfileDto.getExperience()));

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
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserProfileDto).collect(Collectors.toList());
    }

    public List<UserProfileDto> getDevsByName(String name) {
        return userRepository.findAll().stream()
                .filter(dev -> (dev.isDeveloper() && dev.getFirstName().equals(name)) )
                .map(this::mapToUserProfileDto).collect(Collectors.toList());
    }

    public List<UserProfileDto> getDevsBySkill(String skill) {
        return userRepository.findAll().stream()
                .filter(dev -> (dev.isDeveloper() && dev.getSkills().equals(skill)) )
                .map(this::mapToUserProfileDto).collect(Collectors.toList());
    }

    public List<UserProfileDto> getDevsByExperience(String experience) {
        return userRepository.findAll().stream()
                .filter(dev -> (dev.isDeveloper() && dev.getExperience().equals(experience)) )
                .map(this::mapToUserProfileDto).collect(Collectors.toList());
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
        dto.setDeveloper(user.isDeveloper());
        dto.setSkills(String.valueOf(user.getSkills()));
        dto.setExperience(String.valueOf(user.getExperience()));
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


    public User addUser(UserRegistrationDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set other fields as necessary
        return userRepository.save(user);
    }
/*
    public ByteArrayInputStream generateUserProfilePdf(Long userId) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("User Profile");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Name: " + user.getName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 600);
                contentStream.showText("Projects:");
                contentStream.endText();

                int yOffset = 580;
                for (Project project : user.getProjects()) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(120, yOffset);
                    contentStream.showText("- " + project.getName());
                    contentStream.endText();
                    yOffset -= 20;
                }
            }

            document.save(out);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }*/
}
