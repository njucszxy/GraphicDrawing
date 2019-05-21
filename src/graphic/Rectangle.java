package graphic;

import gui.Controller;
import info.Math;
import info.Rate;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Graphic {
    private Point upperLeft = null;
    private double width;
    private double height;

    public Rectangle(){}
    public Rectangle(Point point)
    {
        upperLeft = point;
        width = 50;
        height = 40;
    }
    public Rectangle(Rectangle rectangle)
    {
        this.upperLeft = new Point(rectangle.upperLeft);
        this.width = rectangle.width;
        this.height = rectangle.height;
    }
    public Rectangle(Rectangle rectangle,Point point)
    {
        this.upperLeft = new Point(point);
        this.width = rectangle.width;
        this.height = rectangle.height;
    }
    public Rectangle(Rectangle rectangle,Point beginPoint,Point endPoint)
    {
        this.upperLeft = new Point(rectangle.upperLeft);
        this.width = rectangle.width;
        this.height = rectangle.height;

        //System.out.println("before width:" + width);
        //System.out.println("before height:" + height);

        Rate rate = new Rate(beginPoint,endPoint);
        if(rate.xRate > 0)
            this.width = this.width * rate.xRate;
        else
            this.width = this.width / java.lang.Math.abs(rate.xRate);
        if(rate.yRate > 0)
            this.height = this.height * rate.yRate;
        else
            this.height = this.height / java.lang.Math.abs(rate.yRate);

        //System.out.println("after width:" + width);
        //System.out.println("after height:" + height);

        //this.width = rectangle.width*2;
        //this.height = rectangle.height*2;
    }
    public Rectangle(Rectangle rectangle,double xOffset,double yOffset)
    {
        this.upperLeft = new Point(rectangle.upperLeft.row+xOffset,rectangle.upperLeft.column+yOffset);
        this.width = rectangle.width;
        this.height = rectangle.height;
    }
    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.gc == null)
            this.gc = gc;
        if(upperLeft == null)
        {
            //new Rectangle at corner
            upperLeft = new Point(0,0);
            width = 50;
            height = 40;
        }
        //draw rectangle
        //System.out.println("draw rectangle");
        gc.setFill(Controller.graphicColor);
        gc.strokeRect(upperLeft.row,upperLeft.column,width,height);
        gc.strokeText(this.getText(),upperLeft.row,upperLeft.column);
    }
    @Override
    public Graphic getCopy(Point point)
    {
        return new Rectangle(this,point);
    }
    @Override
    public boolean isOnMe(double row,double column)
    {
        //test
        if(upperLeft == null)
            return false;
        //compute
        Point point1 = new Point(upperLeft.row,upperLeft.column);
        Point point2 = new Point(upperLeft.row + width,upperLeft.column);
        Point point3 = new Point(upperLeft.row,upperLeft.column + height);
        Point point4 = new Point(upperLeft.row + width,upperLeft.column + height);
        Point testPoint = new Point(row,column);
        if(Math.minDistancePointToLine(point1,point2,testPoint) <= 5 ||
           Math.minDistancePointToLine(point1,point3,testPoint) <= 5 ||
           Math.minDistancePointToLine(point3,point4,testPoint) <= 5 ||
           Math.minDistancePointToLine(point2,point4,testPoint) <= 5)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return new String("Rectangle");
    }
    @Override
    public boolean isOnResizable(Point point)
    {
        //test
        if(upperLeft == null)
            return false;
        double d1 = Math.minDistancePointToPoint(upperLeft,point);
        double d2 = Math.minDistancePointToPoint(new Point(upperLeft.row + width,upperLeft.column),point);
        double d3 = Math.minDistancePointToPoint(new Point(upperLeft.row,upperLeft.column+height),point);
        double d4 = Math.minDistancePointToPoint(new Point(upperLeft.row + width,upperLeft.column+height),point);

        /*
        System.out.println("point1:"+upperLeft);
        System.out.println("point2:"+new Point(upperLeft.row + width,upperLeft.column));
        System.out.println("point3:"+new Point(upperLeft.row,upperLeft.column+height));
        System.out.println("point4:"+new Point(upperLeft.row + width,upperLeft.column+height));
        System.out.println("Mouse:" + point);

        System.out.println("d1:"+d1);
        System.out.println("d2:"+d2);
        System.out.println("d3:"+d3);
        System.out.println("d4:"+d4);
        */

        double min = java.lang.Math.min(d1,d2);
        min = java.lang.Math.min(min,d3);
        min = java.lang.Math.min(min,d4);
        if(min >= 5)
            return false;
        else
        {
            if(min == d1)
                resizablePoint = 1;
            else if(min == d2)
                resizablePoint = 2;
            else if(min == d3)
                resizablePoint = 3;
            else
                resizablePoint = 4;

            //System.out.println("Can resize");

            return true;
        }
    }
    @Override
    public Graphic getResize(Point beginPoint,Point endPoint)
    {
        return new Rectangle(this,beginPoint,endPoint);
    }
    @Override
    public Graphic getMove(Point beginPoint,Point endPoint)
    {
        return new Rectangle(this,endPoint.row - beginPoint.row,endPoint.column-beginPoint.column);
    }
}