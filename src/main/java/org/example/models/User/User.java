package org.example.models.User;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String mot_de_passe;
    private Date date_creation;
    private String role;

    private String NumTel;

    public User() {
    }

    public User(int id, String email, String mot_de_passe, Date date_creation, String role, String NumTel) {
        this.id = id;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.date_creation = date_creation;
        this.role = role;
        this.NumTel = NumTel;
    }

    public User(String email, String mot_de_passe, Date date_creation, String role,String NumTel) {
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.date_creation = date_creation;
        this.role = role;
        this. NumTel = NumTel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNumTel() {
        return NumTel;
    }

    public void setNumTel(String NumTel) {
        this.NumTel = NumTel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", date_creation=" + date_creation +
                ", role='" + role + '\'' +
                ", NumTel=" + NumTel +
                '}';
    }
}