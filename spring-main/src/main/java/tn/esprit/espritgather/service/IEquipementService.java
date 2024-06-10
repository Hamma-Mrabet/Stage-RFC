package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Equipement;
import tn.esprit.espritgather.enumeration.Equip;
import tn.esprit.espritgather.enumeration.Metric;

import java.util.List;
import java.util.Map;

public interface IEquipementService {
    public List<Equipement> retrieveAllEquipments();
    public List<Equipement> retrieveEquipementByEvent(long id);
    public List<Equipement> retrieveEquipementByClub(long id);
    Map<String, Double> getEquipmentStatistics(Metric metric, Equip equip);
    Map<String, Double> calculateEquipmentStatistics();
    public float getPriceByEvent(long id);
    public float getPriceByClub(long id);
    public Equipement retrieveEquipement(long id);
    public Equipement addEquipement(Equipement equipement);
    public void removeEquipement (long id);
    public Equipement modifyEquipement(Equipement equipement);

    Equipement addEquipementAdmin(Equipement c);
}
