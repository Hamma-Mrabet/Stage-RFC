package tn.esprit.espritgather.control;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Recrutement;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.service.IRecrutementService;
import tn.esprit.espritgather.service.IUserService;

import java.io.IOException;
import java.util.List;

@Tag(name = "Gestion Recrutement")
@RestController
@AllArgsConstructor
@RequestMapping("/recrutement")
@CrossOrigin(origins = "*")
public class RecrutementRestController {
    IRecrutementService recrutementService;
    IUserService userService;
    // http://localhost:8089/espritgather/recrutement/retrieve-all-recrutements
    @Operation(description = "récupérer toutes les recrutement de la base de données")
    @GetMapping("/retrieve-all-recrutements")
    public List<Recrutement> getrecrutements() {
        List<Recrutement> listRecrutements = recrutementService.retrieveAllRecrutements();
        return listRecrutements;
    }
    // http://localhost:8089/espritgather/recrutement/retrieve-recrutement/8
    @GetMapping("/retrieve-recrutement/{recrutement-id}")
    public Recrutement retrieverecrutement(@PathVariable("recrutement-id") Long rId) {
        Recrutement recrutement = recrutementService.retrieveRecrutement(rId);
        return recrutement;
    }
    // http://localhost:8089/espritgather/recrutement/add-recrutement
    @PostMapping("/add-recrutement")
    public Recrutement addrecrutement(@RequestBody Recrutement r) {
        Recrutement recrutement = recrutementService.addRecrutement(r);

        return recrutement;
    }

    @PostMapping("/add-rec-by-user/{user-id}")
    public Recrutement addRecByUser(@PathVariable("user-id") Long userId, @RequestBody Recrutement r) {

        User user = userService.retrieveUser(userId);
        r.setUser(user);
        Recrutement savedEvent = recrutementService.addRecrutement(r);
        return  savedEvent;

    }
    // http://localhost:8089/espritgather/recrutement/remove-recrutement/{recrutement-id}
    @DeleteMapping("/remove-recrutement/{recrutement-id}")
    public void removerecrutement(@PathVariable("recrutement-id") Long rId) {

        recrutementService.removeRecrutement(rId);

    }
    // http://localhost:8089/espritgather/recrutement/modify-recrutement
    @PutMapping("/modify-recrutement")
    public Recrutement modifyrecrutement(@RequestBody Recrutement r) {
        Recrutement recrutement = recrutementService.modifyRecrutement(r);
        return recrutement;
    }






    @GetMapping("/retrieve-recs-by-user/{user-id}")
    public List<Recrutement> retrieverecsByUser(@PathVariable("user-id") Long userId) {
        recrutementService.updateRecArchive();

        List<Recrutement> recs = recrutementService.retrieverecsByUser(userId);
        return recs;
    }


}
