package a.b.c;

import android.view.MotionEvent;

import java.util.ArrayList;

/**
 *Created by Colin_000 on 10/6/13.
        */
abstract public class Eq {
    public Eq sup = null;
    public String name;
    public boolean select;
    public OnScreenEq onScreenEq;

    public Eq(){}

    public Eq(Eq sup, String name, boolean select) {
        this.sup = sup;
        this.name = name;
        this.select = select;
    }

    public abstract EqDraw getDrawInfo();
    public abstract ArrayList<Eq> GetEqSub();

    // returns true if the motion event was inside this or one of our sub equations, else return false
    public boolean touch(MotionEvent e){
        // if it does not have an onScreenEq we can't have clicked on it
        if (onScreenEq != null){
            // if the click action is inside the Eq
            if (e.getX() >= onScreenEq.x && e.getX() < onScreenEq.x + onScreenEq.width && e.getY() >= onScreenEq.y && e.getY() < onScreenEq.y + onScreenEq.height){
                // todo respond to the click

                // temp for testing
                this.select();
                return true;
            }
        }

        // we now search our sub equations to see if any of them were clicked on
        boolean touchedSomething= false; // are we done searching
        ArrayList<Eq> subEqs = GetEqSub(); // the list we are going to search
        int at = 0; // how much we have searched

        // while we have not found what they clicked on
        while (!touchedSomething && at < subEqs.size()){
            touchedSomething = subEqs.get(at).touch(e);
            at ++;
        }
        return touchedSomething;
    }

    // select this and all its children
    public void select(){
        this.select =true;
        ArrayList<Eq> subEqs = GetEqSub();
        for (int i=0;i<subEqs.size();i++){
            subEqs.get(i).select();
        }
    }

    // deSelect this and all its children
    public void deSelect(){
        this.select =false;
        ArrayList<Eq> subEqs = GetEqSub();
        for (int i=0;i<subEqs.size();i++){
            subEqs.get(i).deSelect();
        }
    }
}
