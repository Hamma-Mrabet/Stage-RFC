package tn.esprit.espritgather.service;


import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Recrutement;
import tn.esprit.espritgather.repo.RecrutementRepository;

import java.util.Date;
import java.util.List;

public interface IRecrutementService {
    public void updateRecArchive();

    public List<Recrutement> retrieveAllRecrutements();
    public Recrutement retrieveRecrutement(Long idRecrutement);
    public Recrutement addRecrutement(Recrutement recrutement);
    public void removeRecrutement(Long idRecrutement);
    public Recrutement modifyRecrutement(Recrutement recrutement);

    List<Recrutement> retrieverecsByUser(Long userId);




}
