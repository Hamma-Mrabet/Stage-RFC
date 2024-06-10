package tn.esprit.espritgather.control;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.espritgather.dto.HelloResponse;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public HelloResponse hello() {

        return new HelloResponse("Hello from Authorized API request.");
    }
}
