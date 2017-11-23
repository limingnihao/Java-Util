package org.limingnihao.util;

import org.limingnihao.model.GeometryBean;

import java.util.HashMap;
import java.util.Map;

public class GeometryUtil {

	/**
	 * 默认地球半径(km)
	 */
    private static double EARTH_RADIUS = 6371;

    /**
     * 根据gps位置和距离，计算出一个方形
     * @param longitude
     * @param latitude
     * @param distance  单位千米
     * @return
     */
    public static GeometryBean getSquare(double longitude, double latitude, double distance) {
        GeometryBean bean = new GeometryBean();

        // 计算经度弧度,从弧度转换为角度
        double dLongitude = 2 * (Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(Math.toRadians(latitude))));
        dLongitude = Math.toDegrees(dLongitude);

        // 计算纬度角度
        double dLatitude = distance / EARTH_RADIUS;

        dLatitude = Math.toDegrees(dLatitude);

        // 经度
        double minLongitude = longitude - dLongitude;

        double maxLongitude = longitude + dLongitude;

        //纬度
        double minLatitude = latitude - dLatitude;

        double maxLatitude = latitude + dLatitude;

        bean.setMinLongitude(minLongitude);
        bean.setMaxLongitude(maxLongitude);

        bean.setMinLatitude(minLatitude);
        bean.setMaxLatitude(maxLatitude);
        return bean;
    }


    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
     *
     * @param lon1 第一点的经度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的经度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS * 1000;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 转化为弧度(rad)
     */
    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
