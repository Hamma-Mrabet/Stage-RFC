package tn.esprit.espritgather.dto;

public class LoginRequest {
    private String Mail ;
    private String password ;

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
