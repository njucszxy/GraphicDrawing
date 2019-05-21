package graphic;

import gui.Controller;
import info.Math;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Graphic {
    private List<Point> points = null;

    public Triangle()
    {
        points = new ArrayList<>();
    }
    public Triangle(Point point)
    {
        points = new ArrayList<>();
        points.add(point);
        points.add(new Point(point.row + 25,point.column));
        points.add(new Point(point.row,point.column + 25));
    }
    public Triangle(Triangle triangle)
    {
        points = new ArrayList<>();
        if(triangle.points.size() != 3)
            assert false;
        points.add(new Point(triangle.points.get(0)));
        points.add(new Point(triangle.points.get(1)));
        points.add(new Point(triangle.points.get(2)));
    }
    public Triangle(Triangle triangle,Point point)
    {
        points = new ArrayList<>();
        if(triangle.points.size() != 3)
            assert false;
        points.add(new Point(point));
        points.add(new Point(point.row + 50,point.column - 25));
        points.add(new Point(point.row + 50,point.column + 25));
    }
    public Triangle(Triangle triangle,Point beginPoint,Point endPoint)
    {
        points = new ArrayList<>();
        if(triangle.points.size() != 3)
            assert false;
        int rePoint = triangle.getResizablePoint();
        if(rePoint == 1)
        {
            points.add(endPoint);
            points.add(triangle.points.get(1));
            points.add(triangle.points.get(2));
        }
        else if(rePoint == 2)
        {
            points.add(triangle.points.get(0));
            points.add(endPoint);
            points.add(triangle.points.get(2));
        }
        else
        {
            points.add(triangle.points.get(0));
            points.add(triangle.points.get(1));
            points.add(endPoint);
        }
    }
    public Triangle(Triangle triangle,double xOffset,double yOffset)
    {
        points = new ArrayList<>();
        if(triangle.points.size() != 3)
            assert false;
        points.add(new Point(triangle.points.get(0).row + xOffset,triangle.points.get(0).column + yOffset));
        points.add(new Point(triangle.points.get(1).row + xOffset,triangle.points.get(1).column + yOffset));
        points.add(new Point(triangle.points.get(2).row + xOffset,triangle.points.get(2).column + yOffset));
    }
    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.gc == null)
        this.gc = gc;
        if(points.size() == 0)
        {
            //new triangle at corner
            points.add(new Point(0,25));
            points.add(new Point(50,0));
            points.add(new Point(50,50));
        }
        //test
        if(points.size() != 3)
            assert false;
        //draw triangle
        //System.out.println("draw triangle");
        gc.setFill(Controller.graphicColor);
        gc.strokeLine(points.get(0).row,points.get(0).column,points.get(1).row,points.get(1).column);
        gc.strokeLine(points.get(1).row,points.get(1).column,points.get(2).row,points.get(2).column);
        gc.strokeLine(points.get(2).row,points.get(2).column,points.get(0).row,points.get(0).column);
        gc.strokeText(this.getText(),points.get(0).row,points.get(0).column);
    }
    @Override
    public Graphic getCopy(Point point)
    {
        return new Triangle(this,point);
    }
    @Override
    public boolean isOnMe(double row,double column)
    {
        //test
        if(points.size() != 3)
            return false;
        //compute
        Point testPoint = new Point(row,column);
        if(Math.minDistancePointToLine(points.get(0),points.get(1),testPoint) <= 5 ||
                Math.minDistancePointToLine(points.get(1),points.get(2),testPoint) <= 5 ||
                Math.minDistancePointToLine(points.get(2),points.get(0),testPoint) <= 5)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return new String("Triangle");
    }
    @Override
    public boolean isOnResizable(Point point)
    {
        //test
        if(points.size() != 3)
            return false;
        double d1 = Math.minDistancePointToPoint(points.get(0),point);
        double d2 = Math.minDistancePointToPoint(points.get(1),point);
        double d3 = Math.minDistancePointToPoint(points.get(2),point);
        double min = java.lang.Math.min(d1,d2);
        min = java.lang.Math.min(min,d3);
        if(min >= 5)
            return false;
        else
        {
            if(min == d1)
                resizablePoint = 1;
            else if(min == d2)
                resizablePoint = 2;
            else
                resizablePoint = 3;
            return true;
        }
    }
    @Override
    public Graphic getResize(Point beginPoint,Point endPoint)
    {
        return new Triangle(this,beginPoint,endPoint);
    }
    @Override
    public Graphic getMove(Point beginPoint,Point endPoint)
    {
        return new Triangle(this,endPoint.row - beginPoint.row,endPoint.column-beginPoint.column);
    }
}
