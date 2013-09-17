package com.moxx.unipoint;

import com.bbn.openmap.proj.Ellipsoid;
import com.bbn.openmap.proj.coords.LatLonPointDouble;
import com.bbn.openmap.proj.coords.MGRSPoint;
import com.rogue.unipoint.LatLonPoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author R. Matt McCann */
public class MgrsPointTest {
    private static final Logger logger = LoggerFactory.getLogger("MgrsPointTest");
    
    public MgrsPointTest() { }
    
    @AfterClass
    public static void afterClass() { }
    
    @After
    public void afterTest() { }
    
    @BeforeClass
    public static void beforeClass() { }
    
    @Before
    public void beforeTest() { }

    @Test
    public void testConversion() {
        /*LatLonPoint base    = newLatLonPoint().withLat(0.0).withLon(0.0).build();
        
        MgrsPoint   mgrs   = new MgrsPoint(base);
        LatLonPoint latlon = newLatLonPoint().from(mgrs).build();
        double      dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(0,0)                = " + mgrs);
        logger.info("LatLon(Mgrs(0,0))        = " + latlon);
        logger.info("Dist(LatLon(Mgrs(0,0)))  = " + dist);
        
        mgrs   = new MgrsPoint(base.plusLatInMeters(1));
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(0,1)                = " + mgrs);
        logger.info("LatLon(Mgrs(0,1))        = " + latlon);
        logger.info("Dist(LatLon(Mgrs(0, 1))  = " + dist);
        
        mgrs   = new MgrsPoint(base.plusLatInMeters(-1));
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(0,-1)               = " + mgrs);
        logger.info("LatLon(Mgrs(0,-1))       = " + latlon);
        logger.info("Dist(LatLon(Mgrs(0,-1))  = " + dist);
        
        mgrs   = new MgrsPoint(base.plusLonInMeters(1));
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(1,0)                = " + mgrs);
        logger.info("LatLon(Mgrs(1,0))        = " + latlon);
        logger.info("Dist(LatLon(Mgrs(1,0)))  = " + dist);
        
        mgrs   = new MgrsPoint(base.plusLonInMeters(-1));
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(-1,0)               = " + mgrs);
        logger.info("LatLon(Mgrs(-1,0))       = " + latlon);
        logger.info("Dist(LatLon(Mgrs(-1,0))) = " + dist);
        
        mgrs   = new MgrsPoint(base.plusLatInMeters(-1).plusLonInMeters(-1));
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(-1,-1)              = " + mgrs);
        logger.info("LatLon(Mgrs(-1,-1))      = " + latlon);
        logger.info("Dist(LatLon(Mgrs(-1,-1)) = " + dist);
        
        mgrs   = new MgrsPoint(base.plusLonInMeters(-1).plusLatInMeters(-1));
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Mgrs(-1,-1)              = " + mgrs);
        logger.info("LatLon(Mgrs(-1,-1))      = " + latlon);
        logger.info("Dist(LatLon(Mgrs(-1,-1)) = " + dist);
        
        LatLonPoint neat = base.plusLonInMeters(-1).plusLatInMeters(-1);
        base   = newLatLonPoint().withLat(-4.368e-8).withLon(-1.207e-8).build();
        mgrs   = new MgrsPoint(base);
        latlon = newLatLonPoint().from(mgrs).build();
        dist   = base.distanceTo(latlon).inMeters();
        logger.info("Base                      = " + base);
        logger.info("Neat                      = " + neat);
        logger.info("Mgrs(manual)              = " + mgrs);
        logger.info("Mgrs(neat)                = " + new MgrsPoint(neat));
        logger.info("LatLon(Mgrs(manual))      = " + latlon);
        logger.info("Dist(LatLon(Mgrs(manual)) = " + dist);
        
        
        for (double latIter = 0; latIter > -1e-7; latIter += -1e-8) {
            for (double lonIter = 0; lonIter > -1e-7; lonIter += -1e-8) {
                LatLonPoint ll = newLatLonPoint().withLat(latIter).withLon(lonIter).build();
                String results = Coordinates.mgrsFromLatLon(latIter, lonIter);
                LatLonPoint after = newLatLonPoint().from(new MgrsPoint(ll)).build();
                logger.info("(" + latIter + "," + lonIter + ") = " + results);
                logger.info("Dist = " + ll.distanceTo(after).inMeters());
            }
        }*/
        
        com.bbn.openmap.proj.coords.LatLonPoint ll = new LatLonPointDouble(0, 0);
        MGRSPoint mgrs = MGRSPoint.LLtoMGRS(ll);
        com.bbn.openmap.proj.coords.LatLonPoint llFromMgrs = new LatLonPointDouble();
        MGRSPoint.MGRStoLL(mgrs, Ellipsoid.WGS_84, llFromMgrs);
        logger.info("ll = " + ll);
        logger.info("mgrs = " + mgrs);
        logger.info("mgrs string = " + mgrs.getMGRS());
        logger.info("ll from mgrs = " + llFromMgrs);
        
        ll = new LatLonPointDouble(-1e-8, 0);
        mgrs = MGRSPoint.LLtoMGRS(ll);
        MGRSPoint.MGRStoLL(mgrs, Ellipsoid.WGS_84, llFromMgrs);
        logger.info("ll = " + ll);
        logger.info("mgrs = " + mgrs);
        logger.info("mgrs string = " + mgrs.getMGRS());
        logger.info("ll from mgrs = " + llFromMgrs);
        
        LatLonPoint from = new LatLonPoint(0, 0, 0);
        LatLonPoint to = from.plusLatInMeters(1);
        logger.info("Lat Distance: " + from.distanceToLatInMeters(to));
        logger.info("Lat Distance: " + from.distanceTo(to).inMeters());
        to = from.plusLonInMeters(1);
        logger.info("Lon Distance: " + from.distanceToLonInMeters(to));
        logger.info("Lon Distance: " + from.distanceTo(to).inMeters());
        to = from.plusLatInMeters(1).plusLonInMeters(1);
        logger.info("Lon Distance: " + from.distanceToLonInMeters(to));
        logger.info("Lon Distance: " + from.distanceTo(to).inMeters());
    }
    
    @Test
    public void testDistance() {
        // Test the distance between two identical points
        /*MgrsPoint point = newMgrsPoint().withEasting(100)
                                        .withNorthing(100).build();
        GridPoint expected = newGridPoint().
        //GridPoint distance = point.distanceTo(point);
        assertEquals("Identical Points", new GridPoint(0, 0), 
        
        // Test the distance between two points in the same grid/sub-grid space
        MgrsPoint point2 = newMgrsPoint().withEasting(200)
                                         .withNorthing(200).build();
        distance = point.distanceTo(point2);
        assertEquals("Same Grid/Sub-Grid - Easting", 100, distance.getColumn());
        assertEquals("Same")*/
    }
}
