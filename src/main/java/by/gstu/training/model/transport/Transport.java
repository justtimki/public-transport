package by.gstu.training.model.transport;


import by.gstu.training.model.Station;

import java.time.LocalDateTime;

/**
 * Represent base transport.
 */
public class Transport {

    private String id;
    private int number;
    private LocalDateTime arrivalTime;
    private Station startStation;
    private Station finalStation;

    public Transport() {
    }

    public Transport(String id, int number, LocalDateTime arrivalTime, Station startStation, Station finalStation) {
        this.id = id;
        this.number = number;
        this.arrivalTime = arrivalTime;
        this.startStation = startStation;
        this.finalStation = finalStation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(Station finalStation) {
        this.finalStation = finalStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transport transport = (Transport) o;

        if (number != transport.number) return false;
        if (id != null ? !id.equals(transport.id) : transport.id != null) return false;
        return arrivalTime != null ? arrivalTime.equals(transport.arrivalTime) : transport.arrivalTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", arrivalTime=" + arrivalTime +
                ", startStation=" + startStation +
                ", finalStation=" + finalStation +
                '}';
    }
}
