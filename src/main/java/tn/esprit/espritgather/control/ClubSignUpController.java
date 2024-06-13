package tn.esprit.espritgather.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.config.CloudinaryService;
import tn.esprit.espritgather.dto.SignupRequest;
import tn.esprit.espritgather.enumeration.Role;
import tn.esprit.espritgather.service.AuthService;

import java.io.IOException;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/signup/club")
public class ClubSignUpController {
    private final AuthService authService ;
    //  public static String uploadDirectory = "C:/Users/Admin/angular/src/assets/images/";
    public static String uploadDirectory = "C:/Users/ameni/OneDrive/Bureau/angular/src/assets/images/";
    CloudinaryService cloudinaryService;

    public ClubSignUpController(AuthService authService) {
        this.authService = authService;
        this.cloudinaryService = new CloudinaryService();
    }

    @PostMapping
    public ResponseEntity<String> signupUser(SignupRequest signupRequest,@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        signupRequest.setRole(Role.club);
        String imagePath = cloudinaryService.uploadImage(imageFile);

        boolean isUserCreated = authService.createUser(signupRequest,imagePath);
        if (isUserCreated) {


            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"User created successfully\"}");
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Failed to create user\"}");
        }

    }

}
