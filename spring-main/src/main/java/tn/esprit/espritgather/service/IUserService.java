package tn.esprit.espritgather.service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.enumeration.NiveauEtude;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
    public List<User> retrieveAllUsers();
    public User retrieveUser(Long idUser);
    public User addUser(User u);
    public void removeUser(Long idUser);
    public User modifyUser(User user);
    public User retrieveUserByMail(String mail);

    public User saveUser(User u, String imageFile) throws IOException;

    public Map<NiveauEtude, Long> countUsersByNiveauClasse();
    public Map<String, Long> countUsersByCreationMonth();

    User findBymail(String mail);
    void createPasswordResetTokenForUser(User user);
    Optional<User> findUserByPasswordResetToken(String token);
    void changeUserPassword(User user, String password);




}