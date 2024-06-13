package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import tn.esprit.espritgather.enumeration.Equip;
import tn.esprit.espritgather.enumeration.Metric;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Equipement;
import tn.esprit.espritgather.service.IEquipementService;

import java.util.List;
import java.util.Map;

@Tag(name = "Gestion equipement")
@RestController
@AllArgsConstructor
@RequestMapping("/equipement")
@CrossOrigin(origins = "*")
public class EquipementRestController {

    IEquipementService equipementService;

    // http://localhost:8089/espritgather/equipement/equipements
    @Operation(description = "récupération de tout les equipements")
    @GetMapping("/equipements")
    public List<Equipement> getEquipements() {
        List <Equipement> equipements = equipementService.retrieveAllEquipments();
        return equipements ;
    }

    // http://localhost:8089/espritgather/equipement/statistics
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Double>> calculateEquipmentStatistics()
    {
        Map<String, Double> statistics = equipementService.calculateEquipmentStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/statistics-metricOnly")
    public ResponseEntity<Map<String, Double>> getEquipmentStatistics(
            @RequestParam(required = false) Metric metric,
            @RequestParam(required = false) Equip equip) {
        Map<String, Double> statistics = equipementService.getEquipmentStatistics(metric, equip);
        return ResponseEntity.ok(statistics);
    }
    


    // http://localhost:8089/espritgather/equipement/retrieve-equipement/8
    @GetMapping("/retrieve-equipement/{equipement-id}")
    public Equipement retrieveEquipement(@PathVariable("equipement-id") Long chId) {
        return equipementService.retrieveEquipement(chId);
    }
    // http://localhost:8089/espritgather/equipement/retrieve-equipementByEvent/8
    @GetMapping("/retrieve-equipementByEvent/{event-id}")
    public List <Equipement> retrieveEquipementByEvent(@PathVariable("event-id") Long chId) {
        return equipementService.retrieveEquipementByEvent(chId);
    }


    // http://localhost:8089/espritgather/equipement/retrieve-equipementByClub/8
    @GetMapping("/retrieve-equipementByClub/{club-id}")
    public List <Equipement> retrieveEquipementByClub(@PathVariable("club-id") Long chId) {
        return equipementService.retrieveEquipementByClub(chId);
    }



    // http://localhost:8089/espritgather/equipement/getPriceByClub/8
    @GetMapping("/getPriceByClub/{club-id}")
    public float getPriceByClub(@PathVariable("club-id") Long chId) {
        return equipementService.getPriceByClub(chId);
    }

    // http://localhost:8089/espritgather/equipement/getPriceByEvent/8
    @GetMapping("/getPriceByEvent/{club-id}")
    public float getPriceByEvent(@PathVariable("club-id") Long chId) {
        return equipementService.getPriceByEvent(chId);
    }


    // http://localhost:8089/espritgather/equipement/add-equipement
    @PostMapping("/add-equipement")
    public Equipement addEquipement(@RequestBody Equipement c) {
        return equipementService.addEquipement(c);
    }



    // http://localhost:8089/espritgather/equipement/remove-equipement/{equipement-id}
    @DeleteMapping("/remove-equipement/{equipement-id}")
    public void removeEquipement(@PathVariable("equipement-id") Long chId) {
        equipementService.removeEquipement(chId);
    }


    // http://localhost:8089/espritgather/equipement/add-equipement-admin
    @PutMapping("/add-equipement-admin")
    public Equipement modifyEquipementAdmin(@RequestBody Equipement c) {
        return equipementService.addEquipementAdmin(c);
    }




    // http://localhost:8089/espritgather/equipement/modify-equipement
    @PutMapping("/modify-equipement")
    public Equipement modifyEquipement(@RequestBody Equipement c) {
        return equipementService.modifyEquipement(c);
    }




}
