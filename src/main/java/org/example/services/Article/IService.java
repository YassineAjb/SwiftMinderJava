package org.example.services.Article;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void add(T t) throws SQLException;

    void modify(T t) throws SQLException;

    void remove(int id) throws SQLException;


    List<T> getall() throws SQLException;
}
