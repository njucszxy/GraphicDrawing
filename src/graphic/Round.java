package graphic;

import gui.Controller;
import info.Math;
import info.Rate;
import javafx.scene.canvas.GraphicsContext;

public class Round extends Graphic {
    private Point upperLeft = null;
    private double radius = 0;

    public Round() {}
    public Round(Point point)
    {
        upperLeft = point;
        radius = 25;
    }
    public Round(Round round)
    {
        this.upperLeft = new Point(round.upperLeft);
        this.radius = round.radius;
        if(round.getText() != null)
            this.addText(new String(round.getText()));
    }
    public Round(Round round,Point point)
    {
        this.upperLeft = new Point(point);
        this.radius = round.radius;
        if(round.getText() != null)
            this.addText(new String(round.getText()));
    }
    public Round(Round round,Point beginPoint,Point endPoint)
    {
        this.upperLeft = new Point(round.upperLeft);
        this.radius = round.radius;
        if(round.getText() != null)
            this.addText(new String(round.getText()));
        Rate rate = new Rate(beginPoint,endPoint);
        if(rate.xRate > 0)
            this.radius = this.radius * rate.xRate;
        else
            this.radius = this.radius / java.lang.Math.abs(rate.xRate);
    }
    public Round(Round round,double xOffset,double yOffset)
    {
        this.upperLeft = new Point(round.upperLeft.row+xOffset,round.upperLeft.column+yOffset);
        this.radius = round.radius;
        if(round.getText() != null)
            this.addText(new String(round.getText()));
    }
    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.gc == null)
            this.gc = gc;
        if(upperLeft == null)
        {
            //new round at corner
            radius = 25;
            upperLeft = new Point(0,0);
        }
        //draw round
        gc.setFill(Controller.graphicColor);
        gc.strokeOval(upperLeft.row,upperLeft.column,2*radius,2*radius);
        gc.strokeText(this.getText(),upperLeft.row,upperLeft.column);
    }
    @Override
    public Graphic getCopy(Point point)
    {
        return new Round(this,point);
    }
    @Override
    public boolean isOnMe(double row,double column)
    {
        //test
        if(upperLeft == null)
            return false;
        //compute
        Point center = new Point(upperLeft.row+radius,upperLeft.column+radius);
        if(Math.minDistancePointToRound(center,radius,new Point(row,column)) <= 5)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return new String("Round");
    }
    @Override
    public boolean isOnResizable(Point point)
    {
        //test
        if(upperLeft == null)
            return false;
        if(point.row < upperLeft.row + radius)
            return false;        //left for move
        else
            return true;       //right for resize
    }
    @Override
    public Graphic getResize(Point beginPoint,Point endPoint)
    {
        return new Round(this,beginPoint,endPoint);
    }
    @Override
    public Graphic getMove(Point beginPoint,Point endPoint)
    {
        return new Round(this,endPoint.row - beginPoint.row,endPoint.column-beginPoint.column);
    }
}