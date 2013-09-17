package com.rogue.unipoint;

/**
 * Simple two-dimensional point.
 * 
 * @author R. Matt McCann
 */
public class Point2D {
    private double x = 0;
    private double y = 0;
    
    public Point2D() { }
    
    public Point2D(final Point2D source) {
        this.x = source.x;
        this.y = source.y;
    }
    
    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point2D(GridPoint source) {
        this.x = source.getColumn();
        this.y = source.getRow();
    }

    @Override
    public Point2D clone() {
        return new Point2D(x, y);
    }
    
    public double distanceTo(Point2D target) {
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
        final Point2D other = (Point2D) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }
    
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
    
    public void normalize(double max) {
        x /= max;
        y /= max;
    }
    
    public Point2D plus(Point2D value) {
        Point2D result = new Point2D();
        result.x = this.x + value.x;
        result.y = this.y + value.y;
        return result;
    }
    
    public Point2D plusX(double value) {
        Point2D result = new Point2D();
        result.x = this.x + value;
        result.y = this.y;
        return result;
    }
    
    public Point2D plusY(double value) {
        Point2D result = new Point2D();
        result.x = this.x;
        result.y = this.y + value;
        return result;
    }
    
    public Point2D scaleBy(double factor) {
        Point2D result = new Point2D();
        result.x = this.x * factor;
        result.y = this.y * factor;
        return result;
    }
    
    public Point2D scaleXBy(double factor) {
        Point2D result = new Point2D();
        result.x = this.x * factor;
        result.y = this.y;
        return result;
    }
    
    public Point2D scaleYBy(double factor) {
        Point2D result = new Point2D();
        result.x = this.x;
        result.y = this.y * factor;
        return result;
    }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    
    public Point2D subtract(final Point2D subtractee) {
        Point2D result = new Point2D();
        
        result.x = this.x - subtractee.x;
        result.y = this.y - subtractee.y;
        
        return result;
    }
    
    @Override
    public String toString() {
        return "" + x + "," + y;
    }
}