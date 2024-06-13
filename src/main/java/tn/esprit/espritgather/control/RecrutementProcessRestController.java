package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.config.CloudinaryService;
import tn.esprit.espritgather.entity.*;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;
import tn.esprit.espritgather.service.IRecrutementService;
import tn.esprit.espritgather.service.IProcessRecrutementService;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.*;


import tn.esprit.espritgather.entity.Recrutement;
import tn.esprit.espritgather.service.IProcessRecrutementService;
import tn.esprit.espritgather.service.IUserService;

import java.util.List;

@Tag(name = "Gestion Process")
@RestController
@AllArgsConstructor
@RequestMapping("/process")
@CrossOrigin(origins = "*")

public class RecrutementProcessRestController {
    IProcessRecrutementService processService;
    IRecrutementService recrutementService;
    IUserService userService;
    CloudinaryService cloudinaryService;


    // http://localhost:8089/espritgather/process/retrieve-all-processes
    @Operation(description = "récupérer toutes les process de la base de données")
    @GetMapping("/retrieve-all-processes")
    public List<ProcessRecrutement> getProcesss() {
        List<ProcessRecrutement> listProcesses = processService.retrieveAllProcesses();
        return listProcesses;
    }
    // http://localhost:8089/espritgather/process/retrieve-process/retrieve-process/{process-id}
    @GetMapping("/retrieve-process/{process-id}")
    public ProcessRecrutement retrieveProcess(@PathVariable("process-id") Long pId) {
        ProcessRecrutement process = processService.retrieveProcess(pId);
        return process;
    }
    // http://localhost:8089/espritgather/process/add-process
    @PostMapping("/add-process")
    public ProcessRecrutement addProcess(@RequestBody ProcessRecrutement p) {
        ProcessRecrutement process = processService.addProcess(p);
        return process;
    }
    // http://localhost:8089/espritgather/process/remove-process/{process-id}
    @DeleteMapping("/remove-process/{process-id}")
    public void removeProcess(@PathVariable("process-id") Long processId) {
        processService.removeProcess(processId);
    }
    // http://localhost:8089/espritgather/process/modify-process
    @PutMapping("/modify-process")
    public ProcessRecrutement modifyProcess(@RequestBody ProcessRecrutement c) {


        ProcessRecrutement process = processService.modifyProcess(c);
        return process;
    }
    @GetMapping("/retrieve-processes-by-recrutement/{recrutement-id}")
    public List<ProcessRecrutement> retrieveProcessesByRecrutement(@PathVariable("recrutement-id") Long idRecrutement) {
        List<ProcessRecrutement> processes = processService.retrieveProcessesByRecrutement(idRecrutement);
        return processes;
    }

    @PostMapping("/add-process-by-recrutement/{recrutement-id}")
    public ProcessRecrutement addProcessByRecrutement(@RequestBody ProcessRecrutement process, @PathVariable("recrutement-id") Long idRecrutement) {
        Recrutement recrutement = recrutementService.retrieveRecrutement(idRecrutement);

        process.setRecrutement(recrutement); // Associer le recrutement au processus de recrutement
        processService.compareSkillsAndApprove(recrutement, process);
        ProcessRecrutement addedProcess = processService.addProcess(process);
        processService.approveProcess(idRecrutement);

        return addedProcess;
    }
    @GetMapping("/approved")
    public Long getApprovedProcessCount() {
        return processService.countApprovedProcesses();
    }

    @GetMapping("/non-approved")
    public Long getNonApprovedProcessCount() {
        return processService.countNonApprovedProcesses();
    }

    @GetMapping("/skill-selection-percentage")
    public ResponseEntity<Map<Skill, Double>> getSkillSelectionPercentage() {
        Map<Skill, Double> skillPercentages = processService.calculateSkillSelectionPercentageIncludingUnapproved();
        return ResponseEntity.ok(skillPercentages);
    }



   /* @PostMapping("/add-process-by-rec-user/{user-id}/{rec-id}")
    public ProcessRecrutement addTicketByEventAndUser(@PathVariable("user-id") Long userId, @ModelAttribute ProcessRecrutement pr, @PathVariable("rec-id") Long recId,@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Recrutement r = recrutementService.retrieveRecrutement(recId);
        String imagePath = cloudinaryService.uploadFile(imageFile);

        User user = userService.retrieveUser(userId);

        pr.setUser(user);

        pr.setRecrutement(r);
        processService.compareSkillsAndApprove(r, pr);
        ProcessRecrutement addedProcess = processService.addProcess(pr);
        processService.approveProcess(recId);

        processService.saveProcess(pr,imagePath);

        return addedProcess;
    }*/
   @PostMapping("/add-process-by-rec-user/{user-id}/{rec-id}")
   public ProcessRecrutement addTicketByEventAndUser(@PathVariable("user-id") Long userId,
                                                     @ModelAttribute ProcessRecrutement pr,
                                                     @PathVariable("rec-id") Long recId,
                                                     @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
       Recrutement r = recrutementService.retrieveRecrutement(recId);
       String imagePath = cloudinaryService.uploadFile(imageFile);
       User user = userService.retrieveUser(userId);
       pr.setUser(user);
       pr.setRecrutement(r);

       boolean isApproved = processService.compareSkillsAndApprove(r, pr);
       pr.setApproved(isApproved);

       ProcessRecrutement addedProcess = processService.saveProcess(pr, imagePath);
       processService.approveProcess(pr.getIdProcessRecrutement());
       pr.setRecrutement(r);
       return addedProcess;

   }

    @GetMapping("/retrieve-process-by-rec-and-user/{user-id}/{rec-id}")
    public List<ProcessRecrutement> retrieveTicketsByEventAndUser(@PathVariable("user-id") Long recId,@PathVariable("rec-id") Long userId) {
        List<ProcessRecrutement> a = processService.retrieveProcesssByRecAndUser(userId,recId);
        return a;
    }

}
