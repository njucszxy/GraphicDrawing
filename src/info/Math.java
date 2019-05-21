package info;

import graphic.Point;

public class Math {
    public static double minDistancePointToPoint(Point point1,Point point2)
    {
        double deltaX = point1.row - point2.row;
        double deltaY = point1.column - point2.column;
        return java.lang.Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }
    public static double minDistancePointToLine(Point point1,Point point2,Point testPoint)
    {
        if(point1.column == point2.column)
        {
            boolean firstIsLeft = true;
            if(point1.row > point2.row)
                firstIsLeft = false;
            if(firstIsLeft)
            {
                if(testPoint.row < point1.row)
                    return minDistancePointToPoint(testPoint,point1);
                else if(testPoint.row > point2.row)
                    return minDistancePointToPoint(testPoint,point2);
                else
                    return java.lang.Math.abs(testPoint.column - point1.column);
            }
            else
            {
                if(testPoint.row < point2.row)
                    return minDistancePointToPoint(testPoint,point2);
                else if(testPoint.row > point1.row)
                    return minDistancePointToPoint(testPoint,point1);
                else
                    return java.lang.Math.abs(testPoint.column - point1.column);
            }
        }
        else if(point1.row == point2.row)
        {
            boolean firstIsUp = true;
            if(point1.column > point2.column)
                firstIsUp = false;
            if(firstIsUp)
            {
                if(testPoint.column < point1.column)
                    return minDistancePointToPoint(testPoint,point1);
                else if(testPoint.column > point2.column)
                    return minDistancePointToPoint(testPoint,point2);
                else
                    return java.lang.Math.abs(testPoint.row - point1.row);
            }
            else
            {
                if(testPoint.column < point2.column)
                    return minDistancePointToPoint(testPoint,point2);
                else if(testPoint.column > point1.column)
                    return minDistancePointToPoint(testPoint,point1);
                else
                    return java.lang.Math.abs(testPoint.row - point1.row);
            }
        }
        else
        {
            double x1 = point1.row;
            double y1 = point1.column;
            double x2 = point2.row;
            double y2 = point2.column;
            double x3 = testPoint.row;
            double y3 = testPoint.column;
            double k = (x1-x2)/(y2-y1) - (y2-y1)/(x2-x1);
            double b = (y1*x2 - x1*y2)/(x2-x1) + x3*(x1-x2)/(y2-y1) - y3;
            double xResult = b/k;
            double yResult = xResult*(y2-y1)/(x2-x1) + (y1*x2 - x1*y2)/(x2-x1);
            if(xResult < java.lang.Math.min(x1,x2))
            {
                if(x1 < x2)
                    return minDistancePointToPoint(testPoint,point1);
                else
                    return minDistancePointToPoint(testPoint,point2);
            }
            else if(xResult > java.lang.Math.max(x1,x2))
            {
                if(x1 <= x2)
                    return minDistancePointToPoint(testPoint,point2);
                else
                    return minDistancePointToPoint(testPoint,point1);
            }
            else
                return minDistancePointToPoint(testPoint,new Point(xResult,yResult));
        }
    }
    public static double minDistancePointToRound(Point center,double radius,Point testPoint)
    {
        double pointToCenter = minDistancePointToPoint(center,testPoint);
        return java.lang.Math.abs(pointToCenter - radius);
    }
    public static double minDistancePointToOval(Point center,double a,double b,Point testPoint)
    {
        if(a == b)
            return minDistancePointToRound(center,a,testPoint);
        else
        {
            double x = -center.row;
            double y = center.column;
            double x1 = -testPoint.row;
            double y1 = testPoint.column;
            double pointToCenter = minDistancePointToPoint(center,testPoint);
            if(x1 == 0)
                return x-a;
            else if(y1 == 0)
                return -y-b;
            else
            {
                //set center to 0,0
                double newY1 = -testPoint.row + center.row;
                double newX1 = testPoint.column - center.column;
                double RESULTX = java.lang.Math.sqrt(a*a*b*b/(b*b + a*a*newY1*newY1/(newX1*newX1)));
                double RESULTY = RESULTX*newY1/newX1;
                double r = minDistancePointToPoint(new Point(RESULTX,RESULTY),new Point(0,0));
                //System.out.println("r " + r);
                //System.out.println("dis " + java.lang.Math.abs(pointToCenter - r));
                return java.lang.Math.abs(pointToCenter - r);
            }
        }
    }
}
