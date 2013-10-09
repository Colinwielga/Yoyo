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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

// this is a little rapper around our custom SurfaceView.. i got i out of some random tutorial
// i think we want so the surfaceView can act like an activity (a screen in an app)
// we don't want the surface to be an activity anyway, since that would not allow us to overlay buttons over it

public class main extends Activity implements View.OnTouchListener {

    MySurfaceView mySurfaceView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // load the previous state
        super.onCreate(savedInstanceState);
        // create the surfaceView
        mySurfaceView = new MySurfaceView(this);
        // listen for touches on the surface
        mySurfaceView.setOnTouchListener(this);
        // show the surface to the user (or somethings)
        setContentView(mySurfaceView);
    }

    // resume the app
    @Override
    protected void onResume() {
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    // pause the app
    @Override
    protected void onPause() {
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // pass the touch event on to the surface
        mySurfaceView.onTouch(motionEvent);
        return true;
    }

    // this surfaceView is the main thing the use interacts with, it draws what the user sees and handles their inputs
    class MySurfaceView extends SurfaceView implements Runnable{

        Thread thread = null;
        Eq myEq;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;
        final float scale = 40;

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public MySurfaceView(Context context) {
            super(context);
            myEq = new binaryEq(null,"=",false);

            paint.setTextSize(30);

            //create a eq to test with
            binaryEq LHS = new binaryEq(null, "+", false);
            ((binaryEq)myEq).add(LHS);
            LHS.add(new Value(null,"A",false));
            LHS.add(new Value(null,"B",false));
            LHS.add(new Value(null,"C",false));
            binaryEq RHS = new binaryEq(null, "/", false);
            RHS.add(new Value(null,"D",false));
            binaryEq RHSB = new binaryEq(null, "*", false);
            RHSB.add(new Value(null,"E",false));
            RHSB.add(new Value(null,"F",false));
            RHS.add(RHSB);
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
                paint.setColor((drawInfo.myEqs.get(i).myEq.select?Color.BLUE:Color.RED));
                drawInfo.myEqs.get(i).draw(canvas,baseX,baseY,scale,paint);
            }
        }

        public boolean onTouch(MotionEvent motionEvent) {
            if (!myEq.touch(motionEvent)){
                // todo this should only happen on touch up if they have not selected anything
                myEq.deSelect();
            }
            return true;
        }
    }
}