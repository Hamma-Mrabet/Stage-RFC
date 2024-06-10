package tn.esprit.espritgather.dto;

import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetails;

@Builder

public class LoginRespone {
    private  String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public LoginRespone(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
