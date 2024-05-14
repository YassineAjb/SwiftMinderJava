package org.example.services.Election;

        import org.example.models.Election.Candidat;
        import org.example.utils.MyDataBase;

        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;

public class CandidatService implements IService<Candidat> {

    private Connection connection;

    public CandidatService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Candidat candidat) throws SQLException {
        String sql = "INSERT INTO candidat (nomC, prenomC, ageC,imgCpath, idElection) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, candidat.getNomC());
            preparedStatement.setString(2, candidat.getPrenomC());
            preparedStatement.setInt(3, candidat.getAgeC());
            preparedStatement.setString(4, candidat.getImgCpath());
            preparedStatement.setInt(5, candidat.getIdElection());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Candidat candidat) throws SQLException {
        String sql = "update candidat set nomC = ?, prenomC = ?, ageC = ?,imgCpath = ?,idElection = ? where idC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, candidat.getNomC());
            preparedStatement.setString(2, candidat.getPrenomC());
            preparedStatement.setInt(3, candidat.getAgeC());
            preparedStatement.setString(4, candidat.getImgCpath());
            preparedStatement.setInt(5, candidat.getIdElection());
            preparedStatement.setInt(6, candidat.getIdC());

            preparedStatement.executeUpdate();
        }
    }
    public void modifierCC(String nomC, String prenomC, int ageC, String imgCpath,int idElection,int idC) throws SQLException {
        int idENouv = 0;
        String sql = "update candidat set nomC = ?, prenomC = ?, ageC = ? ,imgCpath = ?,idElection = ? where idC = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, nomC);
        preparedStatement.setString(2,prenomC);
        preparedStatement.setInt(3,ageC);
        preparedStatement.setString(4, imgCpath);
        preparedStatement.setInt(5,idElection);
        System.out.println(idENouv + "idElection : ou on va modifier le candidat");

        preparedStatement.setInt(6,idC);

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idC) throws SQLException {
        String sql = "delete from candidat where idC = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idC);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Candidat> recuperer() throws SQLException {
        String sql = "SELECT * FROM candidat";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Candidat> candidats = new ArrayList<>();
        while (rs.next()) {
            Candidat p = new Candidat();
            p.setIdC(rs.getInt("idC"));
            p.setNomC(rs.getString("nomC"));
            p.setPrenomC(rs.getString("prenomC"));
            p.setAgeC(rs.getInt("ageC"));
            p.setImgCpath(rs.getString("imgCpath"));
            p.setIdElection(rs.getInt("idElection"));

            candidats.add(p);
        }
        return candidats;
    }


    public List<Candidat> recupererC(int idElection) throws SQLException {
        String sql = "SELECT * FROM candidat where idElection = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idElection);
            ResultSet rs = preparedStatement.executeQuery();

            List<Candidat> candidats = new ArrayList<>();
            while (rs.next()) {
                Candidat p = new Candidat();
                p.setIdC(rs.getInt("idC"));
                p.setNomC(rs.getString("nomC"));
                p.setPrenomC(rs.getString("prenomC"));
                p.setAgeC(rs.getInt("ageC"));
                p.setImgCpath(rs.getString("imgCpath"));
                p.setIdElection(rs.getInt("idElection"));

                candidats.add(p);
            }

            return candidats;
        }
    }



    public List<Candidat> searchCandidatByNomStartingWithLetter(String searchAttribute,String startingLetter) throws SQLException{
        List<Candidat> candidats = new ArrayList<>();

        String sql = "SELECT * FROM candidat WHERE " + searchAttribute + " LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, startingLetter + "%");
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                Candidat e = new Candidat();
                e.setIdC(rs.getInt("idC"));
                e.setNomC(rs.getString("nomC"));
                e.setPrenomC(rs.getString("prenomC"));
                e.setAgeC(rs.getInt("ageC"));
                e.setImgCpath(rs.getString("imgCpath"));
                e.setIdElection(rs.getInt("idElection"));

                candidats.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Candidats !!" + ex.getMessage());
        }

        return candidats;
    }



    public int getIdEbynom(String nomE) throws SQLException {
        int x = 0;
        String sql = "SELECT * FROM evenement WHERE nomE LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + nomE + "%");  // Assuming you want a partial match, adjust as needed
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                x = rs.getInt("idE");
                // Additional code to retrieve other attributes if needed
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for elections by name: " + ex.getMessage());
        }
        System.out.println(x + "id election par nom");
        return x;
    }





    public Candidat getById(int id) throws SQLException {
        Candidat candidat = null;

        String sql = "SELECT * FROM candidat WHERE idC = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                candidat = new Candidat();
                candidat.setIdC(rs.getInt("idC"));
                candidat.setNomC(rs.getString("nomC"));
                candidat.setPrenomC(rs.getString("prenomC"));
                candidat.setAgeC(rs.getInt("ageC"));
                candidat.setImgCpath(rs.getString("imgCpath"));
                candidat.setIdElection(rs.getInt("idElection"));
            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Candidat: " + ex.getMessage());
        }

        return candidat;
    }

    public List<Candidat> recupererCandidatsbyIdE(int idElection) throws SQLException {
        List<Candidat> candidats = new ArrayList<>();

        String sql = "SELECT * FROM candidat WHERE idElection = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idElection);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                Candidat e = new Candidat();
                e.setIdC(rs.getInt("idC"));
                e.setNomC(rs.getString("nomC"));
                e.setPrenomC(rs.getString("prenomC"));
                e.setAgeC(rs.getInt("ageC"));
                e.setImgCpath(rs.getString("imgCpath"));
                e.setIdElection(rs.getInt("idElection"));

                candidats.add(e);
                System.out.println(e);

            }
        } catch (SQLException ex) {
            System.out.println("Error while searching for Candidats !!" + ex.getMessage());
        }

        return candidats;
    }




}
