package com.v1.attendance.utils;

import com.v1.attendance.models.entities.LocalAddress;

public class LocatorAddress {
    public double distanceBetweenPoints(LocalAddress localAddress1, LocalAddress localAddress2) {

        // Radius of the earth
        int r = 6371;

        double latitude1 = localAddress1.getLatitude();
        double longitude1 = localAddress1.getLongitude();
        double latitude2 = localAddress2.getLatitude();
        double longitude2 = localAddress1.getLongitude();

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 -longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude1))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // convert to meters
        return  r * c * 1000;
    }
}
