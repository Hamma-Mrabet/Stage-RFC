package tn.esprit.espritgather.service;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Management;
import tn.esprit.espritgather.entity.Ticket;
import tn.esprit.espritgather.repo.EventRepository;
import tn.esprit.espritgather.repo.ManagementRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class IManagementServiceImpl implements IManagementService {
    EntityManager entityManager;
    ManagementRepository managementRepository;
    EventRepository eventRepository;

    @Override
    public List<Management> retrieveAllManagement() {
        return managementRepository.findAll();
    }

    @Transactional
    @Override
    public Long getManagementByEvent(Long idv) {
        //String sql ="SELECT id_management FROM `management` WHERE management.event_id_event ="+idv+";";
        String sql ="SELECT id_management FROM `management` WHERE management.event ="+idv+";";
        Object result = entityManager.createNativeQuery(sql).getSingleResult();

        if (result != null) {
            // Convert the result to the appropriate type (int or String)
            if (result instanceof Long) {
                return ((Long) result).longValue();
            } else if (result instanceof String) {
                return (Long) result;
            } else {
                // Handle unsupported result type
                throw new IllegalStateException("Unexpected result type: " + result.getClass().getName());
            }
        } else {
            // Handle the case where there are no results
            return null;
        }
    }

    @Override
    public Map<String, Double> getManagementStatistics() {

        List<Object[]> results = managementRepository.calculateManagementCounts();
        for (Object[] result : results) {
            System.out.println(Arrays.toString(result));
            System.out.println(result.length);
        }
        List<Management> man = retrieveAllManagement();
        System.out.println(man.size());
        Map<String, Double> totalPricesByEvent = new HashMap<>();
        for (Object[] result : results) {
            String eventId = String.valueOf(result[0])+"-"+String.valueOf(result[1]);
            Long totalCount = (Long) result[2]*100/man.size();
            Double totalPrice = totalCount.doubleValue();
            totalPricesByEvent.put(eventId, totalPrice);
        }
        return totalPricesByEvent;
    }


    public Management retrieveManagementByEvent(Long eventId) {
        return managementRepository.findManagementByEventIdEvent(eventId);
    }

    @Override
    public Management retrieveManagement(Long idManagement) {
        return managementRepository.findById(idManagement).get();
    }

    @Override
    public Management addManagement(Management management) {
        management.setBloc(null);
        management.setClasse(null);
        management.setApprouvement(null);
        return managementRepository.save(management);
    }


    @Override
    public Management addManagementAdmin(Management management) {
        return managementRepository.save(management);
    }

    @Override
    public List<Event> getOverlappingEvents(Management management) {
        Management m = retrieveManagement(management.getIdManagement());
        String sql = "SELECT * FROM `event` " +
                "WHERE " +
                "(((DATE(event.date_start) = DATE(?1) OR DATE(event.date_finish) = DATE(?1) " +
                "OR DATE(?1) BETWEEN DATE(event.date_start) AND DATE(event.date_finish)) " +
                "OR " +
                "(DATE(event.date_start) = DATE(?2) OR DATE(event.date_finish) = DATE(?2) " +
                "OR DATE(?2) BETWEEN DATE(event.date_start) AND DATE(event.date_finish)))) AND (event.id_event!="+m.getEvent().getIdEvent()+");";
        List<Event> events ;
        events = entityManager.createNativeQuery(sql)
                .setParameter(1, m.getEvent().getDateStart())
                .setParameter(2,m.getEvent().getDateFinish())
                .getResultList();
        return events;
    }

    @Override
    public List<Management> getSimilarManagementClass(Management management, String bloc, String c) {
        String a = "SELECT * FROM `management` WHERE management.bloc = \""+bloc+"\" AND management.classe = \""+c+"\" AND management.id_management !="+management.getIdManagement()+";";
        return entityManager.createNativeQuery(a).getResultList();
    }

    @Override
    public void removeManagement(Long idManagement) {
       /* String sql = "DELETE FROM `management` WHERE `management`.`id_management` = "+idManagement;
        entityManager.createNativeQuery(sql);*/
      managementRepository.deleteById(idManagement);
    }

    @Transactional
    @Override
    public Management modifyManagement(Management management) {
       // String sql ="UPDATE `management` SET `bloc`='"+management.getBloc()+"',`classe`='"+management.getClasse()+"' WHERE `id_management`='"+management.getIdManagement()+"'  ";
        //entityManager.createNativeQuery(sql).executeUpdate();
        managementRepository.save(management);
        return management;
    }



   @Transactional
    @Override
    public Management AddClassroomsAcoordinally(Management m) {
        Management management = retrieveManagement(m.getIdManagement());

        List<Event> listevent = getOverlappingEvents (m);
       List<Management> list= getSimilarManagementClass(management, m.getBloc().toString(),m.getClasse().toString());
       if (list.isEmpty()){listevent.clear();}
        System.out.println(list.size());
        System.out.println(listevent.size());
        System.out.println(listevent.isEmpty());

        if ((listevent.isEmpty()&& list.isEmpty())||(listevent.isEmpty())){

           String sql ="UPDATE `management` SET `bloc`='"+m.getBloc()+"',`classe`='"+m.getClasse()+"' WHERE `id_management`='"+management.getIdManagement()+"'  ";
           entityManager.createNativeQuery(sql).executeUpdate();



           return management;
       }else{


          //  String sql ="UPDATE `management` SET `bloc`='"+management.getBloc()+"',`classe`='"+management.getClasse()+"' WHERE `id_management`='"+management.getIdManagement()+"'  ";
        //    entityManager.createNativeQuery(sql).executeUpdate();
            Management m1= new Management();
            m1.setDetails("erreur");
            System.out.println("m1 fel lokhra:"+m1.getDetails());
           return m1;
        }

    }
}
