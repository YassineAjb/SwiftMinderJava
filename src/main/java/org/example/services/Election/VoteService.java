package org.example.services.Election;

import org.example.models.Election.Candidat;
import org.example.models.Election.Election;
import org.example.models.Election.Vote;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoteService implements IService<Vote> {

    private Connection connection;

    public VoteService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Vote vote) throws SQLException {
        String sql = "INSERT INTO vote (idCandidatv, idElectionV) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vote.getIdCandidatV());
            preparedStatement.setInt(2, vote.getIdElectionV());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Vote vote) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Vote> recuperer() throws SQLException {
        String sql = "SELECT * FROM vote";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Vote> votes = new ArrayList<>();
        while (rs.next()) {
            Vote p = new Vote();
            p.setIdV(rs.getInt("idV"));
            p.setIdCandidatV(rs.getInt("idCandidatV"));
            p.setIdElectionV(rs.getInt("idElectionV"));

            votes.add(p);
        }
        return votes;
    }


    public Candidat getCandidatForVote(Vote vote) throws SQLException {
        int xx = vote.getIdCandidatV();
        Candidat candidat = null;
        System.out.println("id  el vote "+xx);
        String sql = "SELECT * FROM candidat WHERE idC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, xx);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                candidat = new Candidat();
                candidat.setIdC(rs.getInt("idC"));
                candidat.setNomC(rs.getString("nomC"));
                candidat.setPrenomC(rs.getString("prenomC"));
                candidat.setAgeC(rs.getInt("ageC"));
                candidat.setImgCpath(rs.getString("imgCpath"));
                candidat.setIdElection(rs.getInt("idElection"));
                System.out.println("id candidat mtte3 el vote "+rs.getInt("idC"));

            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Candidat by id: " + ex.getMessage());
        }

        return candidat;
    }

    public Election getElectionForVote(Vote vote) {
        int xx = vote.getIdElectionV();
        Election election = new Election();
        System.out.println("id el election mte3 el vote "+xx);
        String sql = "SELECT * FROM evenement WHERE idE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, xx);
            ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                 //   Election election = new Election();
                    election.setIdE(rs.getInt("idE"));
                    election.setNomE(rs.getString("nomE"));
                    election.setDateE(LocalDate.parse(rs.getString("dateE")));
                    election.setPosteE(rs.getString("posteE"));
                    election.setPeriodeP(rs.getString("periodeP"));
                    election.setImgEpath(rs.getString("imgEpath"));
                    System.out.println("id election mtte3 el vooooote "+rs.getInt("idE"));
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Election by id: " + ex.getMessage());
        }

        return election;
    }
}
