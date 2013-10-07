package a.b.c;

import java.util.ArrayList;

/**
 * Created by Colin_000 on 10/6/13.
 */
public class Value extends Eq {


    public Value(Eq sup, String name, boolean select) {
        super(sup,name,select);
    }

    public EqDraw getDrawInfo(){
        //TODO needs to take in to account the number of characters we are trying to draw
        ArrayList<EqDrawRow> eqDrawRows = new ArrayList<EqDrawRow>();
        eqDrawRows.add(new EqDrawRow(0,0,name,select));
        return new EqDraw(0,0,eqDrawRows);
    }

    public  ArrayList<Eq> GetEqSub(){
        ArrayList<Eq> result = new ArrayList<Eq>();
        return result;
    }
}
