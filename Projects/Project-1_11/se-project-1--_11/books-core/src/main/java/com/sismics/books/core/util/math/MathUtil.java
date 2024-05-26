package com.sismics.books.core.util.math;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.google.common.collect.Lists;


/**
 * Math utilities.
 * 
 * @author bgamard
 */
public class MathUtil {
    
    /**
     * Force a value between min and max.
     * 
     * @param value Value
     * @param min Minimum
     * @param max Maximum
     * @return Forced value
     */
    public static double clip(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Interpolate a value between two points.
     * 
     * @param x Value to interpolate
     * @param x1 Point 1 (x)
     * @param y1 Point 1 (y)
     * @param x2 Point 2 (x)
     * @param y2 Point 2 (y)
     * @return Interpolated value
     */
    public static double interpolate(double x, double x1, double y1, double x2, double y2) {
        double alpha = (x - x1) / (x2 - x1);
        
        return y1 * (1 - alpha) + y2 * alpha;
    }
    
    /**
     * Returns a random hex color.
     * 
     * @return Randomized hex color
     */
    public static String randomHexColor() {
        List<Integer> colorList = Lists.newArrayList(RandomUtils.nextInt(256), RandomUtils.nextInt(256), RandomUtils.nextInt(256));
        StringBuilder sb = new StringBuilder("#");
        for (int color : colorList) {
            String hex = Integer.toHexString(color);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
