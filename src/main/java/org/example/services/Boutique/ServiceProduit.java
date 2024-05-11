package org.example.services.Boutique;

import org.example.models.Boutique.Produit;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ServiceProduit implements IService<Produit> {
    private Connection connection;

    public ServiceProduit(){
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Produit produit) throws SQLException {
        try {
            int exist = 0;
            Produit produit2 = new Produit();
            produit2.setQuantiteProduit(0);
            List<Produit> produits= new ArrayList<>();
            produits = this.afficher();

            for (Produit produit1 : produits)
            {if (produit1.getNomProduit().equals(produit.getNomProduit())&&produit1.getTailleProduit().equals(produit.getTailleProduit()))
                exist = 1;
                produit2 = produit1;


            }
            if (exist ==1)
            {
                int Quantite = produit.getQuantiteProduit()+produit2.getQuantiteProduit();
                System.out.println(Quantite);
                String sql = "Update produit set QauntiteProduit= ? where nomProduit = ?";
                PreparedStatement preparedStatement= connection.prepareStatement(sql);
                preparedStatement.setInt(1,Quantite);
                preparedStatement.setString(2, produit.getNomProduit());
                preparedStatement.executeUpdate();
                preparedStatement.close();



            }
            else
            {
                String sql = "INSERT INTO produit (nomProduit, prixProduit,type, tailleProduit, QauntiteProduit,image) VALUES (?, ?, ?, ?, ?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, produit.getNomProduit());
                statement.setInt(2, produit.getPrixProduit());
                statement.setString(3,produit.getType());
                statement.setString(4, produit.getTailleProduit());
                statement.setInt(5, produit.getQuantiteProduit());
                statement.setString(6, produit.getImage());

                statement.executeUpdate();
                statement.close();

                System.out.println("Produit inséré avec succès.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion du produit : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String sql = "Update produit set nomProduit = ?, prixProduit= ? , tailleProduit= ? , QauntiteProduit= ?,Type= ?,image= ? where IdProduit = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1,produit.getNomProduit());
        preparedStatement.setInt(2, produit.getPrixProduit());
        preparedStatement.setString(3,produit.getTailleProduit());
        preparedStatement.setInt(4,produit.getQuantiteProduit());
        preparedStatement.setInt(7,produit.getId());
        preparedStatement.setString(5,produit.getType());
        preparedStatement.setString(6,produit.getImage());
        preparedStatement.executeUpdate();
//        pr.modifier(new Produit(idProduit, Type.getValue(),NomProduit.getText(),Integer.parseInt(Prix.getText()),Taille.getValue(),Integer.parseInt(Quantite.getText())));
//        System.out.println("helloo");

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "Delete from produit where IdProduit = ? ";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();

    }
    public int existence(String taille, String nomProduit) throws SQLException {
        int quantite = 0;
        String sql = "SELECT * FROM produit " +
                "WHERE tailleproduit = ? AND nomProduit = ?";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, taille);
        preparedStatement.setString(2, nomProduit);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Vérifie si le résultat contient une ligne
        if (resultSet.next()) {
            // Si oui, récupère la quantité de produit
            quantite = resultSet.getInt("QauntiteProduit");
        }

        return quantite;
    }

    @Override
    public List<Produit> afficher() throws SQLException {
        List<Produit> produits= new ArrayList<>();
        String sql = "select * from produit";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Produit p = new Produit();
            p.setId(rs.getInt("IdProduit"));
            p.setNomProduit(rs.getString("NomProduit"));
            p.setPrixProduit(rs.getInt("PrixProduit"));
            p.setType(rs.getString("Type"));
            p.setQuantiteProduit(rs.getInt("QauntiteProduit"));
            p.setTailleProduit(rs.getString("TailleProduit"));
            p.setImage(rs.getString("image"));
            produits.add(p);
        }
        return produits;
    }

    public List<Produit> afficherStore() throws SQLException{
        List<Produit> produits = afficher();
        List<Produit> prod = new ArrayList<>();

        // Utiliser un HashSet pour garder une trace des noms de produits déjà rencontrés
        Set<String> nomsDejaRencontres = new HashSet<>();

        for (Produit produit : produits) {
            // Vérifier si le nom du produit a déjà été rencontré
            if (!nomsDejaRencontres.contains(produit.getNomProduit())) {
                // Si le nom n'a pas été rencontré, ajouter le produit à la liste et mettre à jour le HashSet
                prod.add(produit);
                nomsDejaRencontres.add(produit.getNomProduit());
            }
        }

        return prod;


    }
    public String afficherUnProduit(int idProd) throws SQLException {
        String sql = "SELECT nomProduit FROM produit WHERE idProduit = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idProd);

        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            String nomProduit = rs.getString("nomProduit");
            return nomProduit;
            
        }
        else {
            return "Produit non trouvé";
        }
    }
    public Produit afficherunProduit(int idProd) throws SQLException {
        String sql = "SELECT * FROM produit WHERE idProduit = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idProd);

        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            Produit p = new Produit();
            p.setId(rs.getInt("idProduit"));
            p.setNomProduit(rs.getString("NomProduit"));
            p.setPrixProduit(rs.getInt("PrixProduit"));
            p.setType(rs.getString("Type"));
            p.setQuantiteProduit(rs.getInt("QauntiteProduit"));
            p.setTailleProduit(rs.getString("TailleProduit"));
            p.setImage(rs.getString("image"));
            return p;
        }

        return null;
    }


    public List filtre(String nomProduit) throws SQLException {
        String sql = "SELECT * FROM produit WHERE nomProduit = ?";
        List<Produit> produits= new ArrayList<>();


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nomProduit);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Produit p = new Produit();
            p.setId(rs.getInt("IdProduit"));
            p.setNomProduit(rs.getString("NomProduit"));
            p.setPrixProduit(rs.getInt("PrixProduit"));
            p.setType(rs.getString("Type"));
            p.setQuantiteProduit(rs.getInt("QauntiteProduit"));
            p.setTailleProduit(rs.getString("TailleProduit"));
            p.setImage(rs.getString("image"));
            produits.add(p);


        }
       return produits;
    }

}
