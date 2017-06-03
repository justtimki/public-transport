package by.gstu.training.factory.dao;

import by.gstu.training.dao.station.StationDao;
import by.gstu.training.dao.transport.TransportDao;

public abstract class DaoFactory {
    public abstract TransportDao createTransportDao();
    public abstract StationDao createStationDao();
}
