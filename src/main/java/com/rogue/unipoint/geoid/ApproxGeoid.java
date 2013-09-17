package com.rogue.unipoint.geoid;

import static com.google.common.base.Preconditions.*;
import com.rogue.unipoint.LatLonPoint;
import com.rogue.unipoint.LatLonVector;

/**
 * A low accuracy approximation geoid using the original meter definition
 * of 10^7 meters in the distance from the equator to the north pole.
 * 
 * @author R. Matt McCann
 */
public class ApproxGeoid implements Geoid {
    private static final ApproxGeoid instance = new ApproxGeoid();
    
    private final double magicNumber = 111111.0;
    
    private ApproxGeoid() { }
    
    @Override
    public double convertLatToMeters(double lat) {
        return lat * magicNumber;
    }

    @Override
    public double convertLonToMeters(double lon, double lat) {
        return lon * magicNumber * Math.cos(lat);
    }
    
    public static ApproxGeoid getInstance() {
        return instance;
    }
    
    @Override
    public double inMeters(LatLonVector vector) {
        checkNotNull(vector != null, "Vector must not be null!");

        double y = Math.pow((vector.to().lat() - vector.from().lat()) * magicNumber, 2);
        double x = Math.pow((vector.to().lon() - vector.from().lon()) 
                * magicNumber * Math.cos(vector.from().lat()), 2);
        return Math.sqrt(x + y);
    }
    
    @Override
    public LatLonPoint plusLatInMeters(LatLonPoint point, double distance) {
        checkArgument(point != null, "Point must not be null!");
        
        double lat = point.lat() + distance / magicNumber;
        
        return point.withLat(lat);
    }

    @Override
    public LatLonPoint plusLonInMeters(LatLonPoint point, double distance) {
        checkArgument(point != null, "Point must not be null!");
        
        double lon = point.lon() + distance / (magicNumber * Math.cos(point.lat()));
        
        return point.withLon(lon);
    }
}
