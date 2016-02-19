package main.com.traceyourfriend.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T>{

    T add(T object) throws SQLException;

    void remove(T object)  throws SQLException;

    T merge(T object) throws SQLException;

    T find(long id) throws SQLException;

    T find(String name) throws SQLException;

    List<T> findAll() throws SQLException;

}
