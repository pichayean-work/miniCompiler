package Program;

import CodeGen.CodeGen;
import Lexical.Lexical;
import Parser.Parser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Program {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader("C:\\Documents and Settings\\Administrator\\My Documents\\NetBeansProjects\\5005501068\\src\\Program\\source.txt");
       
        int ch = reader.read();
        String source = "";
        while(ch != -1){
            source += String.valueOf((char)ch);
            ch = reader.read();
        }
        Lexical myLexical = new Lexical(source);
        myLexical.Start();
        System.out.println("++++++Lexical++++++");      
        myLexical.getTokenStream().disPlay();
        
        System.out.println("++++++Parser++++++");        
        Parser myParser = new Parser(myLexical.getTokenStream());
        myParser.begin();
        
//        myParser.getInter().disPlay();
        
        System.out.println("++++++CodeGen++++++");     
        CodeGen myCode = new CodeGen(myParser.getInter());
        myCode.MultiPassGen();
       
        myCode.getCode().disPlay();
        
    }   
}
