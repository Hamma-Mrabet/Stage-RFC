package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Management;

import java.util.List;
import java.util.Map;

public interface IManagementService {
    public List<Management> retrieveAllManagement();
    Management retrieveManagementByEvent(Long idEvent);
    public Long getManagementByEvent(Long idv);
    Map<String, Double> getManagementStatistics();
    public Management retrieveManagement(Long idManagement);
    public Management addManagement(Management management);
    public void removeManagement(Long idManagement);
    public Management modifyManagement(Management management);
    public Management AddClassroomsAcoordinally(Management management);
    Management addManagementAdmin(Management c);
    public List<Event> getOverlappingEvents(Management management);
    public List<Management> getSimilarManagementClass(Management management, String bloc, String c);
}
