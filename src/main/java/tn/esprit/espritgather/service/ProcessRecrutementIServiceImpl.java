package tn.esprit.espritgather.service;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Recrutement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.ProcessRecrutement;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;
import tn.esprit.espritgather.repo.ProcessNotFoundException;
import tn.esprit.espritgather.repo.ProcessRecrutementRepository;
import tn.esprit.espritgather.repo.RecrutementRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor

public class ProcessRecrutementIServiceImpl implements IProcessRecrutementService {
    ProcessRecrutementRepository processRecrutementRepository;
    RecrutementRepository recrutementRepository;
    public List<ProcessRecrutement> retrieveAllProcesses() {
        return processRecrutementRepository.findAll();
    }

    public ProcessRecrutement retrieveProcess(Long idProcessRecrutement) {
        return processRecrutementRepository.findById(idProcessRecrutement).get();
    }

    public ProcessRecrutement addProcess(ProcessRecrutement process) {

        return processRecrutementRepository.save(process);

    }

    public void removeProcess(Long idProcessRecrutement) {
        processRecrutementRepository.deleteById(idProcessRecrutement);
    }

    public List<ProcessRecrutement> retrieveProcesssByRecAndUser(Long recId, Long userId) {
        return processRecrutementRepository.findProcessRecrutementByRecrutementIdRecrutementAndUserIdUser(recId,userId);
    }

    public ProcessRecrutement modifyProcess(ProcessRecrutement process) {
        return processRecrutementRepository.save(process);
    }

    public List<ProcessRecrutement> retrieveProcessesByRecrutement(Long idRecrutement) {
        return processRecrutementRepository.findProcessRecrutementByRecrutement_IdRecrutement(idRecrutement);
    }





    public Long countApprovedProcesses() {
        return processRecrutementRepository.countByApproved(true);
    }

    public Long countNonApprovedProcesses() {
        return processRecrutementRepository.countByApproved(false);
    }

    @Override
    public boolean compareSkillsAndApprove(Recrutement recrutement, ProcessRecrutement process) {
        Set<Skill> recruitmentSkills = recrutement.getRequiredSkills();
        Set<Skill> processSkills = process.getSkills();


        int commonSkillsCount = 0;
        for (Skill skill : recruitmentSkills) {
            if (processSkills.contains(skill)) {
                commonSkillsCount++;
            }
        }


        double compatibilityPercentage = ((double) commonSkillsCount / recruitmentSkills.size()) * 100;

        if (compatibilityPercentage >= 50) {
            return true;
        } else {
            return false;
        }


    }



    @Override
    public Map<Skill, Double> calculateSkillSelectionPercentageIncludingUnapproved() {
        List<ProcessRecrutement> allProcesses = processRecrutementRepository.findAll();
        Map<Skill, Integer> skillCounts = new HashMap<>();


        for (Skill skill : Skill.values()) {
            skillCounts.put(skill, 0);
        }


        for (ProcessRecrutement process : allProcesses) {
            Set<Skill> processSkills = process.getSkills();
            for (Skill skill : processSkills) {
                skillCounts.put(skill, skillCounts.get(skill) + 1);
            }
        }


        int totalProcesses = allProcesses.size();
        Map<Skill, Double> skillPercentages = new HashMap<>();
        for (Skill skill : skillCounts.keySet()) {
            double percentage = (double) skillCounts.get(skill) / totalProcesses * 100;
            skillPercentages.put(skill, percentage);
        }

        return skillPercentages;
    }
    public void approveProcess(Long processId) {
        ProcessRecrutement process = processRecrutementRepository.findById(processId)
                .orElseThrow(() -> new ProcessNotFoundException("Processus de recrutement non trouvé avec l'ID : " + processId));

        Recrutement recrutement = process.getRecrutement();

        if (process.isApproved()==true && recrutement.getNiveau() > 0) {



            int niveau = recrutement.getNiveau();
            if (niveau > 0) {
                recrutement.setNiveau(niveau - 1);

            }


            processRecrutementRepository.save(process);
            recrutementRepository.save(recrutement);
        }
    }







    public ProcessRecrutement saveProcess(ProcessRecrutement pr, String imageFile) throws IOException {
        pr.setImagePath(imageFile);
        processRecrutementRepository.save(pr);


        return processRecrutementRepository.save(pr);
    }


}