package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.ProcessRecrutement;
import tn.esprit.espritgather.entity.Recrutement;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.enumeration.Skill;
import tn.esprit.espritgather.enumeration.SkillLevel;
import tn.esprit.espritgather.repo.ProcessNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IProcessRecrutementService {
    public List<ProcessRecrutement> retrieveAllProcesses();
    public ProcessRecrutement retrieveProcess(Long idProcessRecrutement);
    public ProcessRecrutement addProcess(ProcessRecrutement process);
    public void removeProcess(Long idProcessRecrutement);
    public ProcessRecrutement modifyProcess(ProcessRecrutement process);
    List<ProcessRecrutement> retrieveProcessesByRecrutement(Long idRecrutement);
    public void approveProcess(Long processId);
    public Map<Skill, Double> calculateSkillSelectionPercentageIncludingUnapproved();
    //void createProcessRecrutement(ProcessRecrutement processRecrutement);
    public Long countApprovedProcesses();
    public Long countNonApprovedProcesses();

    public boolean compareSkillsAndApprove(Recrutement recrutement, ProcessRecrutement process);
    List<ProcessRecrutement> retrieveProcesssByRecAndUser(Long recId, Long userId);



    public ProcessRecrutement saveProcess(ProcessRecrutement pr, String imageFile) throws IOException;





}

