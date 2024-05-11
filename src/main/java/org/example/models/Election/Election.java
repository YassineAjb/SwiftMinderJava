package org.example.models.Election;

import java.time.LocalDate;
import java.util.Date;



public class Election {
    private int idE;
    private String nomE;
    private LocalDate dateE;
    private String posteE;
    private String periodeP;
    private String imgEpath;


    // Constructor and other methods...


    public Election(int idE, String nomE, LocalDate dateE, String posteE, String periodeP) {
        this.idE = idE;
        this.nomE = nomE;
        this.dateE = dateE;
        this.posteE = posteE;
        this.periodeP = periodeP;
    }

    public Election(int idE, String nomE, LocalDate dateE, String posteE, String periodeP, String imgEpath) {
        this.idE = idE;
        this.nomE = nomE;
        this.dateE = dateE;
        this.posteE = posteE;
        this.periodeP = periodeP;
        this.imgEpath = imgEpath;
    }

    public Election(String nomE, LocalDate dateE, String posteE, String periodeP, String imgEpath) {
        this.nomE = nomE;
        this.dateE = dateE;
        this.posteE = posteE;
        this.periodeP = periodeP;
        this.imgEpath = imgEpath;
    }

    public Election() {
    }

    public Election(String nomE, LocalDate dateE, String posteE, String periodeP) {
        this.nomE = nomE;
        this.dateE = dateE;
        this.posteE = posteE;
        this.periodeP = periodeP;
    }

    public int getIdE() {
        return idE;
    }
    public void setIdE(int idE) {
        this.idE = idE;
    }
    public String getNomE() {
        return nomE;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    public LocalDate getDateE() {
        return dateE;
    }

    public void setDateE(LocalDate dateE) {
        this.dateE = dateE;
    }

    public String getPosteE() {
        return posteE;
    }

    public void setPosteE(String posteE) {
        this.posteE = posteE;
    }

    public String getPeriodeP() {
        return periodeP;
    }

    public void setPeriodeP(String periodeP) {
        this.periodeP = periodeP;
    }

    public String getImgEpath() {
        // Check if imgEpath is null, if so, provide a default image path or an empty string
        String s = imgEpath != null ? imgEpath : "";
        return s;
    }

    public void setImgEpath(String imgEpath) {
        this.imgEpath = imgEpath;
    }

    @Override
    public String toString() {
        return "Election{" +
                "idE=" + idE +
                ", nomE='" + nomE + '\'' +
                ", dateE=" + dateE +
                ", posteE='" + posteE + '\'' +
                ", periodeP='" + periodeP + '\'' +
                ", imgEpath='" + imgEpath + '\'' +
                '}';
    }
  /*  @Override
    public String toString() {
        return String.format("%-33s   %-72s   %-40s  %-37d  %-25d   %-15s ", title, associationName, campaignType,goal,number,description);
    }*/
}
