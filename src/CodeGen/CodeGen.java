package CodeGen;

import Parser.Atom;
import Parser.InterCode;

public class CodeGen {
    InterCode inter = new InterCode();
    Atom inp = new Atom();
    Code code = new Code();
    LabelTB label = new LabelTB();
    
    public CodeGen() {
        
    }    
    
    public CodeGen(InterCode inter){
        this.inter = inter;
    }
    
    public void gen(String Op, String Opr1, String Opr2){
        MachineCode newCode = new MachineCode(Op, Opr1, Opr2);
        code.add(newCode);
    }
    
    public void addLBL(String res, int ip){
        Label newLabel = new Label(inp.getResult(), ip);
        label.add(newLabel);        
    }
    
    public int getaddr(String labelRe){
        for (int i = 0; i < label.size(); i++) {
            if(labelRe.equals(label.get(i).Label)){
                return label.get(i).getValue();
            }
        }
        return 0;
    }
    
    public Code getCode(){
        return code;
    }
   
    public void PassOne(){
        int IP = 0;
        for (int i = 0; i < inter.size(); i++) {
            inp = inter.get(i);
            if(inp.getOpr().equals("LBL")){
                addLBL(String.valueOf(inp.getResult()), IP);
            }else if(inp.getOpr().equals("JMP")||inp.getOpr().equals("ARG")
                    ||inp.getOpr().equals("CALL")){
                IP=IP+1;
            }else if(inp.getOpr().equals("JF")||inp.getOpr().equals("MOV")
                    ||inp.getOpr().equals("JT")){
                IP=IP+2;
            }else{
                IP=IP+3;
            }
        }
    }
    
    public void MultiPassGen(){
        PassOne();
        PassTow();
    }
    
    public void PassTow(){
        int IP=0;
        for (int i = 0; i < inter.size(); i++) {
            inp = inter.get(i);
            switch(inp.getOpr()){
                case "MOV":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "ADD":
                    gen("Load  ", "R1", inp.getOpr1());
                    gen("add   ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "MULT":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("Mult  ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "DIV":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("div   ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "SUB":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("sub   ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "JMP":                    
                    gen("JUMP  ", "R1", String.valueOf(getaddr(inp.getResult())));
                    break;
                case "JT":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("JMPT  ", "R1", String.valueOf(getaddr(inp.getResult())));
                    break;
                case "JF":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("JMPF  ", "R1", String.valueOf(getaddr(inp.getResult())));
                    break;
                case ">":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("cmpgt ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "<":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("cmplt ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "==":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("cmpeg ", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case ">=":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("cmpegt", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;
                case "<=":
                    gen("load  ", "R1", inp.getOpr1());
                    gen("cmpelt", "R1", inp.getOpr2());
                    gen("store ", "R1", inp.getResult());
                    break;                    
                case "ARG":
                    gen("push  ", String.valueOf(inp.getResult()), "");
                    break;
                case "CALL":
                    gen("call  ", inp.getOpr2(), (inp.getResult()));
                    break;
                case "LBL":
                    break;
            }
        }
    }
}
