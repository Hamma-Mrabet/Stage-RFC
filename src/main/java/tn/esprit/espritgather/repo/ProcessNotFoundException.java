package tn.esprit.espritgather.repo;

public class ProcessNotFoundException extends RuntimeException{
    public ProcessNotFoundException(String message) {
        super(message);
    }
}
