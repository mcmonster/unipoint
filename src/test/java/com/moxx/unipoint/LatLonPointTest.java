package com.moxx.unipoint;

import com.rogue.unipoint.LatLonPoint;
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
    }
}
