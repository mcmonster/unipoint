package com.moxx.unipoint;

import com.rogue.unipoint.LatLonPoint;
import com.rogue.unipoint.MgrsPoint;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author moxx
 */
public class OffsetTest {
    private final Logger logger = LoggerFactory.getLogger("OffsetTest");
    
    @Test
    public void testOffset() {
        /**LatLonPoint beforeDrag = new LatLonPoint(0, 39.99669407036091, -82.988289838272);
        LatLonPoint afterDrag = beforeDrag.plusLon(6.872558920179017E-4)
                                          .plusLat(0.0);
        
        MgrsPoint centerBeforeDrag = new MgrsPoint(beforeDrag);
        double offsetBeforeDrag = centerBeforeDrag.calcSubPixelOffset(beforeDrag).getX();
        logger.info("Offset Before Drag: " + offsetBeforeDrag);
        
        MgrsPoint centerAfterDrag = new MgrsPoint(afterDrag);
        double offsetAfterDrag = centerAfterDrag.calcSubPixelOffset(afterDrag).getX();
        logger.info("Offset After Drag:  " + offsetAfterDrag);*/
        /*LatLonPoint testLL   = new LatLonPoint(0, 45, -80);
        MgrsPoint   orig = new MgrsPoint(testLL);
        for (int iter = 1; iter < 10; iter++) {
            LatLonPoint mine = testLL.plusLonInMeters(0.25 * iter);
            MgrsPoint   mgrs = new MgrsPoint(mine);
            double currentOffset = mgrs.calcSubPixelOffset(mine).getX();
            double origOffset = orig.calcSubPixelOffset(mine).getX();
            logger.info("Current LatLon " + iter + " = " + mine);
            logger.info("Current Mgrs " + iter + "   = " + mgrs);
            logger.info("Current Offset " + iter + " = " + currentOffset);
            logger.info("Orig Mgr Offset " + iter + " = " + origOffset + "\n");
        }*/
    }
}
