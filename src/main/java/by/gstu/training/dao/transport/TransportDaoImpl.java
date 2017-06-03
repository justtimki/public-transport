package by.gstu.training.dao.transport;

import by.gstu.training.dao.station.StationDao;
import by.gstu.training.factory.dao.DaoFactory;
import by.gstu.training.factory.dao.MySqlDaoFactory;
import by.gstu.training.factory.transport.BusFactory;
import by.gstu.training.factory.transport.TransportFactory;
import by.gstu.training.model.Station;
import by.gstu.training.model.transport.Transport;
import by.gstu.training.util.CloseUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransportDaoImpl implements TransportDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TRANSPORT_ID = "transport_id";
    private static final String TRANSPORT_NUMBER = "transport_number";
    private static final String TRANSPORT_ARRIVAL_TIME = "transport_arrival_time";

    private static final String TRANSPORT_START_STATION_ID = "transport_start_station_id";
    private static final String TRANSPORT_FINISH_STATION_ID = "transport_finish_station_id";
    private static final String STATION_ID = "station_id";
    private static final String TRANSPORT_TABLE_NAME = "transport";
    private static final String STATION_TABLE_NAME = "station";


    private static final String SELECT_ALL = "SELECT * FROM " + TRANSPORT_TABLE_NAME +
            " INNER JOIN " + STATION_TABLE_NAME + " ON " + TRANSPORT_TABLE_NAME + "." + TRANSPORT_START_STATION_ID + " = " + STATION_TABLE_NAME + "." + STATION_ID +
            " AND " + TRANSPORT_TABLE_NAME + "." + TRANSPORT_FINISH_STATION_ID + " = " + STATION_TABLE_NAME + "." + TRANSPORT_ID + ";";


    private TransportFactory factory = new BusFactory();
    private DaoFactory daoFactory = new MySqlDaoFactory();
    private StationDao stationDao = daoFactory.createStationDao();

    @Override
    public List<Transport> findAll() {
        final List<Transport> transports = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = getConnection();
            st = connection.prepareStatement(SELECT_ALL, Statement.RETURN_GENERATED_KEYS);
            rs = st.executeQuery();
            while (rs.next()) {
                final Transport transport = factory.createTransport();
                final String id = String.valueOf(rs.getInt(TRANSPORT_ID));
                transport.setId(id);
                transport.setArrivalTime(LocalDateTime.of(rs.getDate(TRANSPORT_ARRIVAL_TIME).toLocalDate(), LocalTime.MIN));
                transport.setNumber(rs.getInt(TRANSPORT_NUMBER));
                final String startStationId = String.valueOf(rs.getInt(TRANSPORT_START_STATION_ID));
                final Station startStation = stationDao.findById(startStationId);
                if (startStation != null) {
                    transport.setStartStation(startStation);
                }
                final String finalStationId = String.valueOf(rs.getInt(TRANSPORT_FINISH_STATION_ID));
                final Station finalStation = stationDao.findById(finalStationId);
                if (finalStation != null) {
                    transport.setFinalStation(finalStation);
                }
                transports.add(transport);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while finding all accounts.");
            LOGGER.error(e.getMessage());
        } finally {
            if (st != null) {
                CloseUtility.close(st);
            }
            if (rs != null) {
                CloseUtility.close(rs);
            }
            if (connection != null) {
                CloseUtility.close(connection);
            }
        }
        return transports;
    }

    @Override
    public Transport findById(String id) {
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public void update(Transport object) {

    }

    @Override
    public void create(Class clazz) {

    }
}
