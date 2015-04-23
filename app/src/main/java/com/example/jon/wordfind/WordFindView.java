package com.example.jon.wordfind;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by jon on 3/17/2015.
 */
public class WordFindView extends View {

    private static final int ROW = 10;
    private static final int COL = 10;
    private static final float TOUCH_TOLERANCE = 10;
    private float width;
    private float height;
    private Paint paintScreen;
    private Paint paintLine;
    private Paint paintLetter;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Bitmap listbit;
    private Canvas listCanvas;
    private Board board;
    private ArrayList<String>inGrid;
    private boolean check;
    private final Map<Integer,Path> pathMap = new HashMap<Integer,Path>();
    private final Map<Integer,Point> previousPointMap = new HashMap<Integer,Point>();
    private GestureDetector singleTapDetector;
    private ArrayList<Character> storeWord;
    private ArrayList<Integer> c1;
    private ArrayList<Integer> r1;
    private boolean[][] position;
    private String word;
    private StringBuilder sb;


    public WordFindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(2);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        paintLetter = new Paint();
        paintLetter.setAntiAlias(true);
        paintLetter.setColor(Color.BLACK);
        paintLetter.setStyle(Paint.Style.FILL);
        paintLetter.setTextAlign(Paint.Align.CENTER);
        singleTapDetector = new GestureDetector(getContext(),singleTapListener);
        board = new Board(ROW,COL);
        check = false;
        storeWord = new ArrayList<Character>();
        c1 = new ArrayList<Integer>();
        r1 = new ArrayList<Integer>();

        inGrid = new ArrayList<String>();
        sb = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("what");
        list.add("good");
        list.add("pull");
        list.add("happy");
        list.add("wow");
        list.add("now");
        list.add("bye");
        list.add("list");
        list.add("welcome");


        Random ran = new Random();
        Random row = new Random();
        Random col = new Random();
        Random rev = new Random();
        for(int i = 0; i < list.size(); i++){

            for(int j = 0 ; j < list.size() * list.size(); j++){
                  int r = row.nextInt(ROW);
                  int c = row.nextInt(COL);

                   String word = list.get(i);

                   if(rev.nextInt(2) == 1){
                      word =  board.wordReverse(word);
                   }

                  switch(ran.nextInt(4)+1){
                      case 1:
                          check = board.horizontal(r,c,word);
                          break;
                      case 2:
                         check = board.vertical(r,c,word);
                          break;
                      case 3:
                          check = board.rightDowndiagnol(r,c,word);
                          break;
                      case 4:
                          check = board.rightupDiagnol(r,c,word);
                          break;
                  }

                   if(check){
                       inGrid.add(list.get(i));
                       break;
                   }
            }
        }

        board.fill_board();

        position = new boolean[ROW][COL];

        for(int i = 0; i < position.length; i++) {
            for (int j = 0; j < position.length; j++) {
                position[i][j] = false;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        width =  w / ROW;
        height = (h / COL);

        paintLetter.setTextSize(height * 0.80f);
        paintLetter.setTextScaleX(width / height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,0f,0f,paintScreen);
        //canvas.drawBitmap(listbit,0f,getHeight()/2,paintScreen);
      /*  for(int i = 0; i < ROW; i++){
            canvas.drawLine(i * width,0,i * width,getHeight(),paintLine);
            for(int j = 0; j < COL; j++){
                canvas.drawLine(0,i* height,getWidth(),i *height,paintLine);
            }
        } */

        String[][] temp = board.getBoard(ROW,COL);

        Paint.FontMetrics fm = paintLetter.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;

        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++) {
                canvas.drawText(temp[i][j],(i*width) +x, (j * height) +y,paintLetter);
            }
        }

         //canvas.drawLine(0,0,getWidth(),0,paintLine);

         //for(int i = 0; i < inGrid.size(); i++){
           //  listCanvas.drawText(inGrid.get(i),getWidth()/4, i * height + y,paintLetter);
         //}

           for(int i = 0; i < r1.size(); ++i) {

              if (!position[c1.get(i)][r1.get(i)]) {
                     sb.append(board.returnLetter(c1.get(i),r1.get(i)));
                     position[c1.get(i)][r1.get(i)] = true;
               }
           }

           System.out.println(sb.toString());
            int index = 0;
            boolean b = false;
            for(int i = 0; i < inGrid.size(); i++){

                if( sb.toString().trim().intern() == inGrid.get(i)){
                    for(int j = 0; j < position.length; j++ ){
                        for(int k = 0; k < position.length; k++) {
                            position[j][k] = false;
                        }
                        b = true;
                        index = i;
                    }


                     Toast.makeText(getContext(),inGrid.remove(i)+" was removed",Toast.LENGTH_SHORT).show();
                    paintLine.setColor(Color.BLUE);
                    paintLine.setStrokeWidth(5f);
                }

            }



           for(Integer key: pathMap.keySet())
            canvas.drawPath(pathMap.get(key),paintLine);


    }

    public void hideSystemBars(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_FULLSCREEN| View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void showSystemBars(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private GestureDetector.SimpleOnGestureListener singleTapListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if((getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)== 0)
                hideSystemBars();
            else
                showSystemBars();

            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(singleTapDetector.onTouchEvent(event))
            return true;

        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();

        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN){
            touchStarted(event.getX(actionIndex), event.getY(actionIndex),event.getPointerId(actionIndex));
        }
        else if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP){
            touchEnded(event.getPointerId(actionIndex));
        }
        else{
            touchMoved(event);
        }

      //  Toast.makeText(getContext()," "+board.returnLetter(c1.get(0),r1.get(0)),Toast.LENGTH_SHORT).show();

       invalidate();
        return true;
    }

    private void touchStarted(float x, float y, int lineID){
        Path path;
        Point point;

        if(pathMap.containsKey(lineID)){
            path = pathMap.get(lineID);
            path.reset();
            point = previousPointMap.get(lineID);
        }
        else{
            path = new Path();
            pathMap.put(lineID,path);
            point = new Point();
            previousPointMap.put(lineID,point);
        }

        path.moveTo(x,y);
        point.x = (int) x;
        point.y = (int) y;

        int col = (int)(x / width);
        int row = (int)(y / height);

        if(row < ROW && col < COL){
            c1.clear();
            r1.clear();

            if(!(r1.contains(row) || c1.contains(col))){
                c1.add(col);
                r1.add(row);
            }
        }
    }

    private void touchMoved(MotionEvent event){
        for(int i = 0; i < event.getPointerCount(); i++){
            int pointId = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointId);

            if(pathMap.containsKey(pointId)){
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                Path path = pathMap.get(pointId);
                Point point = previousPointMap.get(pointId);

                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                if(deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE){
                    path.quadTo(point.x,point.y,(newX+point.x)/2,(newY+point.y)/2);

                    point.x = (int) newX;
                    point.y = (int) newY;
                }

                int col = (int)(newX / width);
                int row = (int)(newY / height);

                if(row < ROW && col < COL){
                    c1.add(col);
                    r1.add(row);
                }
            }

        }
    }

    private void touchEnded(int lineID){
        Path path = pathMap.get(lineID);
        bitmapCanvas.drawPath(path,paintLine);
        path.reset();
        sb = new StringBuilder();
    }

}
