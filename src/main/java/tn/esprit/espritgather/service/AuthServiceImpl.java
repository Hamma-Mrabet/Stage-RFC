package tn.esprit.espritgather.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.dto.SignupRequest;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.UserRepository;
import tn.esprit.espritgather.utils.JWTUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;


import static tn.esprit.espritgather.control.SignupController.uploadDirectory;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
/*
    @Override
    public boolean createUser(SignupRequest signupRequest)  {
        User user = new User();
        BeanUtils.copyProperties(signupRequest,user);
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashPassword);
        User createdUser = userRepository.save(user);
        return true;
    }
*/
    public boolean createUser(SignupRequest signupRequest,String imageFile) throws IOException {
        Optional<User> existingUser = userRepository.findBymail(signupRequest.getMail());
        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setImagePath(imageFile);

        //if (!imageFile.isEmpty()) {
            //String fileName = saveImage(imageFile);
        //}
        BeanUtils.copyProperties(signupRequest,user);
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashPassword);
        User createdUser = userRepository.save(user);
        return true;
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + fileName;
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);
        return fileName;
    }











}