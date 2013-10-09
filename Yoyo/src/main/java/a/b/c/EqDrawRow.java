package a.b.c;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Colin_000 on 10/6/13.
 *
 * a nice little package of data to transfer from the Eq for main to draw
 */
public class EqDrawRow {
    float x;
    float y;
    String nam;
    boolean selected;
    Eq myEq;

    public EqDrawRow(float x,float y, String nam, boolean selected,Eq myEq){
        this.nam = nam;
        this.x = x;
        this.y = y;
        this.myEq = myEq;
    }


    public void updateOnScreenInfo(float x, float y, float width, float height){
        myEq.onScreenEq=new OnScreenEq(x,y,width,height);
    }

    public void draw(Canvas canvas, float baseX,float baseY,float scale,Paint paint){
        // draw the character
        canvas.drawText( nam,baseX +  x*scale,baseY + y*scale, paint);
        //record where we drew it
        updateOnScreenInfo((float)(baseX +  (x-0.5)*scale),(float)(baseY +  (y-0.5)*scale),scale,scale);
    }
}
