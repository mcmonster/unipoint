package com.rogue.unipoint;

import com.bbn.openmap.proj.GreatCircle;
import com.bbn.openmap.proj.Length;
import com.bbn.openmap.proj.coords.LatLonPointDouble;
import static com.google.common.base.Preconditions.*;

/**
 * A vector in the lat-lon coordinate system.
 * 
 * @author R. Matt McCann
 */
public class LatLonVector {
    private final LatLonPoint from;
    
    private final LatLonPoint to;
    
    public LatLonVector(LatLonPoint from, LatLonPoint to) {
        this.from = checkNotNull(from);
        this.to = checkNotNull(to);
    }
    
    public double inMeters() {
        double fromLatRadians = Length.DECIMAL_DEGREE.toRadians(from.lat());
        double fromLonRadians = Length.DECIMAL_DEGREE.toRadians(from.lon());
        double toLatRadians   = Length.DECIMAL_DEGREE.toRadians(to.lat());
        double toLonRadians   = Length.DECIMAL_DEGREE.toRadians(to.lon());
        
        double arcLength = GreatCircle.sphericalDistance(
                fromLatRadians, fromLonRadians, toLatRadians, toLonRadians);
        return Length.METER.fromRadians(arcLength);
    }
    
    public LatLonPoint from() { return from; }
    
    public LatLonPoint to() { return to; }
}
