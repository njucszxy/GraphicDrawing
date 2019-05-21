package graphic;

import java.util.ArrayList;
import java.util.List;

public class GraphicManager {
    private List<Graphic> graphics;
    public GraphicManager()
    {
        graphics = new ArrayList<>();
    }
    public void addGraphic(Graphic graphic)
    {
        graphics.add(graphic);
    }
    public void deleteGraphic(Graphic graphic)
    {
        graphics.remove(graphic);
    }
    public int getSize()
    {
        return graphics.size();
    }
    public Graphic getGraphic(int id)
    {
        if(id >= 0 && id < graphics.size())
            return graphics.get(id);
        return null;
    }
}
