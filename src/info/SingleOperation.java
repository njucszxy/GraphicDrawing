package info;

import graphic.Graphic;

public class SingleOperation {
    public int operationMode = -1;  //-1:undefined  0:paint  1:add text  2:paste    3:combine   4:resize    5:move

    //paint
    public Graphic paintGraphic = null;

    //add text
    public Graphic textGraphic = null;

    //paste
    public Graphic pasteGraphic = null;

    //combine
    public Graphic combineGraphic1 = null;
    public Graphic combineGraphic2 = null;
    public Graphic afterCombineGraphic = null;

    //resize
    public Graphic oldGraphic = null;
    public Graphic resizeGraphic = null;

    //move
    public Graphic beforeGraphic = null;
    public Graphic moveGraphic = null;
}
