package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.espritgather.entity.Equipement;
import tn.esprit.espritgather.enumeration.Equip;
import tn.esprit.espritgather.enumeration.Metric;

import java.util.List;

public interface EquipementRepository extends JpaRepository<Equipement,Long> {

    @Query("SELECT e.equipement, SUM(e.price) FROM Equipement e WHERE e.metric = :metric AND e.equipement =:equip GROUP BY e.equipement")
    List<Object[]> calculateEquipmentStatistics(@Param("metric") Metric metric, @Param("equip") Equip equip);

    @Query("SELECT e.metric, SUM(e.price) FROM Equipement e GROUP BY e.metric")
    List<Object[]> calculateEquipmentStatistics();


}
