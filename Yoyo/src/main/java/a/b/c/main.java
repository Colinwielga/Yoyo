package a.b.c;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class main extends Activity {

    MySurfaceView mySurfaceView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }

    class MySurfaceView extends SurfaceView implements Runnable{

        Thread thread = null;
        Eq myEq;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;
        final int scale = 20;

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public MySurfaceView(Context context) {
            super(context);
            myEq = new binaryEq(null,"=",false);

            binaryEq LHS = new binaryEq(null, "+", false);
            ((binaryEq)myEq).add(LHS);
            LHS.add(new Value(null,"A",false));
            LHS.add(new Value(null,"B",false));
            LHS.add(new Value(null,"C",false));
            binaryEq RHS = new binaryEq(null, "/", false);
            RHS.add(new Value(null,"D",false));
            RHS.add(new Value(null,"E",false));
            surfaceHolder = getHolder();
            ((binaryEq)myEq).add(RHS);
        }

        public void onResumeMySurfaceView(){
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseMySurfaceView(){
            boolean retry = true;
            running = false;
            while(retry){
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            while(running){
                if(surfaceHolder.getSurface().isValid()){
                    Canvas canvas = surfaceHolder.lockCanvas();

                    // draw background
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);

                    // draw equation
                    paint.setColor(Color.BLACK);
                    drawEq(canvas, myEq.getDrawInfo());
                    canvas.drawText("weeee!", 100, 100, paint);

                    // draw buttons
                    //TODO ? use android buttons OR build our own on the the surface view?

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void drawEq(Canvas canvas, EqDraw drawInfo) {
            //TODO
            float baseX = (canvas.getWidth() - drawInfo.maxX*scale)/2;
            float baseY = (canvas.getHeight() - drawInfo.maxY*scale)/2;
            for (int i =0; i <drawInfo.myEqs.size();i++){
                canvas.drawText( drawInfo.myEqs.get(i).nam,baseX +  drawInfo.myEqs.get(i).x*scale,baseY + drawInfo.myEqs.get(i).y*scale,paint);
            }
        }
    }
}