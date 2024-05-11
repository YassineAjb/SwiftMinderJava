package org.example.models.Reservation;

public class Reservation {

    private int reservationID;
    private String choixTerrain;
    private String dateReservation;  // Change the type to String
    private String note;
    private String emplacement;

    public Reservation() {

    }

    public Reservation(int choixTerrain, String dateReservation, String note, String emplacement) {
        this.choixTerrain = String.valueOf(choixTerrain);
        this.dateReservation = dateReservation;
        this.note = note;
        this.emplacement = emplacement;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getChoixTerrain() {
        return choixTerrain;
    }

    public void setChoixTerrain(String choixTerrain) {
        this.choixTerrain = choixTerrain;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
}
