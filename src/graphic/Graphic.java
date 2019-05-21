package graphic;

import gui.Controller;
import info.Rate;
import javafx.scene.canvas.GraphicsContext;

public abstract class Graphic {
    private String text = null;
    protected GraphicsContext gc = null;
    private boolean isCombination = false;
    protected int resizablePoint = -1;

    public abstract void draw(GraphicsContext gc);
    public void addText(String text)
    {
        this.text = text;
    }
    protected void setCombination()
    {
        this.isCombination = true;
    }
    public boolean getCombination()
    {
        return this.isCombination;
    }
    public String getText()
    {
        return this.text;
    }
    public Graphic cooperate(Graphic anotherGraphic)
    {
        if(anotherGraphic.getCombination())
        {
            //one + many
            Graphic newGraphic = anotherGraphic.cooperate(this);
            return newGraphic;
        }
        else
        {
            //one + one
            Graphic newGraphic = new CombinedGraphic();
            newGraphic.cooperate(this);
            newGraphic.cooperate(anotherGraphic);
            return newGraphic;
        }
    }
    public abstract Graphic getCopy(Point point);
    public abstract boolean isOnMe(double row,double column);
    public void clearText()
    {
        this.text = null;
    }
    public abstract boolean isOnResizable(Point point);
    public abstract Graphic getResize(Point beginPoint,Point endPoint);
    public int getResizablePoint(){return resizablePoint;}
    public abstract Graphic getMove(Point beginPoint,Point endPoint);
}
