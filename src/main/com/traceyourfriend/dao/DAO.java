package main.com.traceyourfriend.dao;

import org.codehaus.jettison.json.JSONArray;

public interface DAO <T>{

    //T add(T object) throws SQLException;

    //void remove(T object)  throws SQLException;

    //T merge(T object) throws SQLException;

    JSONArray findWithEmail(String email) throws Exception;

    JSONArray findWithName(String name) throws Exception;

    JSONArray findAll() throws Exception;

}
