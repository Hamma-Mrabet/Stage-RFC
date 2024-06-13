package tn.esprit.espritgather.service;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.espritgather.entity.Feedback;
import tn.esprit.espritgather.repo.FeedbackRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {
    EntityManager entityManager;
    FeedbackRepository feedbackRepository;
    @Override
    public List<Feedback> retrieveAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback retrieveFeedback(Long idFeedback) {
        return feedbackRepository.findById(idFeedback).get();
    }

    @Transactional
    @Override
    public Feedback addFeedback(Feedback feedback) {
        feedback.setDateFeedback(new Date());
        feedback.setDatevu(null);
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public void removeFeedback(Long idFeedback) {
        feedbackRepository.deleteById(idFeedback);

    }

    @Transactional
    @Override
    public Feedback modifyFeedback(Feedback feedback) {
        feedback.setDateFeedback(new Date());
      //  String sql = "UPDATE `feedback` SET `body`='"+feedback.getBody()+"',`title`='"+feedback.getTitle()+"',`date_feedback`='"+feedback.getDateFeedback()+"' WHERE `id_feedback`='"+feedback.getIdFeedback()+"'   ";
      //  entityManager.createNativeQuery(sql).executeUpdate();


       feedbackRepository.save(feedback);
        return feedback;
    }

    @Transactional
    @Override
    public Feedback addFeedbackadmin(Feedback c) {
          c.setDatevu(new Date());
        //  String sql = "UPDATE `feedback` SET `datevu`='"+ c.getDatevu()+"' WHERE `id_feedback`='"+c.getIdFeedback()+"'   ";
        //  entityManager.createNativeQuery(sql).executeUpdate();
        feedbackRepository.save(c);
        return c;
    }
}