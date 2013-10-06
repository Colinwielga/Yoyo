package a.b.c;

import java.util.ArrayList;

/**
 *Created by Colin_000 on 10/6/13.
        */
abstract public class Eq {
    public Eq sup = null;
    public String name;
    public boolean select;

    public Eq(){}

    public Eq(Eq sup, String name, boolean select) {
        this.sup = sup;
        this.name = name;
        this.select = select;
    }

    public abstract EqDraw getDrawInfo();
    public abstract ArrayList<Eq> GetEqSub();
}
