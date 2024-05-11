package org.example.services.Boutique;

import org.example.models.Boutique.Commande;
import org.example.models.Boutique.Produit;
import org.example.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande{
    private Connection connection;

    public ServiceCommande(){
        connection = MyDataBase.getInstance().getConnection();
    }


    public void ajouter(Commande commande) throws SQLException {
        try {
            String sql = "INSERT INTO commande (idUser,Somme,idProduit,quantite) VALUES (?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, commande.getIdUser());
            statement.setInt(2, commande.getSomme());
            statement.setInt(3, commande.getProduit().getId());
            statement.setInt(4, commande.getQuantite());


            statement.executeUpdate();
            statement.close();


            System.out.println("Commande inséré avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion du produit : " + e.getMessage());
        }
    }
    public void modifier(Commande commande) {
        String sql = "UPDATE commande SET idUser = ?, Somme = ?, idProduit = ?, quantite = ? WHERE idProduit = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, commande.getIdUser());
            preparedStatement.setInt(2, commande.getSomme());
            preparedStatement.setInt(3, commande.getProduit().getId());
            preparedStatement.setInt(4, commande.getQuantite());
            preparedStatement.setInt(5, commande.getProduit().getId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " lignes ont été modifiées.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la commande : " + e.getMessage());
            e.printStackTrace(); // Affichez la trace complète de l'exception pour un débogage approfondi.
        }
    }


    public List<Commande> afficher(int idUser) throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE idUser = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idUser);
        ServiceProduit serviceProduit = new ServiceProduit();

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Commande c = new Commande();
            c.setIdUser(rs.getInt("idUser"));
            c.setIdCommande(rs.getInt("idCommande"));
            c.setSomme(rs.getInt("Somme"));
            c.setQuantite(rs.getInt("Quantite"));

            int productId = rs.getInt("idProduit");
            Produit produit = serviceProduit.afficherunProduit(productId);
            c.setProduit(produit);

            commandes.add(c);
        }

        return commandes;
    }

    public int calculSomme(int idUser) throws SQLException {
        List<Commande> commandes= new ArrayList<>();
        int somme = 0;

        commandes = this.afficher(idUser);
        for (int i = 0;i<commandes.size();i++)
        {
            somme+=commandes.get(i).getSomme()*commandes.get(i).getQuantite();

        }
        return somme;
    }
    public void supprimer(int id) throws SQLException {
        String sql = "Delete from commande where idCommande = ? ";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();

    }
    public void supprimerTout(int idUser) throws SQLException {
        String sql = "DELETE FROM commande where idUser= ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,idUser);

        preparedStatement.executeUpdate();
    }
    public int exist(int idProduit) throws SQLException
    {
        int quantite = 0;
        String sql = "SELECT * FROM commande " +
                "WHERE idProduit = ?";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idProduit);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Vérifie si le résultat contient une ligne
        if (resultSet.next()) {
            // Si oui, récupère la quantité de produit
            quantite = resultSet.getInt("quantite");
        }

        return quantite;
    }





}
