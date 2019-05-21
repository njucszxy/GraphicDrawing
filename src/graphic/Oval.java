package graphic;

import gui.Controller;
import info.Math;
import info.Rate;
import javafx.scene.canvas.GraphicsContext;

public class Oval extends Graphic {
    private Point upperLeft = null;
    private double a = 0;
    private double b = 0;

    public Oval() {}
    public Oval(Point point)
    {
        upperLeft = point;
        a = 25;
        b = 20;
    }
    public Oval(Oval oval)
    {
        this.upperLeft = new Point(oval.upperLeft);
        this.a = oval.a;
        this.b = oval.b;
    }
    public Oval(Oval oval,Point point)
    {
        this.upperLeft = new Point(point);
        this.a = oval.a;
        this.b = oval.b;
    }
    public Oval(Oval oval,Point beginPoint,Point endPoint)
    {
        this.upperLeft = new Point(oval.upperLeft);
        this.a = oval.a;
        this.b = oval.b;
        Rate rate = new Rate(beginPoint,endPoint);
        if(rate.xRate > 0)
            this.a = this.a * rate.xRate;
        else
            this.a = this.a / java.lang.Math.abs(rate.xRate);
        if(rate.yRate > 0)
            this.b = this.b * rate.yRate;
        else
            this.b = this.b / java.lang.Math.abs(rate.yRate);
    }
    public Oval(Oval oval,double xOffset,double yOffset)
    {
        this.upperLeft = new Point(oval.upperLeft.row+xOffset,oval.upperLeft.column+yOffset);
        this.a = oval.a;
        this.b = oval.b;
    }
    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.gc == null)
            this.gc = gc;
        if(upperLeft == null)
        {
            //new oval at corner
            a = 25;
            b = 20;
            upperLeft = new Point(0,0);
        }
        //draw oval
        //System.out.println("draw oval");
        gc.setFill(Controller.graphicColor);
        gc.strokeOval(upperLeft.row,upperLeft.column,2*a,2*b);
        gc.strokeText(this.getText(),upperLeft.row,upperLeft.column);
    }
    @Override
    public Graphic getCopy(Point point)
    {
        return new Oval(this,point);
    }
    @Override
    public boolean isOnMe(double row,double column)
    {
        //test
        if(upperLeft == null)
            return false;
        //compute
        Point center = new Point(upperLeft.row+a,upperLeft.column+b);
        if(Math.minDistancePointToOval(center,a,b,new Point(row,column)) <= 5)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return new String("Oval");
    }
    @Override
    public boolean isOnResizable(Point point)
    {
        //test
        if(upperLeft == null)
            return false;
        if(point.row < upperLeft.row + a)
            return false;        //left for move
        else
            return true;       //right for resize
    }
    @Override
    public Graphic getResize(Point beginPoint,Point endPoint)
    {
        return new Oval(this,beginPoint,endPoint);
    }
    @Override
    public Graphic getMove(Point beginPoint,Point endPoint)
    {
        return new Oval(this,endPoint.row - beginPoint.row,endPoint.column-beginPoint.column);
    }
}