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
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/signup")
public class SignupController {


    private final AuthService authService ;
    // public static String uploadDirectory = "C:/Users/Admin/angular/src/assets/images/";
    public static String uploadDirectory = "C:/Users/ameni/OneDrive/Bureau/angular/src/assets/images/";
    CloudinaryService cloudinaryService;

    @Autowired
    public SignupController(AuthService authService) {
this.cloudinaryService = new CloudinaryService();
    this.authService = authService;
    }
    @PostMapping
    public ResponseEntity<String> signupUser(@ModelAttribute SignupRequest signupRequest, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        signupRequest.setRole(Role.user);

        if (cloudinaryService != null) {
            String imagePath = cloudinaryService.uploadImage(imageFile);
            if (imagePath != null) {
                boolean isUserCreated = authService.createUser(signupRequest, imagePath);
                if (isUserCreated) {
                    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"User created successfully\"}");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Failed to create user\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Failed to upload image\"}");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Cloudinary service is not initialized\"}");
        }
    }





}


