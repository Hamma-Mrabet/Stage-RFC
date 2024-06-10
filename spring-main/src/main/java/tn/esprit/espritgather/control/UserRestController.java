package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.enumeration.NiveauEtude;
import tn.esprit.espritgather.service.IUserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Gestion User")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserRestController {
    IUserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    // http://localhost:8089/espritgather/user/users
    @Operation(description = "récupérer toutes les utilisateurs de la base de données")
    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> listUsers = userService.retrieveAllUsers();
        return listUsers;
    }
    // http://localhost:8089/espritgather/user/retrieve-user/2
    @GetMapping("/retrieve-user/{user-id}")
    public User retrieveUser(@PathVariable("user-id") Long chId) {
        User user =userService.retrieveUser(chId);
        return user;
    }

    // Retrieve user by email
    @GetMapping("/retrieve-user-by-mail/{mail}")
    public User retrieveUserByEmail(@PathVariable("mail") String mail) {
        User user = userService.retrieveUserByMail(mail);
        return user;
    }


    // http://localhost:8089/espritgather/user/add-user
    @PostMapping("/users")
    public User adduser(@RequestBody User u) {
        User user = userService.addUser(u);
        return user;
    }
    // http://localhost:8089/espritgather/user/remove-user/{user-id}
    @DeleteMapping("/remove-user/{user-id}")
    public void removeUser(@PathVariable("user-id") Long chId) {
        userService.removeUser(chId);
    }

    // http://localhost:8089/espritgather/user/modify-user
    @PutMapping("/modify-user")
    public User modifyUser(@RequestBody User u) {
        User user = userService.modifyUser(u);
        return user;
    }


    @GetMapping("/count-by-niveau")
    public Map<NiveauEtude, Long> countUsersByNiveauClasse() {
        return userService.countUsersByNiveauClasse();
    }

    @GetMapping("/count-by-month")
    public Map<String, Long> countUsersByCreationMonth() {
        return userService.countUsersByCreationMonth();
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam String mail) {
        User user = userService.findBymail(mail);
        if (user == null) {
            // return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");

        }

        userService.createPasswordResetTokenForUser(user);

        // send email
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setTo(user.getMail());
        passwordResetEmail.setSubject("Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" +  user.getPasswordResetToken());

        javaMailSender.send(passwordResetEmail);

        //  return new ResponseEntity<>("Password reset link sent to your email", HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Password reset link sent to your email\"}");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<User> userOptional = userService.findUserByPasswordResetToken(token);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Invalid token\"}");

        }

        User user = userOptional.get();
        userService.changeUserPassword(user, newPassword);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Password changed successfully\"}");

    }




}
