package com.rogue.unipoint.geoid;

import com.rogue.unipoint.LatLonPoint;
import com.rogue.unipoint.LatLonVector;

/**
 * Model of the earth used for position calculations.
 * 
 * @author R. Matt McCann
 */
public interface Geoid {
    double convertLatToMeters(double lat);
    
    double convertLonToMeters(double lon, double lat);
    
    double inMeters(LatLonVector vector); 
            
    LatLonPoint plusLatInMeters(LatLonPoint point, double distance);
    
    LatLonPoint plusLonInMeters(LatLonPoint point, double distance);
}
