package graphic;

import info.Rate;

public class Point {
    public double row = -1;
    public double column = -1;
    public Point(){}
    public Point(double row,double column)
    {
        this.row = row;
        this.column = column;
    }
    public Point(Point point)
    {
        this.row = point.row;
        this.column = point.column;
    }
    @Override
    public String toString()
    {
        return new String("row:" + this.row + ",column:" + this.column);
    }
}
