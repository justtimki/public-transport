package by.gstu.training.util;

import by.gstu.training.database.ConnectionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseUtility {
    private static final Logger LOGGER = LogManager.getLogger();

    private CloseUtility() {
    }

    public static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close statement!");
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close result set!");
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection cn) {
        if (cn != null) {
            try {
                cn.close();
                ConnectionDB.getInstance().freeConnection(cn);
            } catch (SQLException e) {
                LOGGER.error("Can't close connection!");
                e.printStackTrace();
            }
        }
    }
}
