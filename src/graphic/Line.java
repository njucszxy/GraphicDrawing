package graphic;

import gui.Controller;
import info.Math;
import info.Rate;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Line extends Graphic {
    private List<Point> points = null;

    public Line()
    {
        points = new ArrayList<>();
        //points.add(new Point());    //point 1
        //points.add(new Point());    //point 2
    }
    public Line(Line line)
    {
        points = new ArrayList<>();
        if(line.points.size() != 2)
            assert false;
        points.add(new Point(line.points.get(0)));
        points.add(new Point(line.points.get(1)));
    }
    public Line(Line line,Point point)
    {
        points = new ArrayList<>();
        if(line.points.size() != 2)
            assert false;
        points.add(new Point(point));
        points.add(new Point(point.row+50,point.column - 50));
    }
    public Line(Line line,Point beginPoint,Point endPoint)
    {
        points = new ArrayList<>();
        if(line.points.size() != 2)
            assert false;
        int rePoint = line.getResizablePoint();
        if(rePoint == 1)
        {
            points.add(endPoint);
            points.add(line.points.get(1));
        }
        else
        {
            points.add(line.points.get(0));
            points.add(endPoint);
        }
    }
    public Line(Point point)
    {
        points = new ArrayList<>();
        points.add(point);
        points.add(new Point(point.row + 10,point.column + 10));
    }
    public Line(Line line,double xOffset,double yOffset)
    {
        points = new ArrayList<>();
        if(line.points.size() != 2)
            assert false;
        points.add(new Point(line.points.get(0).row + xOffset,line.points.get(0).column + yOffset));
        points.add(new Point(line.points.get(1).row + xOffset,line.points.get(1).column + yOffset));
    }
    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.gc == null)
            this.gc = gc;
        if(points.size() == 0)
        {
            //new Line at corner
            points.add(new Point(0,50));
            points.add(new Point(50,0));
        }
        //test
        if(points.size() != 2)
            assert false;
        //draw line
        //System.out.println("draw line");
        gc.setFill(Controller.graphicColor);
        gc.strokeLine(points.get(0).row,points.get(0).column,points.get(1).row,points.get(1).column);
        gc.strokeText(this.getText(),points.get(0).row,points.get(0).column);
    }
    @Override
    public Graphic getCopy(Point point)
    {
        return new Line(this,point);
    }
    @Override
    public boolean isOnMe(double row,double column)
    {
        //test
        if(points.size() != 2)
            return false;
        //compute
        if(Math.minDistancePointToLine(points.get(0),points.get(1),new Point(row,column)) <= 5)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return new String("Line");
    }
    @Override
    public boolean isOnResizable(Point point)
    {
        //test
        if(points.size() != 2)
            return false;
        double d1 = Math.minDistancePointToPoint(points.get(0),point);
        double d2 = Math.minDistancePointToPoint(points.get(1),point);
        double minDistance = java.lang.Math.min(d1,d2);
        if(minDistance >= 5)
            return false;
        else
        {
            if(minDistance == d1)
            {
                resizablePoint = 1;     //point 1
                return true;
            }
            else
            {
                resizablePoint = 2;      //point 2
                return true;
            }
        }
    }
    @Override
    public Graphic getResize(Point beginPoint,Point endPoint)
    {
        return new Line(this,beginPoint,endPoint);
    }
    @Override
    public Graphic getMove(Point beginPoint,Point endPoint)
    {
        return new Line(this,endPoint.row - beginPoint.row,endPoint.column-beginPoint.column);
    }
}
