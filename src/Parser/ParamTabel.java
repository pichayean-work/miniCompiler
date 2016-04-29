package Parser;

import java.util.ArrayList;

public class ParamTabel {
    ArrayList<Param> paramTb = new ArrayList<Param>();

    public ParamTabel() {
    }
    
    public void add(Param newParam){
        paramTb.add(newParam);
    }
    
    public Param get(int index){
        return paramTb.get(index);
    }
    
    public int size(){
        return paramTb.size();
    }
    
    public void set(int index, Param newParam){
        paramTb.set(index, newParam);
    }
    
    public void disPlay(){
        for (Param paramTb1 : paramTb) {
            System.out.println(paramTb1.toString());
        }
    }
}
