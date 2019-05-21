package info;

import graphic.Point;

public class Rate {
    public double xRate = -1;
    public double yRate = -1;
    public Rate(){}
    public Rate(double xRate,double yRate)
    {
        this.xRate = xRate;
        this.yRate = yRate;
    }
    public Rate(Point beginPoint,Point endPoint)
    {
        this.xRate = 10*(endPoint.row - beginPoint.row)/Info.CanvasWidth;
        this.yRate = 10*(endPoint.column - beginPoint.column)/Info.CanvasHeight;
    }
}
