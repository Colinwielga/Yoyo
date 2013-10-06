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
        Eq myEq = new binaryEq();
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public MySurfaceView(Context context) {
            super(context);
            surfaceHolder = getHolder();
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
                    drawEq(canvas, myEq.getDrawInfo());

                    // draw buttons
                    //TODO ? use android buttons OR build our own on the the surface view?

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void drawEq(Canvas canvas, EqDraw drawInfo) {
            //TODO

            for (int i =0; i <drawInfo.myEqs.size();i++){
                Log.i("draw info", drawInfo.myEqs.get(i).x + " , " + drawInfo.myEqs.get(i).y + " , " + drawInfo.myEqs.get(i).nam + " , " + drawInfo.myEqs.get(i).selected);
            }
        }

    }


}