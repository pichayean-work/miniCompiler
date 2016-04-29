package CodeGen;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.util.ArrayList;

public class LabelTB {
    ArrayList<Label> label = new ArrayList<>();
    
    public void add(Label newLabel){
        label.add(newLabel);
    }
    
    public int size(){
        return label.size();
    }
    
    public Label get(int index){
        return label.get(index);
    }
}
