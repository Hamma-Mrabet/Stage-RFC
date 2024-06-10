package tn.esprit.espritgather.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Equipement;
import tn.esprit.espritgather.enumeration.Equip;
import tn.esprit.espritgather.enumeration.Metric;
import tn.esprit.espritgather.repo.EquipementRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class IEquipementServiceImpl implements IEquipementService{
    EntityManager entityManager;
    EquipementRepository equipementRepository;
    @Override
    public List<Equipement> retrieveAllEquipments() {
        return equipementRepository.findAll();
    }

    @Override
    public List<Equipement> retrieveEquipementByEvent(long id) {
       // String sql ="SELECT * FROM `equipement` WHERE event_id_event = "+id+";";
        String sql ="SELECT * FROM `equipement` WHERE event = "+id+";";
        return  entityManager.createNativeQuery(sql).getResultList();
    }
    @Override
    public Map<String, Double> getEquipmentStatistics(Metric metric, Equip equip ) {
         List<Object[]> results =equipementRepository.calculateEquipmentStatistics(metric,equip);
        Map<String, Double> totalPricesByEvent = new HashMap<>();
        for (Object[] result : results) {
            String eventId = String.valueOf(result[0]);
            Double totalPrice = (Double) result[1];
            totalPricesByEvent.put(eventId, totalPrice);
        }
        return totalPricesByEvent;
    }

    @Override
    public Map<String, Double>  calculateEquipmentStatistics() {

        List<Object[]> results = equipementRepository.calculateEquipmentStatistics();
        Map<String, Double> totalPricesByEvent = new HashMap<>();
        for (Object[] result : results) {
            String eventId = String.valueOf(result[0]);
            Double totalPrice = (Double) result[1];
            totalPricesByEvent.put(eventId, totalPrice);
        }
        return totalPricesByEvent;
    }


    @Override
    public List<Equipement> retrieveEquipementByClub(long id) {
      //  String sql ="SELECT * FROM equipement JOIN event ON equipement.event_id_event = event.id_event JOIN user ON event.user_id_user = user.id_user WHERE user.id_user ="+id+";";
        String sql ="SELECT * FROM equipement JOIN event ON equipement.event = event.id_event JOIN user ON event.user_id_user = user.id_user WHERE user.id_user ="+id+";";
        return entityManager.createNativeQuery(sql).getResultList();
    }



    @Override
    public float getPriceByEvent(long id) {
     /*   String sql ="SELECT SUM(price) AS total_price FROM equipement WHERE event_id_event="+id+";";
        return entityManager.createNativeQuery(sql).executeUpdate();*/
     //   String sql = "SELECT SUM(price) AS total_price FROM equipement WHERE event_id_event = "+id+";";
        String sql = "SELECT SUM(price) AS total_price FROM equipement WHERE event = "+id+";";
        return ((Number) entityManager.createNativeQuery(sql)
                .getSingleResult())
                .floatValue();
    }

    @Override
    public float getPriceByClub(long id) {
   //  String sql = "SELECT SUM(price) FROM equipement JOIN event ON equipement.event_id_event = event.id_event JOIN user ON event.user_id_user = user.id_user WHERE user.id_user ="+id+";";
        String sql = "SELECT SUM(equipement.price) FROM equipement JOIN event ON equipement.event = event.id_event JOIN user ON event.user = user.id_user WHERE user.id_user ="+id+";";
        return ((Number) entityManager.createNativeQuery(sql)
                .getSingleResult())
                .floatValue();
    }

    @Override
    public Equipement retrieveEquipement(long id) {
        return equipementRepository.findById(id).get();
    }

    @Override
    public Equipement addEquipement(Equipement equipement) {
        equipement.setApprouvement(null);
        equipement.setDatemeeting(null);
        return equipementRepository.save(equipement);
    }

  //  @Transactional
    @Override
    public void removeEquipement(long id) {
    equipementRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Equipement modifyEquipement(Equipement equipement) {
      /*  String sql ="UPDATE `equipement` SET `equipement`='"+equipement.getEquipement()+"',`other`='"+equipement.getOther()+"',`price`='"+equipement.getPrice()+"',`quantite`='"+equipement.getQuantite()+"',`datemeeting`='"+equipement.getDatemeeting()+"',`typeequip`='"+equipement.getTypeequip()+"' WHERE `id_equipmenet`='"+equipement.getIdEquipmenet()+"'";
        entityManager.createNativeQuery(sql).executeUpdate();*/
        equipementRepository.save(equipement);
        return equipement;
    }

    @Override
    public Equipement addEquipementAdmin(Equipement c) {
        equipementRepository.save(c);
        return c;
    }
}
