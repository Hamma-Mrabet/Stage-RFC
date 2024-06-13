package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Publication;

import java.util.List;

public interface IPublicationService {
    List<Publication> retrieveAllPublications();
    Publication retrievePublication(Long publicationId);
    Publication addPublication(Publication publication);
    void removePublication(Long publicationId);
    Publication modifyPublication(Publication publication);

    List<Object[]> findAllWithNlAndDl();

    Object[] findLikesAndDislikesByPublicationId(Long publicationId);


}
