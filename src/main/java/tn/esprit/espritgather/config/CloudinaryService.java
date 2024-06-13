package tn.esprit.espritgather.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static tn.esprit.espritgather.control.EventRestController.uploadDirectory;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dk6njrk6l",
                "api_key", "163189432499816",
                "api_secret", "J9z8lpAMwECOoQTLVm_A1-NmkFo"));
    }

    public String uploadImage(MultipartFile ImageFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(ImageFile.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");


        return imageUrl;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "resource_type", "image",
                "format", "png" // Convertir en PNG
        ));
        String imageUrl = (String) uploadResult.get("url");
        return imageUrl;
    }




}