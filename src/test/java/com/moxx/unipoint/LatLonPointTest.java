package com.moxx.unipoint;

import com.bbn.openmap.proj.Length;
import com.rogue.unipoint.LatLonPoint;
import static com.rogue.unipoint.LatLonPoint.LatLonPointBuilder.newLatLonPoint;
import com.rogue.unipoint.MgrsPoint;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for LatLonPoint.
 * 
 * @author R. Matt McCann
 */
public class LatLonPointTest {
    private static final Logger logger = LoggerFactory.getLogger("LatLonPointTest");
    
    private boolean areEqual(double expected, double actual, double tolerance) {
        logger.info("Expected:  " + expected);
        logger.info("Actual:    " + actual);
        logger.info("Tolerance: " + tolerance);
        return ((expected - tolerance <= actual) && (actual <= expected + tolerance));
    }
    
    @Test
    public void testMgrsConversion() {
        LatLonPoint beforeDrag          = new LatLonPoint(0, 39.99681835530467, -82.98806076341697);
        MgrsPoint   beforeDragMgrs      = new MgrsPoint(beforeDrag);
        LatLonPoint beforeDragMgrsLL    = newLatLonPoint().from(beforeDragMgrs).build();
        double      beforeDragOffsetLat = beforeDragMgrsLL.distanceToLatInMeters(beforeDrag);
        double      beforeDragOffsetLon = beforeDragMgrsLL.distanceToLonInMeters(beforeDrag);
        
        LatLonPoint afterDrag           = new LatLonPoint(0, 39.99681880272974, -82.98806091418044);
        MgrsPoint   afterDragMgrs       = new MgrsPoint(afterDrag);
        LatLonPoint afterDragMgrsLL     = newLatLonPoint().from(afterDragMgrs).build();
        double      afterDragOffsetLat  = afterDragMgrsLL.distanceToLatInMeters(afterDrag);
        double      afterDragOffsetLon  = afterDragMgrsLL.distanceToLonInMeters(afterDrag);
        
        double offsetFromLastLat    = beforeDragMgrsLL.distanceToLatInMeters(afterDrag);
        double offsetFromCurrentLat = afterDrag.distanceToLatInMeters(afterDragMgrsLL);
        
        LatLonPoint beforeMgrsPlusOne = beforeDragMgrsLL.plusLatInMeters(1);
        logger.info("BeforeMgrsPlusOne: " + beforeMgrsPlusOne);
        logger.info("MgrsLLDist: " + beforeDragMgrsLL.distanceTo(afterDragMgrsLL).inMeters());
        
        logger.info("BeforeDrag:          " + beforeDrag);
        logger.info("AfterDrag:           " + afterDrag);
        logger.info("BeforeDragMgrs:      " + beforeDragMgrs);
        logger.info("AfterDragMgrs:       " + afterDragMgrs);
        logger.info("BeforeDragMgrsLL:    " + beforeDragMgrsLL);
        logger.info("AfterDragMgrsLL:     " + afterDragMgrsLL);
        logger.info("BeforeDragOffsetLat: " + beforeDragOffsetLat);
        logger.info("AfterDragOffsetLat:  " + afterDragOffsetLat);
        logger.info("BeforeDragOffsetLon: " + beforeDragOffsetLon);
        logger.info("AfterDragOffsetLon:  " + afterDragOffsetLon);
        
        logger.info("From Last Lat:    " + offsetFromLastLat);
        logger.info("From Current Lat: " + offsetFromCurrentLat);
        logger.info("Cos = " + Math.cos(Length.DECIMAL_DEGREE.toRadians(39.9968188)));
        logger.info("Compensated = " + (beforeDragOffsetLat / Math.cos(Length.DECIMAL_DEGREE.toRadians(39.9968188))));
        logger.info("Compensated = " + (afterDragOffsetLat / Math.cos(Length.DECIMAL_DEGREE.toRadians(39.99681880272974))));
    }
    
    @Test
    public void testDistance() {
        double tolerance = 0.00001;
        
        LatLonPoint point1 = new LatLonPoint(0, 0, 0);
        LatLonPoint oneMeterAway = point1.plusMeters(0, 1);
        double expected = 1.0;
        assertTrue("One meter away distance",
                areEqual(expected, point1.distanceTo(oneMeterAway).inMeters(), tolerance));
        
        LatLonPoint oneUpOneOver = point1.plusMeters(1, 1);
        expected = Math.sqrt(2);
        assertTrue("One meter up, one meter over",
                areEqual(expected, point1.distanceTo(oneUpOneOver).inMeters(), tolerance));
        
        LatLonPoint oneThousandOver = point1.plusMeters(0, 1000);
        expected = 1000;
        assertTrue("1000 meters over",
                areEqual(expected, point1.distanceTo(oneThousandOver).inMeters(), tolerance));
        
        LatLonPoint oneThousandUpAndOver = point1.plusMeters(1000, 1000);
        expected = Math.sqrt(2) * 1000;
        tolerance = 0.0001;
        assertTrue("1000 up, 1000 over",
                areEqual(expected, point1.distanceTo(oneThousandUpAndOver).inMeters(), tolerance));
        
        point1 = new LatLonPoint(0, 45, 45);
        oneMeterAway = point1.plusMeters(0, 1);
        expected = 1.0;
        tolerance = 0.00001;
        assertTrue("One over 45,45",
                areEqual(expected, point1.distanceTo(oneMeterAway).inMeters(), tolerance));
        
        oneUpOneOver = point1.plusLonInMeters(1).plusLatInMeters(1);
        expected = Math.sqrt(2);
        assertTrue("One up, one over",
                areEqual(expected, point1.distanceTo(oneUpOneOver).inMeters(), tolerance));
        
        expected = 1.0;
        assertTrue("Lon distance, one up one over",
                areEqual(expected, point1.distanceToLonInMeters(oneUpOneOver), tolerance));
        
        expected = -1.0;
        assertTrue("Lon distance in opposite direction, one up one over",
                areEqual(expected, oneUpOneOver.distanceToLonInMeters(point1), tolerance));
        
        expected = 1.0;
        assertTrue("Lat distance, one up one over",
                areEqual(expected, point1.distanceToLatInMeters(oneUpOneOver), tolerance));
        
        expected = -1.0;
        assertTrue("Lat distance in opposite direction, one up one over",
                areEqual(expected, oneUpOneOver.distanceToLatInMeters(point1), tolerance));
        
        LatLonPoint realClose = point1.plusMeters(0.1, 0.1);
        expected = Math.sqrt(2) * 0.1;
        assertTrue("Real close",
                areEqual(expected, point1.distanceTo(realClose).inMeters(), tolerance));
        
        expected = 0.1;
        assertTrue("Real close lat dist",
                areEqual(expected, point1.distanceToLatInMeters(realClose), tolerance));
        
        expected = 0.1;
        assertTrue("Real close lon dist",
                areEqual(expected, point1.distanceToLonInMeters(realClose), tolerance));
        
        LatLonPoint reallyReallyClose = point1.plusMeters(0.00001, 0.00001);
        expected = Math.sqrt(2) * 0.00001;
        assertTrue("Really really close",
                areEqual(expected, point1.distanceTo(reallyReallyClose).inMeters(), tolerance));
        
        expected = 0.00001;
        assertTrue("Really really close, lat dist",
                areEqual(expected, point1.distanceToLatInMeters(reallyReallyClose), tolerance));
        
        expected = 0.00001;
        assertTrue("Really really close, lon dist",
                areEqual(expected, point1.distanceToLonInMeters(reallyReallyClose), tolerance));
        
    }
}
