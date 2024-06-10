package tn.esprit.espritgather.control;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Feedback;
import tn.esprit.espritgather.service.IEventService;
import tn.esprit.espritgather.service.IFeedbackService;

import java.util.List;

@Tag(name = "Gestion Feedback")
@RestController
@AllArgsConstructor
@RequestMapping("/feedback")
@CrossOrigin(origins = "*")
public class FeedbackRestController {

    IFeedbackService feedbackService;
    // http://localhost:8089/espritgather/feedback/feedbacks
   @Operation(description = "récupération de tout les feedbacks")
    @GetMapping("/feedbacks")
    public List<Feedback> getfeedbacks() {
       List <Feedback> feedbacks = feedbackService.retrieveAllFeedback();
       return feedbacks ;
    }
    // http://localhost:8089/espritgather/feedback/retrieve-feedback/8
    @GetMapping("/retrieve-feedback/{feedback-id}")
    public Feedback retrievefeedback(@PathVariable("feedback-id") Long chId) {
        return feedbackService.retrieveFeedback(chId);
    }
    // http://localhost:8089/espritgather/feedback/add-feedback
    @PostMapping("/add-feedback")
    public Feedback addfeedback(@RequestBody Feedback c) {
       return feedbackService.addFeedback(c);
    }

    // http://localhost:8089/espritgather/feedback/add-feedback-admin
    @PutMapping("/add-feedback-admin")
    public Feedback addfeedbackadmin(@RequestBody Feedback c) {
        return feedbackService.addFeedbackadmin(c);
    }

    // http://localhost:8089/espritgather/feedback/remove-feedback/{feedback-id}
    @DeleteMapping("/remove-feedback/{feedback-id}")
    public void removeFeedback(@PathVariable("feedback-id") Long chId) {
        feedbackService.removeFeedback(chId);
    }


    // http://localhost:8089/espritgather/feedback/modify-feedback
    @PutMapping("/modify-feedback")
    public Feedback modifyFeedback(@RequestBody Feedback c) {
        return feedbackService.modifyFeedback(c);
    }
}
