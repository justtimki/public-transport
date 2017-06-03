package by.gstu.training.model;

import by.gstu.training.model.transport.Transport;

import java.util.List;

public class Route {

    private String id;
    private List<Station> stations;
    private List<Transport> transports;

    public Route() {
    }

    public Route(String id, List<Station> stations, List<Transport> transports) {
        this.id = id;
        this.stations = stations;
        this.transports = transports;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        this.transports = transports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != null ? !id.equals(route.id) : route.id != null) return false;
        if (stations != null ? !stations.equals(route.stations) : route.stations != null) return false;
        return transports != null ? transports.equals(route.transports) : route.transports == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stations != null ? stations.hashCode() : 0);
        result = 31 * result + (transports != null ? transports.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", stations=" + stations +
                ", transports=" + transports +
                '}';
    }
}
