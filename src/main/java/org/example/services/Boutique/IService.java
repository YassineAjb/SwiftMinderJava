package org.example.services.Boutique;
import java.util.List;

import java.sql.SQLException;

public interface IService <T>{
    void ajouter (T t)throws SQLException;
    void modifier (T t)throws SQLException;
    void supprimer (int id)throws SQLException;
    List<T> afficher() throws SQLException;
}
