package a.b.c;

import java.util.ArrayList;

/**
 * Created by Colin_000 on 10/6/13.
 */
public class EqDraw {
    ArrayList<EqDrawRow> myEqs= new ArrayList<EqDrawRow>();
    float maxX;
    float maxY;

    public EqDraw(float maxX, float maxY){
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public EqDraw(float maxX, float maxY, ArrayList<EqDrawRow> eqDrawRows) {
        this.maxX = maxX;
        this.maxY = maxY;
        myEqs = eqDrawRows;
    }
}
