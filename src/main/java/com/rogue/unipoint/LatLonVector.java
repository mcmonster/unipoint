package com.rogue.unipoint;

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
        LatLonPointDouble fromLL = new LatLonPointDouble(from.lat(), from.lon());
        LatLonPointDouble toLL = new LatLonPointDouble(to.lat(), to.lon());
        
        return Length.METER.fromRadians(fromLL.distance(toLL));
    }
    
    public LatLonPoint from() { return from; }
    
    public LatLonPoint to() { return to; }
}
