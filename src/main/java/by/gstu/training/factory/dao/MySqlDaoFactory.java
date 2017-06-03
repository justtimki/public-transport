package by.gstu.training.factory.dao;

import by.gstu.training.dao.station.StationDao;
import by.gstu.training.dao.station.StationDaoImpl;
import by.gstu.training.dao.transport.TransportDao;
import by.gstu.training.dao.transport.TransportDaoImpl;

public class MySqlDaoFactory extends DaoFactory {

    @Override
    public TransportDao createTransportDao() {
        return new TransportDaoImpl();
    }

    @Override
    public StationDao createStationDao() {
        return new StationDaoImpl();
    }
}
