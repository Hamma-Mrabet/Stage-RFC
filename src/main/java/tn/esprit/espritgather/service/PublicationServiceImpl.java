package tn.esprit.espritgather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Interaction;
import tn.esprit.espritgather.entity.Publication;
import tn.esprit.espritgather.repo.InteractionRepository;
import tn.esprit.espritgather.repo.PublicationRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationServiceImpl implements IPublicationService {
    private final PublicationRepository publicationRepository;
private final InteractionRepository interactionRepository;
    @Override
    public List<Publication> retrieveAllPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication retrievePublication(Long publicationId) {
        return publicationRepository.findById(publicationId).orElse(null);
    }

    public List<Object[]> findAllWithNlAndDl() {
        return publicationRepository.findAllWithNlAndDl();
    }

    public Object[] findLikesAndDislikesByPublicationId(Long publicationId) {
        return publicationRepository.findLikesAndDislikesByPublicationId(publicationId);
    }






    @Override
    public Publication addPublication(Publication publication) {
        publication.setDatePublication(new Date());
        return publicationRepository.save(publication);
    }

    @Override
    public void removePublication(Long publicationId) {
        publicationRepository.deleteById(publicationId);
    }

    @Override
    public Publication modifyPublication(Publication publication) {
        publication.setDatePublication(new Date());
        return publicationRepository.save(publication);
    }
}
