package by.gstu.training.dao;

import by.gstu.training.database.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    ConnectionDB connectionDb = ConnectionDB.getInstance();

    default Connection getConnection() throws SQLException {
        final Connection connection = connectionDb.getConnection();
        if (connection == null) {
            throw new SQLException("Connection is null!");
        }
        return connection;
    }

    List<T> findAll();

    T findById(String id);

    void delete();

    void update(T object);

    void create(Class clazz);
}
