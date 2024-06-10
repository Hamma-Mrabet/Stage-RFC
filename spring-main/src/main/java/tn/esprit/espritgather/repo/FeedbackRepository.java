package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.espritgather.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
