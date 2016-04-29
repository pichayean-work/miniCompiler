package Parser;

import java.util.ArrayList;

public class InterCode {
    ArrayList<Atom> inter = new ArrayList<>();

    public InterCode() {
    }
       
    public void add(Atom newAtom){
        inter.add(newAtom);
    }
    
    public int size(){
        return  inter.size();
    }
    
    public Atom get(int index){
        return  inter.get(index);
    }
    
    public void disPlay(){
        for (Atom atom : inter) {
            System.out.println(atom.toString());
        }
    }
}
