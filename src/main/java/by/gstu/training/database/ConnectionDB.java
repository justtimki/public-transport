package by.gstu.training.database;


import by.gstu.training.util.ConfigurationUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDB {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Parameters of database connection
     */
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static final String DB_POOLSIZE = "db.poolsize";

    private static ConnectionDB instance;
    private static List<Connection> freeConnections = new ArrayList<>();
    private String driverName;
    private String user;
    private String pass;
    private String url;
    private int poolSize;
    private Driver driver;
    private int count;

    /**
     * Constructor.
     * Gets values from database.properties and initialize variables.
     * Fill connection pool.
     */
    private ConnectionDB() {
        driverName = ConfigurationUtility.getValue(DB_DRIVER);
        user = ConfigurationUtility.getValue(DB_USER);
        pass = ConfigurationUtility.getValue(DB_PASSWORD);
        url = ConfigurationUtility.getValue(DB_URL);
        poolSize = Integer.parseInt(ConfigurationUtility.getValue(DB_POOLSIZE));
        try {
            driver = (Driver) Class.forName(driverName).newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            LOGGER.error("Error while registering driver! " + e.getMessage());
        }
        count = 0;
        Connection connection;
        for (int i = 0; i < poolSize; i++) {
            connection = newConnection();
            if (connection == null)
                return;
            freeConnections.add(connection);
        }
    }

    static synchronized public ConnectionDB getInstance() {
        if (instance == null) {
            LOGGER.info("Connection to DataBase...");
            instance = new ConnectionDB();
        }
        return instance;
    }

    /**
     * Method to get connections.
     *
     * @return Connection
     */
    public synchronized Connection getConnection() {
        Connection connection = null;
        if (freeConnections.size() > 0) {
            connection = freeConnections.get(0);
            freeConnections.remove(0);
            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                connection = getConnection();
            }
            LOGGER.trace("get connection");
            return connection;
        }
        if (count < poolSize) {
            connection = newConnection();
            if (connection != null) {
                LOGGER.info("New connection created.");
                count++;
            }
        }
        return connection;
    }

    /**
     * Method to create new connection object.
     *
     * @return Connection.
     */
    private Connection newConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return connection;
    }

    /**
     * Method to add free connections in to pool.
     *
     * @param connection
     */
    public synchronized void freeConnection(Connection connection) {
        freeConnections.add(connection);
        count--;
        notifyAll();
    }

    /**
     * Method to destroy all connections.
     */
    public void destroy() {
        closeAll();
        try {
            DriverManager.deregisterDriver(driver);
            LOGGER.debug("Destroy all connections.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Method to close all resources
     */
    private synchronized void closeAll() {
        for (Connection item : freeConnections) {
            try {
                item.close();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        freeConnections.clear();
    }

    @Override
    public void finalize() {
        try {
            super.finalize();
            destroy();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
