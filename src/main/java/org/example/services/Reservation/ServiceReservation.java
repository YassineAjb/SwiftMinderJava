package org.example.services.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Reservation.Reservation;
import org.example.models.Reservation.Terrain;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation {

    private Connection connection;
    private final MyDataBase myDataBase;

    public ServiceReservation() {
        this.myDataBase = MyDataBase.getInstance();
        this.connection = myDataBase.getConnection(); // Initialize connection here
    }

    public ObservableList<Reservation> getAllReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        String query = "SELECT * FROM reservations";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(resultSet.getInt("reservationID"));
                reservation.setChoixTerrain(String.valueOf(resultSet.getInt("idTerrain")));
                reservation.setDateReservation(resultSet.getString("dateReservation"));
                reservation.setNote(resultSet.getString("note"));
                reservation.setEmplacement(resultSet.getString("emplacement"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }


    public void addReservation(Reservation reservation,String choixTerrain) {
        ServiceTerrain serviceTerrain = new ServiceTerrain();
        String query = "INSERT INTO reservation (idTerrain, dateReservation, note, emplacement) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, serviceTerrain.afficherTerrain(choixTerrain));
            preparedStatement.setString(2, reservation.getDateReservation());
            preparedStatement.setString(3, reservation.getNote());
            preparedStatement.setString(4, reservation.getEmplacement());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> afficher() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select * from reservation";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Reservation p = new Reservation();
            Terrain terrain = new Terrain();
            p.setReservationID(rs.getInt("ReservationID"));
            p.setChoixTerrain(String.valueOf(rs.getInt("idTerrain")));
            p.setDateReservation(rs.getString("DateReservation"));
            p.setNote(rs.getString("Note"));
            p.setEmplacement(rs.getString("Emplacement"));

            reservations.add(p);
        }

        return reservations;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "Delete from reservation where ReservationID = ? ";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }


    public void modifier(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET Choixterrain = ?, DateReservation = ?, Note = ?, Emplacement = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, reservation.getChoixTerrain());
        preparedStatement.setString(2, reservation.getDateReservation());
        preparedStatement.setString(3, reservation.getNote());
        preparedStatement.setString(4, reservation.getEmplacement());
        preparedStatement.executeUpdate();
    }


}
