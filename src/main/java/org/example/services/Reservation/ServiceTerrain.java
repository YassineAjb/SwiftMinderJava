package org.example.services.Reservation;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import org.example.models.Reservation.Terrain;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTerrain implements IService<Terrain> {
    private Connection connection;
    public ServiceTerrain() {connection = MyDataBase.getInstance().getConnection();}
    @FXML
    private ImageView Retour;


    @Override
    public void ajouter(Terrain terrain) throws SQLException {
        String insertQuery = "INSERT INTO terrain (nom_terrain, adresse, description, geo_x, geo_y, ouverture, fermeture , datedispo) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, terrain.getNomTerrain());
            preparedStatement.setString(2, terrain.getAdresse());
            preparedStatement.setString(3, terrain.getDescription());
            preparedStatement.setDouble(4, terrain.getGeoX());
            preparedStatement.setDouble(5, terrain.getGeoY());
            preparedStatement.setString(6, terrain.getOuverture());
            preparedStatement.setString(7, terrain.getFermeture());
            preparedStatement.setString(8, terrain.getDatedispo());


            preparedStatement.executeUpdate();
            System.out.println("Terrain ajouté !!  ");



        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception to indicate failure
        }
    }

    @Override
    public void modifier(Terrain terrain) throws SQLException {
        String sql = "UPDATE terrain SET nom_terrain = ?, adresse = ?, description = ?, geo_x = ?, geo_y = ?, ouverture = ?, fermeture = ?, datedispo = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, terrain.getNomTerrain());
        preparedStatement.setString(2, terrain.getAdresse());
        preparedStatement.setString(3, terrain.getDescription());
        preparedStatement.setDouble(4, terrain.getGeoX());
        preparedStatement.setDouble(5, terrain.getGeoY());
        preparedStatement.setString(6, terrain.getOuverture());
        preparedStatement.setString(7, terrain.getFermeture());
        preparedStatement.setString(8, terrain.getDatedispo());
        preparedStatement.setInt(9, terrain.getId());
        preparedStatement.executeUpdate();
    }
    public int afficherTerrain(String nomTerrain) throws SQLException {
        int id = 0;
        String sql = "SELECT id FROM terrain WHERE nom_terrain = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nomTerrain);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        }

        return id;
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "Delete from terrain where id = ? ";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Terrain> afficher() throws SQLException {
        List<Terrain> terrains= new ArrayList<>();
        String sql = "select * from terrain";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            Terrain p = new Terrain();
            p.setId(rs.getInt("id"));
            p.setNomTerrain(rs.getString("Nom_Terrain"));
            p.setAdresse(rs.getString("adresse"));
            p.setDescription(rs.getString("description"));
            p.setOuverture(rs.getString("ouverture"));
            p.setFermeture(rs.getString("fermeture"));
            terrains.add(p);

        }

        return terrains;
    }



}
