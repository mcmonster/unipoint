package com.rogue.unipoint;

/**
 * Simple two-dimensional point.
 * 
 * @author R. Matt McCann
 */
public class FloatPoint2D {
    private float x = 0;
    private float y = 0;
    
    public FloatPoint2D() { }
    
    public FloatPoint2D(final FloatPoint2D source) {
        this.x = source.x;
        this.y = source.y;
    }
    
    public FloatPoint2D(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public FloatPoint2D(GridPoint source) {
        this.x = source.getColumn();
        this.y = source.getRow();
    }

    @Override
    public FloatPoint2D clone() {
        return new FloatPoint2D(x, y);
    }
    
    public double distanceTo(FloatPoint2D target) {
        double xFactor = this.x - target.x;
        double yFactor = this.y - target.y;
        
        return Math.sqrt(xFactor * xFactor + yFactor * yFactor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FloatPoint2D other = (FloatPoint2D) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        return true;
    }
    
    
    public float getX() { return x; }
    public float getY() { return y; }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Float.floatToIntBits(this.x));
        hash = 79 * hash + (int) (Float.floatToIntBits(this.y));
        return hash;
    }
    
    public void normalize(double max) {
        x /= max;
        y /= max;
    }
    
    public FloatPoint2D plus(FloatPoint2D value) {
        FloatPoint2D result = new FloatPoint2D();
        result.x = this.x + value.x;
        result.y = this.y + value.y;
        return result;
    }
    
    public FloatPoint2D plusX(float value) {
        FloatPoint2D result = new FloatPoint2D();
        result.x = this.x + value;
        result.y = this.y;
        return result;
    }
    
    public FloatPoint2D plusY(float value) {
        FloatPoint2D result = new FloatPoint2D();
        result.x = this.x;
        result.y = this.y + value;
        return result;
    }
    
    public FloatPoint2D scaleBy(float factor) {
        FloatPoint2D result = new FloatPoint2D();
        result.x = this.x * factor;
        result.y = this.y * factor;
        return result;
    }
    
    public FloatPoint2D scaleXBy(float factor) {
        FloatPoint2D result = new FloatPoint2D();
        result.x = this.x * factor;
        result.y = this.y;
        return result;
    }
    
    public FloatPoint2D scaleYBy(float factor) {
        FloatPoint2D result = new FloatPoint2D();
        result.x = this.x;
        result.y = this.y * factor;
        return result;
    }
    
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    
    public FloatPoint2D subtract(final FloatPoint2D subtractee) {
        FloatPoint2D result = new FloatPoint2D();
        
        result.x = this.x - subtractee.x;
        result.y = this.y - subtractee.y;
        
        return result;
    }
    
    @Override
    public String toString() {
        return "" + x + "," + y;
    }
}
