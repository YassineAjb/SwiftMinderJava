package org.example.models.Employe;

import com.dlsc.gemsfx.daterange.DateRangePicker;

public class Contrat {
    private int id;
    private Joueur joueur;
    private DateRangePicker date;
    private int salaire;

    public Contrat() {
    }

    public Contrat(int id, Joueur joueur, DateRangePicker date, int salaire) {
        this.id = id;
        this.joueur = joueur;
        this.date = date;
        
        this.salaire = salaire;
    }

    public Contrat(Joueur joueur, DateRangePicker date,  int salaire) {
        this.joueur = joueur;
        this.date = date;
        
        this.salaire = salaire;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateRangePicker getdate() {
        return date;
    }

    public void setdate(DateRangePicker date) {
        this.date = date;
    }


    public void setDateRangePicker_fin(DateRangePicker date_fin) {
        
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", joueur=" + joueur +
                ", date=" + date +
                ", salaire=" + salaire +
                '}';
    }

}

