package Parser;

import Lexical.Token;
import Lexical.TokenStream;
import java.util.ArrayList;

public class Parser {
    private TokenStream tokenstream;
    private InterCode inter;
    public Token current;
    private int index;
    private VariableTable varTb;
    private ParamTabel paramTb;
    int scope;
    int iTemp;
    int iLabel;
    public String END_SOURCE = "$";

    public Parser() {
    }
    
    
    public Parser(TokenStream tokenStream){
        this.tokenstream = tokenStream;
        inter = new InterCode();
        current = new Token();
        index = 0;
        iTemp = 0;
        iLabel = 0;
        varTb = new VariableTable();
        paramTb = new ParamTabel();
    }
    
    public void begin(){
        read();
        S();    
    }
    
    public void read(){        
        if(index >= 0 && index < tokenstream.size()){  
            Token token = tokenstream.get(index++);
            current.setName(token.getName());
            current.setType(token.getType());
        }else{
            current.setName(END_SOURCE);  
            current.setType(END_SOURCE);  
        }
    }
    
    public void addInter(String Opr, String Opr1, String Opr2, String Result){
        Atom newAtom = new Atom(Opr, Opr1, Opr2, Result);
        
        inter.add(newAtom);
    }
    
    public void addVar(String varDefault, String varName, String vartype, int scope){
        Variable newVar = new Variable(varDefault, varName, vartype, scope);
        varTb.add(newVar);
    }
    
    public void addParam(String paramName, String parmType, String typeDefault,String fName, int count){
        Param newParam = new Param(paramName, parmType, typeDefault,fName, count);
        paramTb.add(newParam);
    }
    
    public boolean paramCheck(String paramName, String fName){
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getParamName().equals(paramName) && paramTb.get(i).getfName().equals(fName)){
                return true;
            }
        }
        return false;
    }
    
    public boolean varCheck(String varname, int scope, String fName){
        for (int i = 0; i < varTb.size(); i++) {
            if(varTb.get(i).varName.equals(varname)&&varTb.get(i).scope == scope){
                return true;
            }
        }
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getParamName().equals(varname)&&paramTb.get(i).getfName().equals(fName)){
                return true;
            }
        }
        return false;
    }
    
    public void matchWord(String sym, Token current){
        if (sym.equals(current.getName())) {
            read();
        }else{
            System.out.println("Error Parser");
        }
    }
    
    public InterCode getInter(){
        return inter;
    }
    
    public String newTemp(){
        iTemp++;
        String t = "T" + iTemp;
        return t;
    }
    
    public String newLabel(){
        iLabel++;
        String L = "L" + iLabel;
        return L;
    }
    
    public void matchClass(String sym, Token current){
        if(sym.equals(current.getType())){
           read(); 
        }else{
            System.out.println("Error Parser");
        }
    }
    
    public void S(){
        if(current.getType().equals("IDENTIFIER")
           ||current.getName().equals("fun")){
            scope = 0;
            T();
            F();
        }else if(current.getName().equals(END_SOURCE)){
            
        }else{
            System.out.println("Error in S()");
        }
    }
    
    public void T(){
        Token varType = new Token();
        ArrayList<String> varNameList = new ArrayList<String>();
        ArrayList<String> refVarNameList = new ArrayList<String>();
        if(current.getType().equals("IDENTIFIER")){            
            varNameList.add(current.getName());
            matchClass("IDENTIFIER", current);
            T2(varNameList, refVarNameList);
            matchWord(":", current);
            T3(varType);
            for(int i =0; i<refVarNameList.size();i++){
                if(varCheck(varNameList.get(i), scope, null)){
                    System.out.println(varNameList.get(i)+" "+"var not available");
                }else{
                    addVar(varType.getType(), varNameList.get(i), varType.getType(), scope);
                }
            }
            matchWord(";", current);            
            T();
        }else if(current.getName().equals("fun")
                ||current.getName().equals(END_SOURCE)){
            
        }else{
            System.out.println("Error in T()");
        }
    }
    
    public void T1(String fName, Param count){
        Token varType = new Token();
        String idName = "";        
        int level=0;
        if(current.getType().equals("IDENTIFIER")){
            idName = current.getName();
            matchClass("IDENTIFIER", current);            
            matchWord(":", current);
            T3(varType);       
            level++;
            if(paramCheck(idName, fName)){
                System.out.println(idName + " not ready");
            }else{
                addParam(idName, varType.getType(), varType.getType(), fName, level);
            } 
            T9(fName, count, level);
        }else if(current.getName().equals(")")){
            count.setCount(level);
        }else{
            System.out.println("Error in T()");
        }
    }
    
    public void T9(String fName, Param count, int level){
        Token varType = new Token();
        String idName = "";
        if(current.getName().equals(",")){
            matchWord(",", current);
            idName = current.getName();
            matchClass("IDENTIFIER", current);                
            level++;
            matchWord(":", current);
            T3(varType);
            if(paramCheck(idName, fName)){
                System.out.println(idName + " not ready");
            }else{
                addParam(idName, varType.getType(), varType.getType(), fName, level);
            }
            T9(fName, count, level);
        }else if(current.getName().equals(")")){
            count.setCount(level);
        }else{
            System.out.println("Error in T9()");
        }
    }
    
    public void T2(ArrayList<String> varNameList, ArrayList<String> refVarNameList ){
        if(current.getName().equals(",")){
            matchWord(",", current);
            varNameList.add(current.getName());
            matchClass("IDENTIFIER", current);
//            T4(varNameList, refVarNameList);
            T2(varNameList, refVarNameList);
        }else if(current.getName().equals(":")){
            for (String string : varNameList) {//ให้ค่า List varname เพื่อส่งกลับไปด้านบน
                refVarNameList.add(string);     //varNameList = refVarNameList;
            }
        }else{
            System.out.println("Error in T2()");
        }
    }
    
    public void T3(Token varType){
        if(current.getName().equals("int")){
            varType.setType(current.getName());
            matchWord("int", current);
        }else if(current.getName().equals("float")){
            varType.setType(current.getName());
            matchWord("float", current);
        }else if(current.getName().equals("var")){
            varType.setType(current.getName());
            matchWord("var", current);
        }else if(current.getName().equals("string")){
            varType.setType(current.getName());
            matchWord("string", current);
        }else{
            System.out.println("Error in T3()");
        }
    }
    
    public void F(){
        String fName="";
        Param count = new Param();
        if(current.getName().equals("fun")){
            matchWord("fun", current);
            fName = current.getName();
            matchClass("IDENTIFIER", current);            
            matchWord("(", current);
            if(fNameCheck(fName)){
                System.out.println("function " + fName + " Not ready");                    
            }
            T1(fName,count);        
            if(!fNameCheck(fName)){
                addParam(fName, "fun", "fun", "-", count.getCount());
            }  
//            System.out.println("+++++ParamTable " + paramTb.size() + "++++++");
//            paramTb.disPlay();
            matchWord(")", current);
            matchWord("{", current);
            scope++;
            TL(fName);
            scope--;                      
            
//            System.out.println("+++++varTable "+varTb.size()+"+++++++");
//            varTb.disPlay();
            clearLocal();
            matchWord("}", current);  
            F();            
        }else if(current.getName().equals(END_SOURCE)){
            
        }else{
            System.out.println("Error in F()");
        }
    }

    public void TL(String fName){
        ArrayList<String> varNameList = new ArrayList<String>();
        String idName = "";
        String idType = "";
        String idTypeDefalut = "";
        Token eType = new Token();
        if(current.getType().equals("IDENTIFIER")){
            varNameList.add(current.getName());
            idName = current.getName();
            idType = declareVCheck(current.getName(), fName);
            idTypeDefalut = defaultTypeCheck(current.getName(), fName);
            
            matchClass("IDENTIFIER", current);
            TL1(varNameList, idName, fName, idType, idTypeDefalut);
            
        }else if(current.getName().equals("while")){
            W(fName);
            B(fName);
        }else if(current.getName().equals("}")){
            
        }else{
            System.out.println("Error in TL()");
        }
    }
    
    public void TL1(ArrayList<String> varNameList, String idName, String fName, String idType, String idTypeDefalut){
        Token varType = new Token();
        ArrayList<String> refVarNameList = new ArrayList<String>();
        if(current.getName().equals(",")
           ||current.getName().equals(":")){
            T2(varNameList, refVarNameList);
            matchWord(":", current);
            T3(varType);
            for(int i =0; i<refVarNameList.size();i++){
                if(varCheck(refVarNameList.get(i), scope, fName)){
                    System.out.println(refVarNameList.get(i)+" "+"var Not available");
                }else{
                    addVar(varType.getType(), refVarNameList.get(i), varType.getType(), scope);
                }
            }
            matchWord(";", current);
            TL(fName);            
        }else if(current.getName().equals("(")
           ||current.getName().equals("=")){
            E(idName, fName, idType, idTypeDefalut);
            matchWord(";", current);
            B(fName);
        }else{
            System.out.println("Error in TL1()");
        }
    }
    
    public void B(String fName){    
        Token eType = new Token();
        String idTypeDefalut = "";
        String idType = "";
        String idName = "";
        if(current.getType().equals("IDENTIFIER")){
            idName = current.getName();
            idType = declareVCheck(current.getName(), fName);
            idTypeDefalut = defaultTypeCheck(current.getName(), fName);
            matchClass("IDENTIFIER", current);
            E(idName, fName, idType, idTypeDefalut);
            matchWord(";", current);
            B(fName);
        }else if(current.getName().equals("while")){
            W(fName);
            B(fName);
        }else if(current.getName().equals("}")){
            
        }else{
            System.out.println("Error in B()");
        }
    }
    
    public void P(Param count, String fName, String idName){
        Token e2Type = new Token();
        Token eTemp = new Token();
        int level;
        if(current.getType().equals("IDENTIFIER")
            ||current.getType().equals("int_const")
            ||current.getType().equals("float_const")
            ||current.getType().equals("string_const")){
            E2(fName, e2Type, eTemp);
            if(e2Type.getType().equals("var")){
                    System.out.println("Error var type not assing");
                }
            level = 1;
            if(argumentCheck(idName, e2Type.getType(), level)){                
                    setNewType(idName, e2Type, fName);   
            }else{
                System.out.println("Argument Type miss match");
            }            
            addInter("ARG", "-", "-", eTemp.getName());
            P2(count, fName, idName, level);       
            //System.out.println("CALL " + " - " + pCount.getCount() + " " + idName);
            
            addInter("CALL", "-", String.valueOf(count.getCount()), idName);
        }else if(current.getName().equals(")")){
            addInter("CALL", "-", "0", idName);
            //System.out.println("CALL " + " - " + "0"+ " " + idName);
        }else{
            System.out.println("Error in P()");
        }
    }
    
    public void P2(Param count, String fName, String idName, int level){
        Token e2Type = new Token();
        Token eTemp = new Token();
        if(current.getName().equals(",")){
            matchWord(",", current);
            E2(fName, e2Type, eTemp);
            level++;
            if(argumentCheck(idName, e2Type.getType(), level)){
                setNewType(idName, e2Type, fName);
            }else{
                System.out.println("Argument Type miss match");
            }   
            //System.out.println("ARG " + " - " + " - " +  eTemp.getName());  
            addInter("ARG", "-", "-", eTemp.getName());
            P2(count, fName, idName, level);
        }else if(current.getName().equals(")")){
            count.setCount(level);
        }else{
            System.out.println("Error in P2()");
        }
    }
    
    public void E(String idName, String fName, String idType, String idTypeDefalut){
        Token e2Type = new Token();
        Token eTemp = new Token();
        Param count = new Param();
        if(current.getName().equals("(")){
            if(!fNameCheck(idName)){
                System.out.println("ยังไม่ประกาศ function นี้  : " + idName );
            }
            matchWord("(", current);
            P(count, fName, idName);
            if(!fAgumentCountCheck(idName, count)){
                System.out.println("Error argument Invalid");
            }
            matchWord(")", current);
        }else if(current.getName().equals("=")){
            if(declareVCheck(idName, fName) == "NO"){
                System.out.println("Variable " + idName + " has not been declare");
            }
            matchWord("=", current);
            E2(fName, e2Type, eTemp);
            if(idType.equals(e2Type.getType()) || idTypeDefalut.equals("var")){
                if(e2Type.getType().equals("var")){
                        System.out.println("ยังไม่มีการ ให้ค่ากับตัวแปร var");
                    }else{
                        setNewType(idName, e2Type, fName);
                    }                    
            }else{
                System.out.println("Type miss match");
            }
            //System.out.println("MOV "+eTemp.getName()+" - "+idName);
            addInter("MOV", eTemp.getName(), "-", idName);
        }else{
            System.out.println("Error in E()");
        }
    }
    
    public void E2(String fName, Token e2Type, Token eTemp){
        Token iType = new Token();        
        Token iName = new Token();
        if(current.getType().equals("IDENTIFIER")
            ||current.getType().equals("int_const")
            ||current.getType().equals("float_const")
            ||current.getType().equals("string_const")){
            I(fName, iType, iName);
            e2Type.setType(iType.getType());
            E3(fName, iType.getType(), iName.getName(), eTemp);
        }else{
            System.out.println("Error in E()");
        }
    }
    
    public void E3(String fName, String e2Type, String e2Name, Token eTemp){
        Token iType = new Token();
        Token iName = new Token();
        Token oName = new Token();
        String temp = "";
        if(current.getName().equals("+")
            ||current.getName().equals("-")
            ||current.getName().equals("*")
            ||current.getName().equals("/")){
            O(oName);
            I(fName, iType, iName);
            if(!e2Type.equals(iType.getType())){
                System.out.println("Type miss match");
            }
            temp = newTemp();
            //System.out.println(oName.getName() + " " + e2Name +" "+ iName.getName() +" "+ temp);
            addInter(oName.getName(), e2Name, iName.getName(), temp);
            E3(fName, iType.getType(), temp, eTemp);
        }else if(current.getName().equals(",")
            ||current.getName().equals(")")
            ||current.getName().equals(";")
            ||current.getName().equals(">")
            ||current.getName().equals(">=")
            ||current.getName().equals("<")
            ||current.getName().equals("<=")
            ||current.getName().equals("==")){
            eTemp.setName(e2Name);
        }else{
            System.out.println("Error in E3()");
        }
    }
    
    public void I(String fName, Token iType, Token iName){
        if(current.getType().equals("IDENTIFIER")){            
            if(declareVCheck(current.getName(), fName) == "NO"){
                System.out.println("Variable " + current.getName() + " has not been declare");
            }
            iName.setName(current.getName());
            iType.setType(declareVCheck(current.getName(), fName));
            matchClass("IDENTIFIER", current);
        }else if(current.getType().equals("int_const")){
            iName.setName(current.getName());
            iType.setType("int");
            matchClass("int_const", current);
        }else if(current.getType().equals("float_const")){
            iName.setName(current.getName());
            iType.setType("float");
            matchClass("float_const", current);
        }else if(current.getType().equals("string_const")){
            iName.setName(current.getName());
            iType.setType("string");
            matchClass("string_const", current);
        }else{
            System.out.println("Error in I()");
        }
    }
    
    public void O(Token oName){
        if(current.getName().equals("/")){
            oName.setName("DIV");
            matchWord("/", current);
        }else if(current.getName().equals("+")){
            oName.setName("ADD");
            matchWord("+", current);
        }else if(current.getName().equals("-")){
            oName.setName("SUB");
            matchWord("-", current);
        }else if(current.getName().equals("*")){
            oName.setName("MULT");
            matchWord("*", current);
        }else{
            System.out.println("Error in O()");
        }
    }
    
    public void W(String fName){
        Token e2Type = new Token();
        Token eTemp = new Token();
        Token e4Temp = new Token();
        Param count = new Param();
        String L1,L2,L3, temp;
        int level = 0;
        if(current.getName().equals("while")){
            matchWord("while", current);
            L1 = newLabel();
            //System.out.println("LBL" + " - " + "- " + L1);
            addInter("LBL", "-", "-", L1);
            matchWord("(", current);
            E2(fName, e2Type, eTemp);
            if(e2Type.getType().equals("var")){
                    System.out.println("Error var type not assing");
            }
            E4(fName, e2Type, eTemp.getName(), e4Temp, count, level);
            matchWord(")", current);
            L2 = newLabel();
            //System.out.println("JMF " + e4Temp.getName() + " - " + L2);
            
            if(count.getCount()==0){
                temp = newTemp();
                addInter(">", e4Temp.getName(), "0", temp);
                addInter("JT", temp, "-", L2);
                L3 = L2;                
                L2=newLabel();
                addInter("JMP", "-", "-", L2);
                addInter("LBL", "-", "-", L3);
            }else{
                addInter("JF", e4Temp.getName(), "-", L2);
            }
            matchWord("{", current);
            B(fName);
            matchWord("}", current);            
//            System.out.println("JMP" + " - " + "- " + L1);
//            System.out.println("LBL" + " - " + "- " + L2);            
            addInter("JMP", "-", "-", L1);
            addInter("LBL", "-", "-", L2);
        }else{
            System.out.println("Error in W()");
        }
    }
    
    public void E4(String fName, Token e4Type, String e2Temp, Token e4Temp, Param count, int level){
        Token e2Type = new Token();
        Token o2Name = new Token();
        Token eTemp = new Token();
        String temp = "";
        if(current.getName().equals(">")
            ||current.getName().equals(">=")
            ||current.getName().equals("<")
            ||current.getName().equals("<=")
            ||current.getName().equals("==")){
            O2(o2Name);
            E2(fName, e2Type, eTemp);
//            if(!e4Type.getType().equals(e2Type.getType())){
//                System.out.println("Type miss match");
//            }
            if(e4Type.getType().equals(e2Type.getType())){
                if(e2Type.getType().equals("var")){
                    System.out.println("Error var type not assing");
                }
            }else{
                System.out.println("Type miss match");
            }
            temp = newTemp();
            //System.out.println(o2Name.getName() + " " + e2Temp + " " + eTemp.getName() + " " + temp);
            level++;
            addInter(o2Name.getName(), e2Temp, eTemp.getName(), temp);
            E4(fName, e2Type, temp, e4Temp, count, level);
        }else if(current.getName().equals(")")){
            e4Temp.setName(e2Temp);
            count.setCount(level);
        }else{
            System.out.println("Error in E4()");
        }
    }
    
    public void O2(Token o2Name){
        if(current.getName().equals(">")){
            o2Name.setName(current.getName());
            matchWord(">", current);
        }else if(current.getName().equals("<=")){
            o2Name.setName(current.getName());
            matchWord("<=", current);
        }else if(current.getName().equals("<")){
            o2Name.setName(current.getName());
            matchWord("<", current);
        }else if(current.getName().equals(">=")){
            o2Name.setName(current.getName());
            matchWord(">=", current);
        }else if(current.getName().equals("==")){
            o2Name.setName(current.getName());
            matchWord("==", current);
        }else{
            System.out.println("Error in E3()");
        }
    }

    private void clearLocal() {
        int first = 0; 
        while(first<varTb.size()){
            if(varTb.get(first).getScope()==1){
             break;
            }
            first++;
        }
        int i = first;        
        int temp = varTb.size();
        while(i < temp){
            varTb.remove(i);
            
            temp--;
        }
    }
    
    public boolean fNameCheck(String fName){
            for (int i = 0; i < paramTb.size(); i++) {
                if(fName.equals(paramTb.get(i).getParamName())
                   &&paramTb.get(i).getParmType().equals("fun")){
                    return true;
                }
            }
            return false;
    }

    public boolean fAgumentCountCheck(String fName, Param pCount) {
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getParamName().equals(fName)
               &&paramTb.get(i).getCount()== pCount.getCount()){
                return true;
            }
        }
        return false;
    }
    
    public String declareVCheck(String vName, String fName){
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getParamName().equals(vName)&&paramTb.get(i).getfName().equals(fName)){
                return paramTb.get(i).getParmType();
            }            
        }
        for(int i =0; i < varTb.size(); i++){
            if(varTb.get(i).getVarName().equals(vName)&& varTb.get(i).getScope() == 1){
                return varTb.get(i).getVartype();
            }
        }
        for(int i =0; i < varTb.size(); i++){
            if(varTb.get(i).getVarName().equals(vName)&& varTb.get(i).getScope() == 0){
                return varTb.get(i).getVartype();
            }
        }
        return "NO";
    }
    
    public String defaultTypeCheck(String vName, String fName){
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getParamName().equals(vName)&&paramTb.get(i).getfName().equals(fName)){
                return paramTb.get(i).getTypeDefault();
            }            
        }
        for(int i =0; i < varTb.size(); i++){
            if(varTb.get(i).getVarName().equals(vName)&& varTb.get(i).getScope() == 1){
                return varTb.get(i).getVarDefault();
            }
        }
        for(int i =0; i < varTb.size(); i++){
            if(varTb.get(i).getVarName().equals(vName)&& varTb.get(i).getScope() == 0){
                return varTb.get(i).getVarDefault();
            }
        }
        return "NO";
    }

    public boolean argumentCheck(String fName, String arguType, int arguLevel){
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getfName().equals(fName)
                &&paramTb.get(i).getCount() == arguLevel
                &&(paramTb.get(i).getParmType().equals(arguType)||paramTb.get(i).getTypeDefault().equals("var"))){
                return true;
            }
        }
        return false;
    }

    private void setNewType(String idName, Token e2Type, String fName) {
        for (int i = 0; i < paramTb.size(); i++) {
            if(paramTb.get(i).getfName().equals(fName)
               &&paramTb.get(i).getParamName().equals(idName)){
                Param newParam = new Param(idName, e2Type.getType(), paramTb.get(i).getTypeDefault(), fName, paramTb.get(i).getCount());
                paramTb.set(i, newParam);
                break;
            }
        }
        for (int i = 0; i < varTb.size(); i++) {
            if(varTb.get(i).getVarName().equals(idName)&&varTb.get(i).getScope() == 1){
                Variable newVar = new Variable(varTb.get(i).getVarDefault(), idName, e2Type.getType(), 1);
                varTb.set(i, newVar);
                break;
            }
        }
        for (int i = 0; i < varTb.size(); i++) {
            if(varTb.get(i).getVarName().equals(idName)&&varTb.get(i).getScope() == 0){
                Variable newVar = new Variable(varTb.get(i).getVarDefault(), idName, e2Type.getType(), 0);
                varTb.set(i, newVar);
                break;
            }
        }
        
    }
}
