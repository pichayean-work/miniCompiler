package Parser;

import java.util.ArrayList;

public class VariableTable {
    ArrayList<Variable> varTb = new ArrayList<Variable>();
    
    public void add(Variable newVar){
        varTb.add(newVar);
    }
    
    public Variable get(int index){
        return varTb.get(index);
    }
    
    public int size(){
        return varTb.size();
    }
    
    public void remove(int index){
        varTb.remove(index);
    }
    
    public void set(int index, Variable newVar){
        varTb.set(index, newVar);
    }
    
    public void disPlay(){
        for (Variable varTb1 : varTb) {
            System.out.println(varTb1.toString());
        }
    }
}
