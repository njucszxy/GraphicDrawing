package graphic;

import info.Rate;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class CombinedGraphic extends Graphic {
    private List<Graphic> graphics = new ArrayList<>();

    public CombinedGraphic()
    {
        this.setCombination();
    }
    public CombinedGraphic(CombinedGraphic combinedGraphic,Point point)
    {
        this.setCombination();
        for(int i = 0;i < combinedGraphic.graphics.size();i++)
            this.graphics.add(combinedGraphic.graphics.get(i).getCopy(point));
    }
    public CombinedGraphic(CombinedGraphic combinedGraphic,Point beginPoint,Point endPoint)
    {
        this.setCombination();
        for(int i = 0;i < combinedGraphic.graphics.size();i++)
            this.graphics.add(combinedGraphic.graphics.get(i).getResize(beginPoint, endPoint));
    }
    public CombinedGraphic(CombinedGraphic combinedGraphic,Point beginPoint,Point endPoint,int type)
    {
        this.setCombination();
        for(int i = 0;i < combinedGraphic.graphics.size();i++)
            this.graphics.add(combinedGraphic.graphics.get(i).getMove(beginPoint, endPoint));
    }
    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.gc == null)
            this.gc = gc;
        for(int i = 0;i < graphics.size();i++)
            graphics.get(i).draw(gc);
    }
    @Override
    public Graphic cooperate(Graphic anotherGraphic)
    {
        if(anotherGraphic.getCombination())
        {
            //many + many
            CombinedGraphic combinedGraphic = (CombinedGraphic)anotherGraphic;
            for(int i = 0;i < combinedGraphic.graphics.size();i++)
                this.graphics.add(combinedGraphic.graphics.get(i));
        }
        else {
            //many + one
            graphics.add(anotherGraphic);
        }
        return this;
    }
    @Override
    public Graphic getCopy(Point point)
    {
        return new CombinedGraphic(this,point);
    }
    @Override
    public boolean isOnMe(double row,double column)
    {
        for(int i = 0;i < graphics.size();i++)
        {
            if(graphics.get(i).isOnMe(row,column))
                return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Combination:");
        for(int i = 0;i < graphics.size();i++)
        {
            stringBuilder.append(" ");
            stringBuilder.append(graphics.get(i).toString());
        }
        return stringBuilder.toString();
    }
    @Override
    public String getText()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i < graphics.size();i++)
            stringBuilder.append(graphics.get(i).getText());
        return stringBuilder.toString();
    }
    @Override
    public boolean isOnResizable(Point point)
    {
        for(int i = 0;i < graphics.size();i++)
        {
            if(graphics.get(i).isOnResizable(point))
                return true;
        }
        return false;
    }
    @Override
    public Graphic getResize(Point beginPoint,Point endPoint)
    {
        return new CombinedGraphic(this,beginPoint,endPoint);
    }
    @Override
    public Graphic getMove(Point beginPoint,Point endPoint)
    {
        return new CombinedGraphic(this,beginPoint,endPoint,0);
    }
}
