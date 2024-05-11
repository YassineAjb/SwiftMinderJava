package org.example.services.Article;


import org.example.models.Article.Match;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchService implements IService<Match> {

    private final Connection connection;

    public MatchService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Match m) throws SQLException {
        String sql = "INSERT INTO rencontre (Adversaire, DateRencontre, Score) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, m.getAdversaireMatch());
        statement.setDate(2, m.getDateMatch());
        statement.setString(3, m.getScoreMatch());

        statement.executeUpdate();

    }

    @Override
    public void modify(Match m) throws SQLException {
        String sql = "UPDATE rencontre SET Adversaire = ?, DateRencontre = ?, Score = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, m.getAdversaireMatch());
        preparedStatement.setDate(2, m.getDateMatch());
        preparedStatement.setString(3, m.getScoreMatch());
        preparedStatement.setInt(4, m.getIdMatch());
        preparedStatement.executeUpdate();
    }

    @Override
    public void remove(int id) throws SQLException {
        String sql = "delete from rencontre where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Match> getall() throws SQLException {
        String sql = "select * from rencontre";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Match> match = new ArrayList<>();
        while (rs.next()) {
            Match m = new Match();

            m.setIdMatch(rs.getInt("id"));
            m.setAdversaireMatch (rs.getString("Adversaire"));
            m.setDateMatch(rs.getDate("DateRencontre"));
            m.setScoreMatch(rs.getString("Score"));


            match.add(m);
        }
        return match;
    }
    public List<Match> searchByAdversaire(String searchQuery) throws SQLException {
        List<Match> listOfMatches = new ArrayList<>();

        // Your implementation to search matches by adversary
        // Populate the listOfMatches based on the searchQuery

        return listOfMatches;
    }




}
