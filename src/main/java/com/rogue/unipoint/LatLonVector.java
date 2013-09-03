package com.rogue.unipoint;

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
        
        checkArgument(from.geoid() == to.geoid(), "Expected the points in "
                + "comprising the vector to be mapped on the same geoid. These "
                + "points are using two different geoid, thus the vector cannot "
                + "be properly evaluated!");
    }
    
    public double inMeters() {
        return from.geoid().inMeters(this);
    }
    
    public LatLonPoint from() { return from; }
    
    public LatLonPoint to() { return to; }
}
