package tn.esprit.espritgather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tn.esprit.espritgather.control.EventRestController;

import java.io.File;
@EnableScheduling
@SpringBootApplication
public class EspritGatherApplication {

    public static void main(String[] args) {

        new File(EventRestController.uploadDirectory).mkdir();
        SpringApplication.run(EspritGatherApplication.class, args);
    }

}
