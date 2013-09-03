package com.rogue.unipoint;

/**
 * Immutable point on a grid, fluent-stylee!
 * 
 * @author R. Matt McCann
 */
public class GridPoint {
    private final int column;
    private final int row;
    
    public GridPoint() { this(0, 0); }
    
    protected GridPoint(int column, int row) {
        this.column = column;
        this.row = row;
    }
    
    public int getColumn() { return column; }
    public int getRow() { return row; }

    /** Fluent interface for constructing the point. */
    public static class GridPointBuilder {
        private int column, row;
        
        public GridPoint build() { return new GridPoint(column, row); }
        
        public GridPointBuilder from(GridPoint from) {
            this.column = from.column;
            this.row = from.row;
            return this;
        }
        
        public static GridPointBuilder newGridPoint() { return new GridPointBuilder(); }
        
        public GridPointBuilder withColumn(String column) {
            this.column = Integer.parseInt(column);
            return this;
        }
        
        public GridPointBuilder withColumn(int column) {
            this.column = column;
            return this;
        }
        
        public GridPointBuilder withRow(String row) {
            this.row = Integer.parseInt(row);
            return this;
        }
        
        public GridPointBuilder withRow(int row) {
            this.row = row;
            return this;
        }
    }
}
