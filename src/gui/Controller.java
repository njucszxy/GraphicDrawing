package gui;

import graphic.*;
import info.Info;
import info.SingleOperation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    //GUI units
    @FXML
    Canvas canvas;
    @FXML
    Button buttonTriangle;      //button id:0
    @FXML
    Button buttonRectangle;     //button id:1
    @FXML
    Button buttonRound;         //button id:2
    @FXML
    Button buttonOval;          //button id:3
    @FXML
    Button buttonLine;          //button id:4
    @FXML
    Button buttonText;
    @FXML
    TextField graphicText;
    @FXML
    Button buttonCombine;
    @FXML
    Button buttonCopy;
    @FXML
    Button buttonUndo;

    //state machine
    private int currentMode = -1;       //-1:NULL   0:button to draw    1:specific graphic  2:canvas    3:combine   4:copy  5:resize    6:move
    private Graphic currentGraphic = null;    //-1:NULL   else:graphic id
    //private Graphic pastGraphic = null;       //-1:NULL   else:graphic id
    private Point currentPoint = null;  //null:NULL else:canvas point
    private List<SingleOperation> singleOperations = new ArrayList<>();//-1:undefined  0:paint  1:add text  2:paste    3:combine
    private Point beginPoint = null;
    private Point endPoint = null;
    //draw on canvas
    public static final Color graphicColor = Color.web("#000000");
    public static final Color backgroundColor = Color.web("#FFF8DC");
    private GraphicsContext gc;
    private Stage primaryStage;     //for interface jump
    private GraphicManager graphicManager;

    public void initController(Stage primaryStage)
    {
        gc = canvas.getGraphicsContext2D();
        graphicManager = new GraphicManager();
        //set response function
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //pastGraphic = currentGraphic;
                if(currentMode == 3)
                    return;
                if(testMouseClicked(event.getX(),event.getY()))
                {
                    if(currentGraphic.isOnResizable(new Point(event.getX(),event.getY())))
                    {
                        System.out.println("Begin: " + event.getX() + "," + event.getY());
                        beginPoint = new Point(event.getX(), event.getY());
                        currentMode = 5;
                    }
                    else
                    {
                        System.out.println("Begin: " + event.getX() + "," + event.getY());
                        beginPoint = new Point(event.getX(), event.getY());
                        currentMode = 6;
                    }
                }
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Graphic pastGraphic = currentGraphic;
                if(testMouseClicked(event.getX(),event.getY()))
                {
                    if(currentMode == 3)
                    {
                        //combine
                        Graphic graphic1 = pastGraphic;
                        Graphic graphic2 = currentGraphic;
                        if(graphic1 != graphic2) {
                            Graphic newGraphic = graphic1.cooperate(graphic2);
                            graphicManager.deleteGraphic(graphic1);
                            graphicManager.deleteGraphic(graphic2);
                            graphicManager.addGraphic(newGraphic);
                            currentGraphic = newGraphic;

                            recordCombine(graphic1,graphic2,newGraphic);
                        }
                    }
                    currentMode = 1;
                    System.out.println("Select " + currentGraphic.toString());
                }
                else {
                    if(currentMode == 4)
                    {
                        //paste
                        Graphic newGraphic = currentGraphic.getCopy(new Point(event.getX(),event.getY()));
                        graphicManager.addGraphic(newGraphic);
                        recordPaste(newGraphic);
                        currentGraphic = newGraphic;
                        currentMode = 1;
                        System.out.println("Select " + currentGraphic.toString());
                        refreshCanvas();
                    }
                    else if(currentMode == 5)
                    {
                        System.out.println("End: " + event.getX() + "," + event.getY());
                        endPoint = new Point(event.getX(),event.getY());
                        //resize
                        Graphic newGraphic = currentGraphic.getResize(beginPoint,endPoint);
                        graphicManager.deleteGraphic(currentGraphic);
                        graphicManager.addGraphic(newGraphic);
                        recordResize(currentGraphic,newGraphic);
                        currentGraphic = newGraphic;
                        currentMode = 1;
                        System.out.println("Select " + currentGraphic.toString());
                        refreshCanvas();
                    }
                    else if(currentMode == 6)
                    {
                        System.out.println("End: " + event.getX() + "," + event.getY());
                        endPoint = new Point(event.getX(),event.getY());
                        //move
                        Graphic newGraphic = currentGraphic.getMove(beginPoint,endPoint);
                        graphicManager.deleteGraphic(currentGraphic);
                        graphicManager.addGraphic(newGraphic);
                        recordMove(currentGraphic,newGraphic);
                        currentGraphic = newGraphic;
                        currentMode = 1;
                        System.out.println("Select " + currentGraphic.toString());
                        refreshCanvas();
                    }
                    else {
                        currentMode = 2;
                        currentPoint = new Point(event.getX(), event.getY());
                        System.out.println("Clicked " + currentPoint);
                    }
                }
                refreshCanvas();
            }
        });
        buttonTriangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 2)
                {
                    Graphic paintGraphic = new Triangle(currentPoint);
                    graphicManager.addGraphic(paintGraphic);
                    recordPaint(paintGraphic);
                }
                currentMode = 0;
                refreshCanvas();
            }
        });
        buttonRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 2)
                {
                    Graphic paintGraphic = new Rectangle(currentPoint);
                    graphicManager.addGraphic(paintGraphic);
                    recordPaint(paintGraphic);
                }
                currentMode = 0;
                refreshCanvas();
            }
        });
        buttonRound.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 2)
                {
                    Graphic paintGraphic = new Round(currentPoint);
                    graphicManager.addGraphic(paintGraphic);
                    recordPaint(paintGraphic);
                }
                currentMode = 0;
                refreshCanvas();
            }
        });
        buttonOval.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 2)
                {
                    Graphic paintGraphic = new Oval(currentPoint);
                    graphicManager.addGraphic(paintGraphic);
                    recordPaint(paintGraphic);
                }
                currentMode = 0;
                refreshCanvas();
            }
        });
        buttonLine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 2)
                {
                    Graphic paintGraphic = new Line(currentPoint);
                    graphicManager.addGraphic(paintGraphic);
                    recordPaint(paintGraphic);
                }
                currentMode = 0;
                refreshCanvas();
            }
        });
        buttonText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 1)
                {
                    currentGraphic.addText(graphicText.getText());
                    recordText(currentGraphic);
                    System.out.println("get text:" + graphicText.getText());
                    refreshCanvas();
                }
            }
        });
        buttonCombine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 1)
                {
                    //combine
                    currentMode = 3;
                }
            }
        });
        buttonCopy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(currentMode == 1)
                {
                    //copy
                    currentMode = 4;
                }
            }
        });
        buttonUndo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //undo
                undoSingleOperation();
            }
        });
        this.primaryStage = primaryStage;
        refreshCanvas();
    }
    public void refreshCanvas()
    {
        //clear
        gc.clearRect(0,0, Info.CanvasWidth,Info.CanvasHeight);
        gc.setFill(backgroundColor);
        gc.fillRect(0,0,Info.CanvasWidth,Info.CanvasHeight);

        //draw existing graphics
        for(int i = 0;i < graphicManager.getSize();i++)
            graphicManager.getGraphic(i).draw(this.gc);

        if(currentMode == 2)
        {
            //draw mouse click
            gc.setFill(graphicColor);
            gc.fillOval(currentPoint.row,currentPoint.column,10,10);
        }
    }
    private boolean testMouseClicked(double row,double column)
    {
        for(int i = 0;i < graphicManager.getSize();i++)
        {
            if(graphicManager.getGraphic(i).isOnMe(row,column))
            {
                currentGraphic = graphicManager.getGraphic(i);
                return true;
            }
        }
        return false;
    }
    private void recordPaint(Graphic paintGraphic)
    {
        SingleOperation singleOperation = new SingleOperation();
        singleOperation.operationMode = 0;
        singleOperation.paintGraphic = paintGraphic;
        singleOperations.add(singleOperation);
    }
    private void recordText(Graphic textGraphic)
    {
        SingleOperation singleOperation = new SingleOperation();
        singleOperation.operationMode = 1;
        singleOperation.textGraphic = textGraphic;
        singleOperations.add(singleOperation);
    }
    private void recordPaste(Graphic pasteGraphic)
    {
        SingleOperation singleOperation = new SingleOperation();
        singleOperation.operationMode = 2;
        singleOperation.pasteGraphic = pasteGraphic;
        singleOperations.add(singleOperation);
    }
    private void recordCombine(Graphic combineGraphic1,Graphic combineGraphic2,Graphic afterCombineGraphic)
    {
        SingleOperation singleOperation = new SingleOperation();
        singleOperation.operationMode = 3;
        singleOperation.combineGraphic1 = combineGraphic1;
        singleOperation.combineGraphic2 = combineGraphic2;
        singleOperation.afterCombineGraphic = afterCombineGraphic;
        singleOperations.add(singleOperation);
    }
    private void recordResize(Graphic oldGraphic,Graphic resizeGraphic)
    {
        SingleOperation singleOperation = new SingleOperation();
        singleOperation.operationMode = 4;
        singleOperation.oldGraphic = oldGraphic;
        singleOperation.resizeGraphic = resizeGraphic;
        singleOperations.add(singleOperation);
    }
    private void recordMove(Graphic beforeGraphic,Graphic moveGraphic)
    {
        SingleOperation singleOperation = new SingleOperation();
        singleOperation.operationMode = 5;
        singleOperation.beforeGraphic = beforeGraphic;
        singleOperation.moveGraphic = moveGraphic;
        singleOperations.add(singleOperation);
    }
    private void undoSingleOperation()
    {
        if(singleOperations.size() == 0)
            return;
        SingleOperation singleOperation = singleOperations.get(singleOperations.size()-1);
        singleOperations.remove(singleOperation);
        if(singleOperation.operationMode == 0) {
            //undo paint
            if(singleOperation.paintGraphic == null)
                assert false;
            graphicManager.deleteGraphic(singleOperation.paintGraphic);
        }
        else if(singleOperation.operationMode == 1)
        {
            //undo text
            if(singleOperation.textGraphic == null)
                assert false;
            singleOperation.textGraphic.clearText();
        }
        else if(singleOperation.operationMode == 2)
        {
            //undo paste
            if(singleOperation.pasteGraphic == null)
                assert false;
            graphicManager.deleteGraphic(singleOperation.pasteGraphic);
        }
        else if(singleOperation.operationMode == 3)
        {
            //undo combine
            if(singleOperation.combineGraphic1 == null)
                assert false;
            if(singleOperation.combineGraphic2 == null)
                assert false;
            if(singleOperation.afterCombineGraphic == null)
                assert false;
            graphicManager.deleteGraphic(singleOperation.afterCombineGraphic);
            graphicManager.addGraphic(singleOperation.combineGraphic1);
            graphicManager.addGraphic(singleOperation.combineGraphic2);
        }
        else if(singleOperation.operationMode == 4)
        {
            //undo resize
            if(singleOperation.oldGraphic == null)
                assert false;
            if(singleOperation.resizeGraphic == null)
                assert false;
            graphicManager.deleteGraphic(singleOperation.resizeGraphic);
            graphicManager.addGraphic(singleOperation.oldGraphic);
        }
        else if(singleOperation.operationMode == 5)
        {
            //undo move
            if(singleOperation.beforeGraphic == null)
                assert false;
            if(singleOperation.moveGraphic == null)
                assert false;
            graphicManager.deleteGraphic(singleOperation.moveGraphic);
            graphicManager.addGraphic(singleOperation.beforeGraphic);
        }
        else
            assert false;
        refreshCanvas();
    }
}
