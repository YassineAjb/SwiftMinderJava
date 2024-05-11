package org.example.services.Employe;

import com.dlsc.gemsfx.daterange.DateRange;
import com.dlsc.gemsfx.daterange.DateRangePicker;
import org.example.models.Employe.Contrat;
import org.example.models.Employe.Joueur;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceContrat implements IService<Contrat> {
    private Connection connection;

    public ServiceContrat() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Contrat contrat){
        try {
            String sql = "INSERT INTO `contrat`(`employee_id`, `date_debut`, `date_fin`, `salaire`) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contrat.getJoueur().getId());
            preparedStatement.setDate(2, Date.valueOf(contrat.getdate().getValue().getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(contrat.getdate().getValue().getEndDate()));
            preparedStatement.setInt(4, contrat.getSalaire());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Contrat contrat) {
        try {
            String sql = "UPDATE contrat SET employee_id=?, date_debut=?, date_fin=?, salaire=? WHERE contrat_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contrat.getJoueur().getId());
            preparedStatement.setDate(2, Date.valueOf(contrat.getdate().getValue().getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(contrat.getdate().getValue().getEndDate()));
            preparedStatement.setInt(4, contrat.getSalaire());
            preparedStatement.setInt(5, contrat.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            } else {
                System.out.println("No rows affected. ID not found or values unchanged.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `contrat` WHERE contrat_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Contrat> afficher() throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String sql = "SELECT * FROM contrat";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Contrat contrat = new Contrat();
            Joueur joueur = new Joueur();
            contrat.setJoueur(joueur);
            contrat.setId(rs.getInt("contrat_id"));
            contrat.getJoueur().setId(rs.getInt("employee_id"));
            DateRangePicker date = new DateRangePicker();
            LocalDate localStartDate = LocalDate.of(rs.getDate("date_debut").getYear() + 1900, rs.getDate("date_debut").getMonth() + 1, rs.getDate("date_debut").getDay()+1);
            LocalDate localEndDate = LocalDate.of(rs.getDate("date_fin").getYear() + 1900, rs.getDate("date_fin").getMonth() + 1, rs.getDate("date_fin").getDay()+1);
            DateRange daterange = new DateRange(localStartDate,localEndDate);
            date.setValue(daterange);
            contrat.setdate(date);
            contrat.setSalaire(rs.getInt("salaire"));

            contrats.add(contrat);
        }
        return contrats;
    }
}