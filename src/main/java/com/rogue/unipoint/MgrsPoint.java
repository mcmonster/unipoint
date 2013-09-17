package com.rogue.unipoint;

import com.bbn.openmap.proj.coords.MGRSPoint;
import static com.google.common.base.Preconditions.*;
import static com.rogue.unipoint.GridPoint.GridPointBuilder.newGridPoint;
import static com.rogue.unipoint.LatLonPoint.LatLonPointBuilder.newLatLonPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a coordinate in the Military Grade Reference System. Immutable!
 * 
 * @author R. Matt McCann
 */
public class MgrsPoint {
    /** In which grid zone the point lies. */
    private final String gridZone;
    
    private static final Logger logger = LoggerFactory.getLogger("MgrsPoint");
    
    /** Position within the sub-grid zone. */
    private final GridPoint position;
    
    /** Resolution of the point. */
    private final Resolution resolution;
    
    /** 100 Km resolution polygon in which the point lies. */
    private final String subGridZone;
    
    /** Resolution of the point. */
    public enum Resolution {
        ONE_METER(1), /** 1 meter resolution. */
        TEN_METER(2), /** 10 meter resolution. */
        ONE_HUNDRED_METER(3), /** 100 meter resolution. */ 
        ONE_KILOMETER(4), /** 1 kilometer resolution. */
        TEN_KILOMETER(5); /** 10 kilometer resolution. */
        
        private Resolution(int raw) { this.raw = raw; }
        
        public static Resolution parse(int raw) {
            return ONE_METER.plus(raw - 1);
        }
        
        public Resolution plus(int levels) {
            int newRaw = raw + levels;
            
            if (newRaw >= 5) return TEN_KILOMETER;
            else if (newRaw == 4) return ONE_KILOMETER;
            else if (newRaw == 3) return ONE_HUNDRED_METER;
            else if (newRaw == 2) return TEN_METER;
            else return ONE_METER;
        }
        
        public int raw() { return raw; }
        
        @Override
        public String toString() {
            switch (raw) {
                case 1:
                    return "1 m";
                case 2:
                    return "10 m";
                case 3:
                    return "100 m";
                case 4:
                    return "1 km";
                default:
                    return "10 km";
            }
        }
        
        private final int raw;
    }
    
    public MgrsPoint(String gridZone,
                     GridPoint position,
                     Resolution resolution,
                     String subGridZone) {
        this.gridZone = checkNotNull(gridZone);
        this.position = checkNotNull(position);
        this.resolution = checkNotNull(resolution);
        this.subGridZone = checkNotNull(subGridZone);
    }
    
    /** 
     * Constructs a point corresponding to the provided latlon coordinate
     * using 1 meter resolution.
     * 
     * @param latlon Must not be null.
     */
    public MgrsPoint(LatLonPoint latlon) {
        this(latlon, Resolution.ONE_METER);
    }
    
    /** 
     * Constructs a point corresponding to the provided latlon coordinate.
     * 
     * @param latlon Must not be null. 
     * @param resolution Must not be null.
     */
    public MgrsPoint(LatLonPoint latlon, Resolution resolution) {
        this.resolution = checkNotNull(resolution);
        com.bbn.openmap.proj.coords.LatLonPoint ll 
                = new com.bbn.openmap.proj.coords.LatLonPointDouble(latlon.lat(), latlon.lon());
        String mgrs = MGRSPoint.LLtoMGRS(ll).getMGRS();
        logger.info("MGRS String: " + mgrs);

        // If the easting grid zone is a single digit
        if (mgrs.length() == "0XXX0000000000".length()) { 
            this.gridZone = mgrs.substring(0, 2);
            this.subGridZone = mgrs.substring(2, 4);
            this.position = newGridPoint().withColumn(mgrs.substring(4, 9))
                                          .withRow(mgrs.substring(9, 14))
                                          .build();
        } else { // If the easting grid zone is a double digit
            this.gridZone = mgrs.substring(0, 3);
            this.subGridZone = mgrs.substring(3, 5);
            this.position = newGridPoint().withColumn(mgrs.substring(5, 10))
                                          .withRow(mgrs.substring(10, 15))
                                          .build();
        }
    }
    
    private int calcGridZoneDistEasting(MgrsPoint point) {
        int myGridZone  = this.getGridZoneEasting();
        int itsGridZone = point.getGridZoneEasting();
        
        int totalEastingSize = 60;
        int dist = itsGridZone - myGridZone;
        if (dist > totalEastingSize / 2) dist -= totalEastingSize;
        else if (dist < -totalEastingSize / 2) dist += totalEastingSize;
        
        return dist;
    }
    
    private int calcGridZoneDistNorthing(MgrsPoint point) {
        int myGridZone  = this.getGridZoneNorthing();
        int itsGridZone = point.getGridZoneNorthing();
        
        int totalNorthingSize = 20;
        int dist = itsGridZone - myGridZone;
        if (dist > totalNorthingSize / 2) dist -= totalNorthingSize;
        else if (dist < -totalNorthingSize / 2) dist += totalNorthingSize;
        
        return dist;
    }
    
    /**
     * Calculates where the lat-lon point lies inside the 9 grid pixel neighborhood
     * around this pixel. This function is useful because a lat-lon point can be
     * higher resolution than this point.
     * 
     * @param point
     * @return 
     */
    public Point2D calcSubPixelOffset(LatLonPoint point) {
        LatLonPoint center         = newLatLonPoint().from(this).build();
        Point2D     subPixelOffset = new Point2D(); 
        double      unitDistance   = Math.pow(10, resolution.raw() - 1);
        
        logger.info("Unit Distance:     " + unitDistance);
        logger.info("CenterPoint MGRS:  " + this.toString());
        logger.info("CenterPoint LL:    " + center.toString());
        logger.info("Point:             " + point);
        logger.info("Center-Point Lon:  " + center.distanceToLonInMeters(point));
        logger.info("Center-Point Lat:  " + center.distanceToLatInMeters(point));
        
        subPixelOffset.setX(center.distanceToLonInMeters(point) / unitDistance);
        subPixelOffset.setY(center.distanceToLatInMeters(point) / unitDistance);
        logger.info("Offset: " + subPixelOffset);
        
        return subPixelOffset;
    }
    
    public GridPoint distanceTo(MgrsPoint point) {
        checkArgument(this.resolution == point.resolution, "Expected matching"
                + " resolutions, but this point has resolution of %s and the targeted "
                + "point has a resolution of %s", this.resolution, point.resolution);
        
        int gridSizeE        = 60;
        int gridSizeN        = 20;
        int gridZoneDistE    = calcGridZoneDistEasting(point);
        int gridZoneDistN    = calcGridZoneDistNorthing(point);
        int itsEasting       = point.getPositionEasting();
        int itsNorthing      = point.getPositionNorthing();
        int myEasting        = this.getPositionEasting();
        int myNorthing       = this.getPositionNorthing();
        int subGridSize      = 100000;
        
        if ((Math.abs(gridZoneDistE) > 1) || (Math.abs(gridZoneDistN) > 1)) {
            //Log.w(TAG, "These two points are more than one grid zone apart. The distance"
            //        + " calculation used here is simplified in such a way that very far "
            //        + "apart points cannot be accurately used in distance calculations!");
        }
        
        // Adjust the points raw position for the sub-grid zone difference
        itsEasting += subGridSize * point.getSubGridOffsetEasting();
        itsNorthing += subGridSize * point.getSubGridOffsetNorthing();
        myEasting += subGridSize * this.getSubGridOffsetEasting();
        myNorthing += subGridSize * this.getSubGridOffsetNorthing();
        
        // Adjust the points raw position for the grid zone difference
        itsEasting += gridSizeE * gridZoneDistE;
        itsNorthing += gridSizeN * gridZoneDistN;
        
        int easting = itsEasting - myEasting;
        int northing = itsNorthing - myNorthing;
        
        return newGridPoint().withColumn(easting).withRow(northing).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MgrsPoint other = (MgrsPoint) obj;
        
        return this.toString().equals(other.toString());
    }
    
    public String getGridZone() { return gridZone; }
    public int getGridZoneEasting() { 
        if (gridZone.length() == 3) return Integer.parseInt(gridZone.substring(0, 2));
        else return Integer.parseInt(gridZone.substring(0, 1));
    }
    public char getGridZoneNorthing() { return gridZone.charAt(gridZone.length()-1); }
    
    public GridPoint getPosition() { return position; }
    public int getPositionEasting() { return position.getColumn(); }
    public int getPositionNorthing() { return position.getRow(); }
    public GridPoint getPosition(Resolution resolution) {
        checkState(this.resolution.raw() <= resolution.raw(), "Requestion resolution (%s) is"
                + " more precise than the resolution of this point (%s)",
                resolution, this.resolution);
        
        int modulo = (int) Math.pow(10, resolution.raw());
        int divisor = (int) Math.pow(10, resolution.raw() - 1);
        
        return newGridPoint().withColumn(position.getColumn() % modulo / divisor)
                             .withRow(position.getRow() % modulo / divisor)
                             .build();
    }
    public int getPositionEasting(Resolution resolution) {
        return getPosition(resolution).getColumn();
    }
    public int getPositionNorthing(Resolution resolution) {
        return getPosition(resolution).getRow();
    }
    
    public Resolution getResolution() { return resolution; }
    
    public String getSubGridZone() { return subGridZone; }
    public char getSubGridEasting() { return subGridZone.charAt(0); }
    public char getSubGridNorthing() { return subGridZone.charAt(1); }
    public int getSubGridOffsetEasting() {
        char subGrid = getSubGridEasting();
        if (subGrid < 'I') { return subGrid - 'A' + 1; }
        else if (subGrid < 'O') { return subGrid - 'A'; }
        else { return subGrid - 'A' - 1; }
    }
    public int getSubGridOffsetNorthing() {
        char subGrid = getSubGridNorthing();
        if (subGrid < 'I') { return subGrid - 'A' + 1; }
        else if (subGrid < 'O') { return subGrid - 'A'; }
        else { return subGrid - 'A' - 1; }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.gridZone != null ? this.gridZone.hashCode() : 0);
        hash = 31 * hash + (this.position != null ? this.position.hashCode() : 0);
        hash = 31 * hash + (this.resolution != null ? this.resolution.hashCode() : 0);
        hash = 31 * hash + (this.subGridZone != null ? this.subGridZone.hashCode() : 0);
        return hash;
    }
    
    public MgrsPoint plusEasting(int scalar) {
        return plusEasting(scalar, resolution);
    }
    
    public MgrsPoint plusEasting(int scalar, Resolution resolution) {
        int scale = (int) Math.pow(10, resolution.raw() - 1);
        
        MgrsPointBuilder builder = new MgrsPointBuilder()
                .from(this)
                .withEasting(this.position.getColumn() + scalar * scale);
                
        if (resolution.raw() < this.resolution.raw()) { // If the added value is higher res
            return builder.withResolution(resolution).build();
        } else { // If the current res is equal or higher
            return builder.build();
        }
    }
    
    public MgrsPoint plusNorthing(int scalar) {
        return plusNorthing(scalar, resolution);
    }
    
    public MgrsPoint plusNorthing(int scalar, Resolution resolution) {
        int scale = (int) Math.pow(10, resolution.raw() - 1);
        
        MgrsPointBuilder builder = new MgrsPointBuilder()
                .from(this)
                .withNorthing(this.position.getRow() + scalar * scale);
        
        if (resolution.raw() < this.resolution.raw()) { // If the added value is higher res
            return builder.withResolution(resolution).build();
        } else { 
            return builder.build();
        }
    }
    
    @Override
    public String toString() {
        return toString(Resolution.ONE_METER);
    }
    
    public String toString(Resolution resolution) {
        String rep = gridZone + subGridZone;
        
        String easting = String.format("%05d", position.getColumn());
        rep += easting.substring(0, 6 - resolution.raw());
        
        String northing = String.format("%05d", position.getRow());
        rep += northing.substring(0, 6 - resolution.raw());
        
        return rep;
    }
    
    public static class MgrsPointBuilder {
        private String     gridZone = "1A";
        private GridPoint  position = new GridPoint();
        private Resolution resolution = Resolution.ONE_METER;
        private String     subGridZone = "AA";
        
        public MgrsPoint build() {
            return new MgrsPoint(gridZone, position, resolution, subGridZone);
        }
        
        public MgrsPointBuilder from(MgrsPoint from) {
            this.resolution = from.resolution;
            this.gridZone = from.gridZone;
            this.position = from.position;
            this.subGridZone = from.subGridZone;
            
            return this;
        }
        
        public static MgrsPointBuilder newMgrsPoint() { return new MgrsPointBuilder(); }
        
        public MgrsPointBuilder withEasting(int scalar) {
            logger.info("WithEasting(" + scalar + ")...");
            if (scalar >= 0) {
                position = new GridPoint(scalar, position.getRow());
            } else {
                position = new GridPoint(100000 + scalar, position.getRow());
            }
            
            return this;
        }
        
        public MgrsPointBuilder withNorthing(int scalar) {
            logger.info("WithNorthing(" + scalar + ")...");
            if (scalar >= 0) {
                position = new GridPoint(position.getColumn(), scalar);
            } else {
                position = new GridPoint(position.getColumn(), 100000 + scalar);
            }
            
            return this;
        }
        
        public MgrsPointBuilder withResolution(Resolution resolution) {
            this.resolution = checkNotNull(resolution);
            
            return this;
        }
    }
}
