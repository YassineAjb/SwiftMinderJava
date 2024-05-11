package org.example.models.Election;

public class Vote {
    private Candidat candidat; // assuming a one-to-one relationship

    private int idV ;
    private int idCandidatV ;
    private int idElectionV ;

    public int getIdV() {
        return idV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    public int getIdCandidatV() {
        return idCandidatV;
    }

    public void setIdCandidatV(int idCandidatV) {
        this.idCandidatV = idCandidatV;
    }

    public int getIdElectionV() {
        return idElectionV;
    }

    public void setIdElectionV(int idElectionV) {
        this.idElectionV = idElectionV;
    }
    public Candidat getCandidat() {
        return candidat;
    }

    public void setElection(Candidat candidat) {
        this.candidat = candidat;
    }
    private Election election; // assuming a one-to-one relationship

    public Election getElection() {
        return election;
    }

    public void setCElection(Election election) {
        this.election = election;
    }


}
