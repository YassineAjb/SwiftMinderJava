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
        String sql = "INSERT INTO vote (idCandidatv, idElectionV, idUser) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vote.getIdCandidatV());
            preparedStatement.setInt(2, vote.getIdElectionV());
            preparedStatement.setInt(3, vote.getIdUser());
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
            p.setIdUser(rs.getInt("idUser"));
            votes.add(p);
        }
        return votes;
    }


    public Candidat getCandidatForVote(Vote vote) throws SQLException {
        int xx = vote.getIdCandidatV();
        //Candidat candidat = null;
        Candidat candidat = new Candidat();

        System.out.println("id  el vote "+xx);
        String sql = "SELECT * FROM candidat WHERE idC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, xx);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                //candidat = new Candidat();
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
        Election electionn = new Election();
        System.out.println("id el election mte3 el vote "+xx);
        String sql = "SELECT * FROM evenement WHERE idE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, xx);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(" hnee");

                if (rs.next()) {
                 //   Election election = new Election();
                    System.out.println(" hnee");
                    electionn.setIdE(rs.getInt("idE"));
                    electionn.setNomE(rs.getString("nomE"));
                    electionn.setDateE(LocalDate.parse(rs.getString("dateE")));
                    electionn.setPosteE(rs.getString("posteE"));
                    electionn.setPeriodeP(rs.getString("periodeP"));
                    electionn.setImgEpath(rs.getString("imgEpath"));System.out.println("id election mtte3 el vooooote "+rs.getInt("idE"));
            }else {
                    System.out.println("mouch mawjouda election"+xx);
                }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Election by id: " + ex.getMessage());
        }

        return electionn;
    }

    public boolean hasVoted(int idElection, int idUser) {
        String sql = "SELECT COUNT(*) FROM vote WHERE idElectionV = ? AND idUser = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idElection);
            preparedStatement.setInt(2, idUser);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Retourne true si une entrée existe, sinon false
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if user has voted: " + ex.getMessage());
        }
        return false; // Par défaut, indique que l'utilisateur n'a pas voté en cas d'erreur
    }
}
