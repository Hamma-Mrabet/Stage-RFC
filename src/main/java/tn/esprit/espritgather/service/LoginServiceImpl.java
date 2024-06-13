package tn.esprit.espritgather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class LoginServiceImpl implements UserDetailsService {

    @Autowired
  private final UserRepository userRepository ;

  @Autowired
  public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findBymail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found" + mail));
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), Collections.emptyList());


    }

    public String forgetPassword(String mail) {
        User user = userRepository.findBymail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found" + mail));
        return user.getPassword();
    }


}

