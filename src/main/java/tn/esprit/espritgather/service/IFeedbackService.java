package tn.esprit.espritgather.service;

import tn.esprit.espritgather.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    public List<Feedback> retrieveAllFeedback();
    public Feedback retrieveFeedback(Long idFeedback);
    public Feedback addFeedback(Feedback feedback);
    public void removeFeedback(Long idFeedback);
    public Feedback modifyFeedback(Feedback feedback);

    Feedback addFeedbackadmin(Feedback c);
}