package com.rogue.unipoint;

import static com.google.common.base.Preconditions.*;
import com.google.common.base.Objects;
import com.rogue.unipoint.geoid.ApproxGeoid;
import com.rogue.unipoint.geoid.Geoid;

/**
 * Represents a point in the geospace coordinate system, immutable and fluent stylee!
 * 
 * @author R. Matt McCann
 */
public class LatLonPoint {
    /** In meters above sea level. //TODO Geoid height? */
    private final double altitude;
    
    /** Shape of the earth used in calculations. */
    private final Geoid geoid;
    
    /** North is positive, south is negative. */
    private final double lat;
    
    /** East is positive, west is negative. */
    private final double lon;
    
    public LatLonPoint() {
        this(0, 0, 0); //TODO Change the default to something better
    }
    
    public LatLonPoint(double altitude, double lat, double lon) {
        this(altitude, ApproxGeoid.getInstance(), lat, lon);
    }
    
    public LatLonPoint(double altitude, Geoid geoid, double lat, double lon) {
        this.altitude = altitude;
        this.geoid = checkNotNull(geoid);
        this.lat = lat;
        this.lon = lon;
    }
    
    public double altitude() { return altitude; }
    
    /** {@inheritDocs} */
    @Override
    public LatLonPoint clone() {
        return new LatLonPoint(this.altitude, this.geoid, this.lat, this.lon);
    }
    
    public LatLonVector distanceTo(LatLonPoint to) {
        return new LatLonVector(this, to);
    }
    
    public double distanceToLatInMeters(LatLonPoint to) {
        return geoid.convertLatToMeters(to.lat - this.lat);
    }
    
    public double distanceToLonInMeters(LatLonPoint to) {
        return geoid.convertLonToMeters(to.lon - this.lon, lat);
    }
    
    @Override
    public boolean equals(Object comparee) {
        if (!(comparee instanceof LatLonPoint)) {
            return false;
        }
        
        LatLonPoint point = (LatLonPoint) comparee;
        return Objects.equal(this.altitude, point.altitude) &&
               Objects.equal(this.lat, point.lat) &&
               Objects.equal(this.lon, point.lon);
    }
    
    public Geoid geoid() { return geoid; }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.altitude) ^ (Double.doubleToLongBits(this.altitude) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.lat) ^ (Double.doubleToLongBits(this.lat) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.lon) ^ (Double.doubleToLongBits(this.lon) >>> 32));
        return hash;
    }
    
    public double lat() { return lat; }
    
    public double lon() { return lon; }
    
    public LatLonPoint plusAltitude(double value) {
        return new LatLonPoint(this.altitude + value, this.lat, this.lon);
    }
    
    public LatLonPoint plusLat(double value) {
        return new LatLonPoint(this.altitude, this.lat + value, this.lon);
    }
    
    public LatLonPoint plusLatInMeters(double value) {
        return geoid.plusLatInMeters(this, value);
    }
    
    public LatLonPoint plusLon(float value) {
        return new LatLonPoint(this.altitude, this.lat, this.lon + value);
    }
    
    public LatLonPoint plusLonInMeters(double value) {
        return geoid.plusLonInMeters(this, value);
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(LatLonPoint.class)
                .add("Altitude", altitude)
                .add("Latitude", lat)
                .add("Longitude", lon)
                .toString();
    }
    
    public LatLonPoint withLat(double value) {
        return new LatLonPoint(this.altitude, value, this.lon);
    }
    
    public LatLonPoint withLon(double value) {
        return new LatLonPoint(this.altitude, this.lat, value);
    }
    
    public static class LatLonPointBuilder {
        private double altitude, lat, lon;
        
        public LatLonPoint build() { return new LatLonPoint(altitude, lat, lon); }
        
        public LatLonPointBuilder from(LatLonPoint from) {
            this.altitude = from.altitude;
            this.lat = from.lat;
            this.lon = from.lon;
            return this;
        }
        
        public static LatLonPointBuilder newLatLonPoint() { return new LatLonPointBuilder(); }
        
        public LatLonPointBuilder withAltitude(double altitude) { 
            this.altitude = altitude; 
            return this;
        }
        
        public LatLonPointBuilder withLat(double lat) { 
            this.lat = lat; 
            return this;
        }
        
        public LatLonPointBuilder withLon(double lon) {
            this.lon = lon;
            return this;
        }
    }
}