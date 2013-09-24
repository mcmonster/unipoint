package com.moxx.unipoint;

import com.bbn.openmap.proj.Ellipsoid;
import com.bbn.openmap.proj.Length;
import com.bbn.openmap.proj.coords.LatLonPoint;
import com.bbn.openmap.proj.coords.LatLonPointDouble;
import com.bbn.openmap.proj.coords.MGRSPoint;
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
        /*LatLonPoint ll = new LatLonPointDouble(0, 0);
        MGRSPoint point = MGRSPoint.LLtoMGRS(ll);
        logger.info(point.getMGRS() + " = (0,0)");
        point = new MGRSPoint("30NZF3397800000");
        MGRSPoint.MGRStoLL(point, Ellipsoid.WGS_84, ll);
        logger.info("30NZF3397800000 = (" + ll.getLatitude() + "," + ll.getLongitude() + ")");
        
        com.rogue.unipoint.LatLonPoint myLL = new com.rogue.unipoint.LatLonPoint(0, 0, 0);
        myLL = myLL.plusLonInMeters(-1);
        logger.info(myLL.toString());*/
    }
    
    @Test
    public void testDistance() {
        /*LatLonPoint nicePoint = new LatLonPointDouble();
        LatLonPoint nicePoint2 = new LatLonPointDouble(0, Length.METER.toRadians(1), true);
        
        com.rogue.unipoint.LatLonPoint point = new com.rogue.unipoint.LatLonPoint();
        com.rogue.unipoint.LatLonPoint point2 = point.plusLonInMeters(1);
        double wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        double approxDist = point.distanceTo(point2).inMeters();
        logger.info("1 Meter Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint2 = new LatLonPointDouble(0, Length.METER.toRadians(10), true);
        point2 = point.plusLonInMeters(10);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("10 Meter Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint2 = new LatLonPointDouble(0, Length.METER.toRadians(100), true);
        point2 = point.plusLonInMeters(100);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("100 Meter Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint2 = new LatLonPointDouble(0, Length.METER.toRadians(1000), true);
        point2 = point.plusLonInMeters(1000);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("1000 Meter Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint = new LatLonPointDouble(Length.DECIMAL_DEGREE.toRadians(45), 0, true);
        point = new com.rogue.unipoint.LatLonPoint(0, 45, 0);
        
        nicePoint2 = new LatLonPointDouble(Length.DECIMAL_DEGREE.toRadians(45), Length.METER.toRadians(1), true);
        point2 = point.plusLonInMeters(1);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("1 Meter, 45 Degree WGS84: " + wgs84Dist);
        logger.info("1 Meter, 45 Degree Approx:" + approxDist);
        logger.info("1 Meter, 45 Degree Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint2 = new LatLonPointDouble(Length.DECIMAL_DEGREE.toRadians(45), Length.METER.toRadians(10), true);
        point2 = point.plusLonInMeters(10);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("10 Meter, 45 Degree WGS84: " + wgs84Dist);
        logger.info("10 Meter, 45 Degree Approx:" + approxDist);
        logger.info("10 Meter, 45 Degree Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint2 = new LatLonPointDouble(Length.DECIMAL_DEGREE.toRadians(45), Length.METER.toRadians(100), true);
        point2 = point.plusLonInMeters(100);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("100 Meter, 45 Degree WGS84: " + wgs84Dist);
        logger.info("100 Meter, 45 Degree Approx:" + approxDist);
        logger.info("100 Meter, 45 Degree Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
        
        nicePoint2 = new LatLonPointDouble(Length.DECIMAL_DEGREE.toRadians(45), Length.METER.toRadians(1000), true);
        point2 = point.plusLonInMeters(1000);
        wgs84Dist = Length.METER.fromRadians(nicePoint.distance(nicePoint2));
        approxDist = point.distanceTo(point2).inMeters();
        logger.info("1000 Meter, 45 Degree WGS84: " + wgs84Dist);
        logger.info("1000 Meter, 45 Degree Approx:" + approxDist);
        logger.info("1000 Meter, 45 Degree Error: " + (Math.abs(wgs84Dist / approxDist - 1) * 100) + "%");
    */}
}
