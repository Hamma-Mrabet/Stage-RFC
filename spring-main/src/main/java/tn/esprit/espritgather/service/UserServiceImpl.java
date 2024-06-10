package tn.esprit.espritgather.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.enumeration.NiveauEtude;
import tn.esprit.espritgather.repo.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static tn.esprit.espritgather.control.EventRestController.uploadDirectory;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
    public User retrieveUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User addUser(User u) {
        return userRepository.save(u);
    }
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }
    public User modifyUser(User user) {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return userRepository.save(user);
    }

    @Override
    public User retrieveUserByMail(String mail) {
        return userRepository.findBymail(mail).orElse(null);
    }

    public User saveUser(User u, String imageFile) throws IOException {
        //if (!imageFile.isEmpty()) {
            //String fileName = saveImage(imageFile);
            //u.setImagePath(fileName);
            u.setImagePath(imageFile);

        //}
        return userRepository.save(u);
    }


    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + fileName;
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);
        return fileName;
    }

    public Map<NiveauEtude, Long> countUsersByNiveauClasse() {
        List<Object[]> counts = userRepository.countUsersByNiveauClasse();
        return counts.stream()
                .collect(Collectors.toMap(
                        count -> (NiveauEtude) count[0],
                        count -> (Long) count[1]
                ));
    }

    @Scheduled(cron = "0 0 0 1 9 *")
    //@Scheduled(cron = "*/10 * * * * *")
    public void updateUserGrades() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            NiveauEtude currentGrade = user.getNiveau();
            user.setNiveau(currentGrade.next());
            userRepository.save(user);
        }
    }

    @Override
    public User findBymail(String mail) {
        Optional<User> user = userRepository.findBymail(mail.trim().toLowerCase());
        if (user.isPresent()) {
            return user.get();
        } else {
            System.out.println("No user found with email: " + mail);
            return null;
        }
    }

    public Map<String, Long> countUsersByCreationMonth() {
        List<Object[]> counts = userRepository.countUsersByCreationMonth();
        return counts.stream()
                .collect(Collectors.toMap(
                        count -> Month.of((Integer) count[1]).name() + "-" + count[0].toString(),
                        count -> (Long) count[2]
                ));
    }

    public void createPasswordResetTokenForUser(final User user) {
        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        userRepository.save(user);
    }

    public Optional<User> findUserByPasswordResetToken(final String token) {
        return userRepository.findByPasswordResetToken(token);
    }

    public void changeUserPassword(final User user, final String password) {
        // encode the password and set it
        user.setPassword(passwordEncoder.encode(password));
        // clear the reset token
        user.setPasswordResetToken(null);
        userRepository.save(user);
    }
}