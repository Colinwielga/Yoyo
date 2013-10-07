package a.b.c;

import java.util.ArrayList;

/**
 * Created by Colin_000 on 10/6/13.
 */
public class binaryEq extends Eq{
    ArrayList<Eq> params = new ArrayList<Eq>();

    //create empty equation
    public binaryEq(){
        sup = null;
        name = "=";
        params.add(new Value(this,"_",select));
        params.add(new Value(this,"_",select));
    }

    // main constructor
    public binaryEq(Eq sup, String name,boolean select ,ArrayList<Eq> param){
        super(sup,name,select);
        this.params = params;
    }


    // main constructor
    public binaryEq(Eq sup, String name,boolean select){
        super(sup,name,select);
    }

    public Eq add(Eq under){
        params.add(under);
        under.sup = this;
        return this;
    }

    public EqDraw getDrawInfo(){
        float maxX = 0;
        float maxY = 0;

        EqDraw[] allInfo= new EqDraw[0];
        EqDrawRow[] functionInfo= new EqDrawRow[0];
        if (name.equals("+") || name.equals("*")|| name.equals("-")|| name.equals("=")){
            float atX = 0;
            allInfo = new EqDraw[params.size()];
            functionInfo = new EqDrawRow[params.size()-1];
            for (int i=0;i<params.size();i++){
                allInfo[i] = params.get(i).getDrawInfo();
                for (int j=0;j< allInfo[i].myEqs.size();j++){
                    allInfo[i].myEqs.get(j).x =  allInfo[i].myEqs.get(j).x + atX;
                }
                atX = atX + allInfo[i].maxX;
                if (i <params.size()-1){
                    atX++;
                    functionInfo[i] = new EqDrawRow(atX,0,name,select);
                    atX++;
                }
            }
            maxX = atX;
            for (int i=0;i<allInfo.length;i++){
                if (allInfo[i].maxY > maxY){
                    maxY = allInfo[i].maxY;
                }
            }
            for (int i=0;i<allInfo.length;i++){
                for (int j=0;j< allInfo[i].myEqs.size();j++){
                    allInfo[i].myEqs.get(j).y =allInfo[i].myEqs.get(j).y + (maxY - allInfo[i].maxY)/2;
                }
            }
            for (int i=0;i<functionInfo.length;i++){
                functionInfo[i].y = maxY/2;
            }
        }else if (name.equals("/")){
            float atY = 0;
            allInfo = new EqDraw[params.size()];
            functionInfo = new EqDrawRow[params.size()-1];
            for (int i=0;i<params.size();i++){
                allInfo[i] = params.get(i).getDrawInfo();
                for (int j=0;j< allInfo[i].myEqs.size();j++){
                    allInfo[i].myEqs.get(j).y =  allInfo[i].myEqs.get(j).y + atY;
                }
                atY = atY + allInfo[i].maxY;
                if (i <params.size()-1){
                    atY++;
                    functionInfo[i] = new EqDrawRow(0,atY,name,select);
                    atY++;
                }
            }
            maxY = atY;
            for (int i=0;i<allInfo.length;i++){
                if (allInfo[i].maxX > maxX){
                    maxX = allInfo[i].maxX;
                }
            }
            for (int i=0;i<allInfo.length;i++){
                for (int j=0;j< allInfo[i].myEqs.size();j++){
                    allInfo[i].myEqs.get(j).x =allInfo[i].myEqs.get(j).x + (maxX - allInfo[i].maxX)/2;
                }
            }

            for (int i=0;i<functionInfo.length;i++){
                functionInfo[i].x = (maxX)/2;
            }
        }
        // add everything to a arrayList<EqDrawRow>
        ArrayList<EqDrawRow> eqDrawRows = new ArrayList<EqDrawRow>();
        for (int i=0;i<allInfo.length;i++){
            for (int j=0;j< allInfo[i].myEqs.size();j++){
                eqDrawRows.add( allInfo[i].myEqs.get(j));
            }
            if (i <allInfo.length-1){
                eqDrawRows.add(functionInfo[i]);
            }
        }
        return new EqDraw(maxX, maxY, eqDrawRows);
    }

    public ArrayList<Eq> GetEqSub(){
        return params;
    }
}
