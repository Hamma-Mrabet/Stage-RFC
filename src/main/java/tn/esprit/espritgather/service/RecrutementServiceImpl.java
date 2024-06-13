package tn.esprit.espritgather.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Recrutement;

import tn.esprit.espritgather.repo.RecrutementRepository;

import java.util.Date;
import java.util.List;
@Service
@Slf4j

@AllArgsConstructor
public class RecrutementServiceImpl implements IRecrutementService{
    RecrutementRepository recrutementRepository;
    public List<Recrutement> retrieveAllRecrutements() { return recrutementRepository.findAll(); }
    public Recrutement retrieveRecrutement(Long recrutementId) {
        return recrutementRepository.findById(recrutementId).get();
    }
    public Recrutement addRecrutement(Recrutement recrutement) {
        return recrutementRepository.save(recrutement);
    }
    public void removeRecrutement(Long recrutementId) {
        recrutementRepository.deleteById(recrutementId);

    }
    public Recrutement modifyRecrutement(Recrutement recrutement) {
        return recrutementRepository.save(recrutement);
    }


    public List<Recrutement> retrieverecsByUser(Long userId) {
        return recrutementRepository.findRecrutementByUserIdUser(userId);
    }

    @Scheduled(fixedRate = 60000)
    public void updateRecArchive() {
        List<Recrutement> rs = recrutementRepository.findAll();
        for (Recrutement r : rs) {
            if (r.getDateFinish() == null || new Date().after(r.getDateFinish())) {
                r.setArchive(true);
                recrutementRepository.save(r);
            }
            else{
                r.setArchive(false);
                recrutementRepository.save(r);
            }
        }
    }


}
