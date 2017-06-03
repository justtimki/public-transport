package by.gstu.training.dao.station;

import by.gstu.training.model.Station;
import by.gstu.training.util.CloseUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alexandr Kolymago on 6/3/2017.
 */
public class StationDaoImpl implements StationDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String STATION_TABLE_NAME = "station";
    private static final String STATION_ID = "station_id";
    private static final String STATION_TITLE = "station_title";

    private static final String SELECT_BY_ID = "SELECT * FROM " + STATION_TABLE_NAME + " WHERE " + STATION_ID + "=?;";

    @Override
    public List<Station> findAll() {
        return null;
    }

    @Override
    public Station findById(String id) {
        Station station = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = getConnection();
            st = connection.prepareStatement(SELECT_BY_ID);
            st.setInt(1, Integer.parseInt(id));
            rs = st.executeQuery();
            while (rs.next()) {
                station = new Station();
                station.setId(rs.getString(STATION_ID));
                station.setTitle(rs.getString(STATION_TITLE));
            }
            if (station == null) {
                LOGGER.warn("Can't find record with id [" + id + "]!");
            }
        } catch (SQLException e) {
            LOGGER.error("Error while finding account with id " + id);
            LOGGER.error(e.getMessage());
        } finally {
            if (st != null)
                CloseUtility.close(st);
            if (rs != null)
                CloseUtility.close(rs);
            if (connection != null)
                CloseUtility.close(connection);
        }
        return station;
    }

    @Override
    public void delete() {

    }

    @Override
    public void update(Station object) {

    }

    @Override
    public void create(Class clazz) {

    }
}
