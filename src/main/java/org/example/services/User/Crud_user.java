package org.example.services.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.User.User;
import org.example.utils.MyDataBase;

import java.sql.*;


public class Crud_user {
    private Connection connection;


    public Crud_user() {
      connection= MyDataBase.getInstance().getConnection();

    }
    /*public void createUser(User user) {
        try {
            String sql = "INSERT INTO user (`email`, `mot_de_passe`, `date_creation`,`role`,`NumTel`) VALUES (?,?,?,?,?)";
            PreparedStatement st = this.connection.prepareStatement(sql);
            st.setString(1, user.getEmail());
            st.setString(2, user.getMot_de_passe());
            st.setDate(3,  new java.sql.Date(user.getDate_creation().getTime()));
            st.setString(4, user.getRole());
            st.setString(5, user.getNumTel());
            st.executeUpdate();
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }*/
    public void createUser(User user) {
        try {
            String sql = "INSERT INTO user (`email`, `motdepasse`,`role`,`NumTel`) VALUES (?,?,?,?)";
            PreparedStatement st = this.connection.prepareStatement(sql);
            st.setString(1, user.getEmail());
            st.setString(2, user.getMot_de_passe());

            st.setString(3, user.getRole());
            st.setString(4, user.getNumTel()); // Ensure NumTel is not null
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int id)  {
   try {
    String sql = "delete from user where iduser = ?";
    PreparedStatement st = connection.prepareStatement(sql);


    st.setInt(1, id);
    st.executeUpdate();
   }catch (SQLException E)
{
    System.out.println(E.getMessage());
}}
    public boolean modifier(User usr)  {
        try {
            String sql = "Update user set email = ?, motdepasse= ? , role= ?,NumTel= ? where iduser = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usr.getEmail());
            preparedStatement.setString(2, usr.getMot_de_passe());
            preparedStatement.setString(3, usr.getRole());
            preparedStatement.setString(4,usr.getNumTel());
            preparedStatement.setInt(5,usr.getId());


            return  preparedStatement.executeUpdate()!=0;

        }catch (SQLException E)
        { System.out.println(E.getMessage());
    }
    return  false;
    }
    ObservableList<User> users = FXCollections.observableArrayList();
    public ObservableList<User> afficher() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM user";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt("iduser"));
                p.setEmail(rs.getString("email"));
                p.setMot_de_passe(rs.getString("motdepasse"));

                p.setRole(rs.getString("role"));
                p.setNumTel( rs.getString("NumTel"));

                users.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }


    /*
    public ObservableList<User> afficher() {
        ObservableList<User> users=FXCollections.observableArrayList();
         try {
            String sql = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setEmail(rs.getString(2));
                p.setMot_de_passe(rs.getString(3));
                p.setDate_creation(rs.getDate(4));
                p.setRole(rs.getString(5));
                p.setNumTel(rs.getString(6));
                users.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return users;

    }*/
    public User Login(String email) {
       User user=new User();
        try {
            String sql = "SELECT * FROM user where email=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                user.setId(rs.getInt("iduser"));
                user.setEmail(rs.getString("email"));
                user.setMot_de_passe(rs.getString("motdepasse"));

                user.setRole(rs.getString("role"));
                user.setNumTel(rs.getString("NumTel"));

                return user;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;

    }
    public boolean isUserExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public User getUserByNumTell(String Numtell) {
        String sql = "SELECT * FROM user WHERE NumTel = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Numtell);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("iduser"));
                user.setEmail(rs.getString("email"));
                user.setMot_de_passe(rs.getString("motdepasse"));

                user.setRole(rs.getString("role"));
                user.setNumTel( rs.getString("NumTel"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



}
