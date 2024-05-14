package org.example.services.Election;

import org.example.models.Election.Election;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ElectionService implements IService<Election> {

    private Connection connection;

    public ElectionService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Election election) throws SQLException {
        String sql = "INSERT INTO evenement (nomE, dateE, posteE,periodeP,imgEpath) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, election.getNomE());
            preparedStatement.setDate(2, java.sql.Date.valueOf(election.getDateE()));
            preparedStatement.setString(3, election.getPosteE());
            preparedStatement.setString(4, election.getPeriodeP());
            preparedStatement.setString(5, election.getImgEpath());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Election election) throws SQLException {
        String sql = "update evenement set nomE = ?, dateE = ?, description = ? where idE = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, election.getNomE());
        preparedStatement.setDate(2, Date.valueOf(election.getDateE()));
       // preparedStatement.setString(3, election.getDescription());
        preparedStatement.setInt(4, election.getIdE());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idE) throws SQLException {
        String sql = "delete from evenement where idE = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idE);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Election> recuperer() throws SQLException {
        String sql = "SELECT * FROM evenement";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Election> elections = new ArrayList<>();
        while (rs.next()) {
            Election p = new Election();
            p.setIdE(rs.getInt("idE"));
            p.setNomE(rs.getString("nomE"));
            Date sqlDate = rs.getDate("dateE");
            p.setDateE(sqlDate.toLocalDate());
            p.setPosteE(rs.getString("posteE"));
            p.setPeriodeP(rs.getString("periodeP"));
            p.setImgEpath(rs.getString("imgEpath"));
            elections.add(p);
        }
        return elections;
    }

    public List<Election> recupererElectionSysDate() throws SQLException {
        String sql = "SELECT * FROM evenement WHERE DATE(dateE) = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Obtenez la date actuelle
            LocalDate today = LocalDate.now();

            // Définissez la date actuelle comme paramètre dans la requête SQL
            preparedStatement.setDate(1, Date.valueOf(today));

            // Exécutez la requête
            try (ResultSet rs = preparedStatement.executeQuery()) {
                List<Election> elections = new ArrayList<>();
                while (rs.next()) {
                    Election p = new Election();
                    p.setIdE(rs.getInt("idE"));
                    p.setNomE(rs.getString("nomE"));
                    Date sqlDate = rs.getDate("dateE");
                    p.setDateE(sqlDate.toLocalDate());
                    p.setPosteE(rs.getString("posteE"));
                    p.setPeriodeP(rs.getString("periodeP"));
                    p.setImgEpath(rs.getString("imgEpath"));
                    elections.add(p);
                }
                return elections;
            }
        }
    }


    public List<Election> searchElectionByNomStartingWithLetter(String searchAttribute,String startingLetter) throws SQLException{
        List<Election> elections = new ArrayList<>();

        String sql = "SELECT * FROM evenement WHERE " + searchAttribute + " LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, startingLetter + "%");
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                Election e = new Election();
                e.setIdE(rs.getInt("idE"));
                e.setNomE(rs.getString("nomE"));
                e.setDateE(LocalDate.parse(rs.getString("dateE")));
                e.setPosteE(rs.getString("posteE"));
                e.setPeriodeP(rs.getString("periodeP"));
                e.setImgEpath(rs.getString("imgEpath"));

                elections.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for elections by name: " + ex.getMessage());
        }

        return elections;
    }

    public void modifier(String nomE, LocalDate dateE, String posteE, String periodeP, String imgEpath, int idE) throws SQLException {
        String sql = "update evenement set nomE = ?, dateE = ?, posteE = ?,periodeP = ?,imgEpath = ? where idE = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nomE);
        preparedStatement.setDate(2, java.sql.Date.valueOf(dateE));
        preparedStatement.setString(3,posteE);
        preparedStatement.setString(4,periodeP);
        preparedStatement.setString(5, imgEpath);
        preparedStatement.setInt(6,idE);

        preparedStatement.executeUpdate();
    }

    /*public List<Election> searchElectionByNomStartingWithLetter(String searchAttribute, String startingLetter) throws SQLException {
        List<Election> elections = new ArrayList<>();
        try (  Connection connection = MyDataBase.getInstance().getConnection();
               Statement statement = connection.createStatement();
               ResultSet rs = statement.executeQuery("SELECT * FROM evenement WHERE " + searchAttribute + " LIKE ?")) {
                while (rs.next()) {
                    Election e = new Election();
                    e.setIdE(rs.getInt("idE"));
                    e.setNomE(rs.getString("nomE"));
                    e.setDateE(LocalDate.parse(rs.getString("dateE")));
                    e.setPosteE(rs.getString("posteE"));
                    e.setPeriodeP(rs.getString("periodeE"));
                    e.setImgEpath(rs.getString("imgEpath"));

                    elections.add(e);
                }
        } catch (SQLException ex) {
            System.out.println("Error while searching for elections: " + ex.getMessage());
        }

        return elections;
    }*/


   /* public List<Election> searchElectionByNomStartingWithLetter(String searchAttribute, String startingLetter) throws SQLException {
        List<Election> elections = new ArrayList<>();

        //Connection connection = MyDataBase.getInstance().getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM evenement WHERE " + searchAttribute + " LIKE ?")) {

            // Check if the connection is still open before processing the result set
            if (!connection.isClosed()) {
                while (rs.next()) {
                    Election e = new Election();
                    e.setIdE(rs.getInt("idE"));
                    e.setNomE(rs.getString("nomE"));
                    e.setDateE(LocalDate.parse(rs.getString("dateE")));
                    e.setPosteE(rs.getString("posteE"));
                    e.setPeriodeP(rs.getString("periodeE"));
                    e.setImgEpath(rs.getString("imgEpath"));
                    elections.add(e);
                }
            } else {
                System.out.println("Connection is closed.");
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for elections: " + ex.getMessage());
        } finally {
            // Close the connection explicitly after use
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return elections;
    }
*/


    public Election getById(int id) throws SQLException {
        Election election = null;

        String sql = "SELECT * FROM candidat WHERE idC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Election e = new Election();
                election.setIdE(rs.getInt("idE"));
                election.setNomE(rs.getString("nomE"));
                election.setDateE(LocalDate.parse(rs.getString("dateE")));
                election.setPosteE(rs.getString("posteE"));
                election.setPeriodeP(rs.getString("periodeP"));
                election.setImgEpath(rs.getString("imgEpath"));

            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Candidat: " + ex.getMessage());
        }

        return election;
    }

}
