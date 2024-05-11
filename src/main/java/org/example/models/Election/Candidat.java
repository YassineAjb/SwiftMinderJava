package org.example.models.Election;
public class Candidat {
    private int idC;
    private String nomC;
    private String prenomC;
    private int ageC;
    private String imgCpath ;
    private int idElection ;


    public Candidat() {
    }

    public Candidat(int idC, String nomC, String prenomC, int ageC, String imgCpath, int idElection) {
        this.idC = idC;
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.ageC = ageC;
        this.imgCpath = imgCpath;
        this.idElection = idElection;
    }

    public Candidat(String nomC, String prenomC, int ageC, String imgCpath, int idElection) {
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.ageC = ageC;
        this.imgCpath = imgCpath;
        this.idElection = idElection;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    public String getPrenomC() {
        return prenomC;
    }

    public void setPrenomC(String prenomC) {
        this.prenomC = prenomC;
    }

    public int getAgeC() {
        return ageC;
    }

    public void setAgeC(int age) {
        this.ageC = age;
    }

    public String getImgCpath() {
        // Check if imgEpath is null, if so, provide a default image path or an empty string
        String s = imgCpath != null ? imgCpath : "";
        return s;
    }

    public void setImgCpath(String imgCpath) {
        this.imgCpath = imgCpath;
    }

    public int getIdElection() {
        return idElection;
    }

    public void setIdElection(int idElection) {
        this.idElection = idElection;
    }


    @Override
    public String toString() {
        return "Candidat{" +
                "idC=" + idC +
                ", nomC='" + nomC + '\'' +
                ", prenomC='" + prenomC + '\'' +
                ", age=" + ageC +
                '}';
    }
}
