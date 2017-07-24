package ayush.practice.motiondetect;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Ayush Jain on 7/21/17.
 */

public class GameView extends View {

    private int width, height, lineWidth;
    private int mazeSizeX, mazeSizeY;
    float cellWidth, cellHeight;
    float totalCellWidth, totalCellHeight;
    private int mazeFinishX, mazeFinishY;
    private DrawMaze maze;
    private Activity context;
    public static ArrayList<Float> verticalStartXList, verticalStartYList, verticalEndYList;
    public static ArrayList<Float> horzStartYList, horzStartXList, horzEndXList;
//    public static ArrayList<Float> finalXPos, finalYPos;
    private Paint line, red, background;

    public GameView(Context context, DrawMaze maze) {
        super(context);
        this.context = (Activity)context;
        this.maze = maze;
        mazeFinishX = maze.getFinalX();
        mazeFinishY = maze.getFinalY();
        mazeSizeX = maze.getMazeWidth();
        mazeSizeY = maze.getMazeHeight();
        line = new Paint();
        line.setColor(Color.WHITE);
        red = new Paint();
        red.setColor(getResources().getColor(R.color.position));
        background = new Paint();
        background.setColor(Color.TRANSPARENT);
        setFocusable(true);
        this.setFocusableInTouchMode(true);

        verticalStartXList = new ArrayList<>();
        verticalStartYList = new ArrayList<>();
        verticalEndYList   = new ArrayList<>();

        horzStartYList   = new ArrayList<>();
        horzStartXList   = new ArrayList<>();
        horzEndXList     = new ArrayList<>();

//        finalXPos        = new ArrayList<>();
//        finalYPos        = new ArrayList<>();

    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = (w < h)?w:h;   //check whether the width or height of the screen is smaller
        height = width;         //for now square mazes
        lineWidth = 1;          //for now 1 pixel wide walls
        cellWidth = (width - ((float)mazeSizeX*lineWidth)) / mazeSizeX;
        totalCellWidth = cellWidth+lineWidth;
        cellHeight = (height - ((float)mazeSizeY*lineWidth)) / mazeSizeY;
        totalCellHeight = cellHeight+lineWidth;
        red.setTextSize(cellHeight*0.75f);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onDraw(Canvas canvas) {
        //fill in the background
        canvas.drawRect(0, 0, width, height, background);

        boolean[][] hLines = maze.getHorizontalLines();
        boolean[][] vLines = maze.getVerticalLines();
        //iterate over the boolean arrays to draw walls
        for(int i = 0; i < mazeSizeX; i++) {
            for(int j = 0; j < mazeSizeY; j++){
                float x = j * totalCellWidth;
                float y = i * totalCellHeight;
                if(j < mazeSizeX - 1 && vLines[i][j]) {
                    //we'll draw a vertical line
                    canvas.drawLine(x + cellWidth,   //start X
                            y,               //start Y
                            x + cellWidth,   //stop X
                            y + cellHeight,  //stop Y
                            line);

                    verticalStartXList.add(x + cellWidth);
                    verticalStartYList.add(y);
                    verticalEndYList.add( y + cellHeight);

                }
                if(i < mazeSizeY - 1 && hLines[i][j]) {
                    //we'll draw a horizontal line
                    canvas.drawLine(x,               //startX
                            y + cellHeight,  //startY
                            x + cellWidth,   //stopX
                            y + cellHeight,  //stopY
                            line);

                    horzStartYList.add(y+cellHeight);
                    horzStartXList.add(x);
                    horzEndXList.add(x+cellWidth);

                }
            }
        }
        canvas.drawText("F",
                (mazeFinishX * totalCellWidth)+(cellWidth*0.25f),
                (mazeFinishY * totalCellHeight)+(cellHeight*0.75f),
                red);

//        finalXPos.add((mazeFinishX * totalCellWidth)+(cellWidth*0.25f));
//        finalYPos.add((mazeFinishY * totalCellHeight)+(cellHeight*0.75f));
    }


//    private void storeVCordinate(float x1, float y1, float y2) {
//
//
//    }
//
//    private void storeHCordinate(float x1, float y1, float x2) {
//
//
//    }

}
