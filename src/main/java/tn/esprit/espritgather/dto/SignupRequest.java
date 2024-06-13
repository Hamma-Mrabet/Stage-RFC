package tn.esprit.espritgather.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tn.esprit.espritgather.enumeration.ClubTypes;
import tn.esprit.espritgather.enumeration.NiveauEtude;
import tn.esprit.espritgather.enumeration.Role;

import java.util.Date;

public class SignupRequest {
    @Email(message = "Valid email address is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String mail ;
@NotNull(message = "Firstname is required")

private String firstName;
@NotNull(message = "Lastname is required")

private String lastName;
@NotNull(message = "Password is required")

private String password;
@NotNull(message = "Role is required")


private Role role;
@NotNull(message = "TelNumber is required")

private String telNumber;

    private Date birthDay;


    private NiveauEtude niveau;
    private String address;
    private String clubDetails ;
    private String clubName ;

    public String getClubDetails() {
        return clubDetails;
    }

    public void setClubDetails(String clubDetails) {
        this.clubDetails = clubDetails;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    private ClubTypes clubTypes ;





    public ClubTypes getClubTypes() {
        return clubTypes;
    }

    public void setClubTypes(ClubTypes clubTypes) {
        this.clubTypes = clubTypes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NiveauEtude getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauEtude niveau) {
        this.niveau = niveau;
    }
}
