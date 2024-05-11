package org.example.models.Reservation;

public class Terrain {
    private int id ;
    private String nomTerrain;
    private String adresse;
    private String description;
    private double GeoX;
    private double GeoY;
    private String ouverture;
    private String fermeture;

    private String datedispo ;

    public Terrain(String nomTerrain, String adresse, String description, double GeoX, double GeoY, String ouverture, String fermeture , String datedispo)  {
        this.nomTerrain = nomTerrain;
        this.adresse = adresse;
        this.description = description;
        this.GeoX = GeoX;
        this.GeoY = GeoY;
        this.ouverture = ouverture;
        this.fermeture = fermeture;
        this.datedispo = datedispo ;
    }

    public Terrain(int id, String nomTerrain, String adresse, String description, double geoX, double geoY, String ouverture, String fermeture, String datedispo) {
        this.id = id;
        this.nomTerrain = nomTerrain;
        this.adresse = adresse;
        this.description = description;
        GeoX = geoX;
        GeoY = geoY;
        this.ouverture = ouverture;
        this.fermeture = fermeture;
        this.datedispo = datedispo;
    }

    public Terrain() {

    }

    public int getId() {
        return id;
    }

    public String getNomTerrain() {
        return nomTerrain;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDescription() {
        return description;
    }

    public double getGeoX() {
        return GeoX;
    }

    public double getGeoY() {
        return GeoY;
    }

    public String getOuverture() {
        return ouverture;
    }

    public String getFermeture() {
        return fermeture;
    }


    public String getDatedispo() {
        return datedispo;
    }

    public void setNomTerrain(String nomTerrain) {
        this.nomTerrain = nomTerrain;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGeoX(double geoX) {
        GeoX = geoX;
    }

    public void setGeoY(double geoY) {
        GeoY = geoY;
    }

    public void setOuverture(String ouverture) {
        this.ouverture = ouverture;
    }

    public void setFermeture(String fermeture) {
        this.fermeture = fermeture;
    }

    public void setDatedispo(String datedispo) {
        this.datedispo = datedispo;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Terrain{" +
                "id=" + id +
                ", nomTerrain='" + nomTerrain + '\'' +
                ", adresse='" + adresse + '\'' +
                ", description='" + description + '\'' +
                ", GeoX=" + GeoX +
                ", GeoY=" + GeoY +
                ", ouverture='" + ouverture + '\'' +
                ", fermeture='" + fermeture + '\'' +
                ", datedispo='" + datedispo + '\'' +
                '}';
    }
}
