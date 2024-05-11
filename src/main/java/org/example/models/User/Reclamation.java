package org.example.models.User;

public class Reclamation {
    private int idReclamation;
    private int idUser;
    private String Titre;
    private String Description;
    private Boolean Etat;

    public Reclamation() {
    }

    public Reclamation(int idUser, String titre, String description, Boolean etat) {
        this.idUser = idUser;
        Titre = titre;
        Description = description;
        Etat = etat;
    }

    public Reclamation(int idReclamation, int idUser, String titre, String description, Boolean etat) {
        this.idReclamation = idReclamation;
        this.idUser = idUser;
        Titre = titre;
        Description = description;
        Etat = etat;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getEtat() {
        return Etat;
    }

    public void setEtat(Boolean etat) {
        Etat = etat;
    }
}
