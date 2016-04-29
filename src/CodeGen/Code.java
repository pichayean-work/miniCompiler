
package CodeGen;

import java.util.ArrayList;

public class Code {
    ArrayList<MachineCode> code = new ArrayList<>();
    
    public void add(MachineCode newLabel){
        code.add(newLabel);
    }
    
    public int size(){
        return code.size();
    }
    
    public MachineCode get(int index){
        return code.get(index);
    }
    
    public void disPlay(){
        int i = 0;
        String t = "";
        
        for (MachineCode machineCode : code) {
            if(i<=9){
                t=String.valueOf(i) +" ";
            }else{
                t=String.valueOf(i);
            }
            System.out.println(t+": "+machineCode.toString());
            i++;
        }
        if(i<=9){
                t=String.valueOf(i) +" ";
            }else{
                t=String.valueOf(i);
            }
        System.out.println(t+": ");
    }
}
