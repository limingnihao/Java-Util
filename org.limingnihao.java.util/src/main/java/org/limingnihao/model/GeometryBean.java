package org.limingnihao.model;

import java.io.Serializable;

public class GeometryBean implements Serializable {

    private double minLongitude;
    private double maxLongitude;
    private double minLatitude;
    private double maxLatitude;

    @Override
    public String toString() {
        return "GeometryBean{" +
                "minLongitude=" + minLongitude +
                ", maxLongitude=" + maxLongitude +
                ", minLatitude=" + minLatitude +
                ", maxLatitude=" + maxLatitude +
                '}';
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }
}
