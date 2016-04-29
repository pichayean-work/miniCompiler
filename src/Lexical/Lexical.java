package Lexical;


import Lexical.Token;
import Lexical.TokenStream;
import java.util.ArrayList;

public class Lexical {
    int index = 0;
    char END_SOURCE = '$';
    private char[] source;
    private TokenStream tokenStream;
    private boolean checkComment = true;
    public Lexical(String source) {
        tokenStream = new TokenStream();
        this.source = new char[source.length()];
        for(int i = 0; i < source.length(); i++){
            this.source[i] = source.charAt(i);
        }
    }
    
    public void addToken(String name, String type){
        Token newToken = new Token(name, type);
        tokenStream.add(newToken);
    }
        
    public TokenStream getTokenStream() {
        return tokenStream;
    }
     
    private char read(){
        if(index < source.length){
            return source[index++];
        }else{
            return END_SOURCE;
        }
    }
    
    private void unRead(int i){
        index-=i;
    }
    
    public void Start(){
        char ch = read();
        String word = "";        
        
        while(ch != END_SOURCE){
            switch( ch ){
                case ' ' : ch = read(); continue;
                case '\n': ch = read(); continue;
                case '#' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case '%' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;                
                case '^' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;                
                case '&' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;                
                case '|' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case '!' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case ']' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case '@' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case '[' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case '?' : word += String.valueOf(ch); addToken(word, "SEPARATOR"); word = ""; ch = read(); continue;
                case '\t': ch = read(); continue;
                case '\r': ch = read(); continue;
                case '+' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case ':' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case '.' : 
                            word += String.valueOf(ch);   
                            ch = read();
                            if(isDigit(ch)){
                                while(isDigit(ch)){
                                    word += String.valueOf(ch);   
                                    ch = read();                                    
                                }
                                addToken(word, "float_const");
                            }else{
                                addToken(word, "OPERATOR");
                            }
                            word = "";
                            break;
                case '-' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case '*' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case '(' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case ')' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;                
                case '{' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case '}' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case ';' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;
                case ',' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;                      
                case '/' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;              
                case '\'' : word += String.valueOf(ch); addToken(word, "OPERATOR"); word = ""; ch = read(); continue;              
                case '"' : ch = read();
                           while(ch != '"'){
                               word += String.valueOf(ch);
                               ch = read();  
                               if(ch == END_SOURCE){      
                                       System.out.println("String Error");
                                       word = "";
                                       break;
                                }
                           }
                           if(ch == END_SOURCE){      
                                       word = "";
                                       break;
                           }
                           addToken(word, "string_const");
                           ch = read();
                           word = "";
                           break;
                case '>' : word += String.valueOf(ch);
                           ch = read();
                           if(ch == '='){
                               word += String.valueOf(ch);
                               addToken(word, "OPERATOR");
                               ch = read();
                           }else{
                               addToken(word, "OPERATOR");
                           }
                           word = "";
                           break;
                  
//                case '<' : word += String.valueOf(ch);//เก็บ <
//                           String lastWord ;
//                           String tempfirstWord="";                           
//                           lastWord="";
//                           StringBuffer firstWord = new StringBuffer();
//                           firstWord.append(ch);//เก็บ ค่า < สำหรับเช็คจบ
//                           ch = read();
//                           if(ch==' '){ 
//                               firstWord.append(ch);//เก็บ ค่า ว่าง สำหรับเช็คจบ
//                               ch=read();
//                               while(ch != ' '){
//                               firstWord.append(ch);//เก็บ คำ สำหรับเช็คจบ
//                               ch=read();
//                               }
//                               firstWord.append(ch);// เก็บค่าว่าง ก่อนเข้าคอมเเมนต์
//                               
//                               firstWord.reverse();//สลับคำเพื่อเช็คออก
//                               firstWord.setCharAt(firstWord.length()-1, '>');// แปลง < เป็น >
//                               ch = read();
//                               
//                               String cword=""; //เคลียค่า เตรียมเก็บคอมแมนต์
//                               
//                               tempfirstWord = String.valueOf(firstWord);
//                               
//                               while(!tempfirstWord.equals(lastWord)){ //ขณะที่ไม่ใช่ตัวออกให้เก็บ คำต่อไป
//                                   cword += String.valueOf(ch);//เก็บคอมแมนต์
//                                   
//                                   lastWord = "";//เตรีบมตัวเก็บตัวออก
//                                   for (int i = 0; i < firstWord.length(); i++) {
//                                       if(tempfirstWord.charAt(i) == ch){
//                                           lastWord += String.valueOf(ch);
//                                           ch=read();
//                                       }else{
//                                           unRead(i);
//                                           break;
//                                       }                                       
//                                   }
//                                   if(!tempfirstWord.equals(lastWord)){
//                                       ch=read();
//                                   }
//                                   if(index == source.length-1){//ถ้าตัวออกไม่ถูกต้องให้หยุดและ แจ้ง Error  
//                                       System.out.println("Comment Error");
//                                       break;
//                                   }
//                               }                               
//                               if(tempfirstWord.equals(lastWord)){
////                               addToken(cword, "COMMENT"); //ถ้า ตัวเช็คออกถูกต้อง ให้เก็บคำไว้เป็น คอมแมนต์
//                                   System.out.println(cword + " : " + "COMMENT");
//                               word="";
//                               break;
//                               }                                                          
//                           
//                           
//                           }else if(ch == '='){
//                               word+=String.valueOf(ch);
//                               addToken(word, "OPERATOR"); 
//                               ch = read();
//                               word="";
//                           break;
//                           }
//                           addToken(word, "OPERATOR");                             
//                           word="";
//                           break;
                case '<' : StringBuffer fristWord = new StringBuffer();
                           StringBuffer lastWord = new StringBuffer();
                           fristWord.append(ch);
                           int tempIndex = index;
                           
                           word += String.valueOf(ch);
                           ch = read();
                           if(ch == '='){
                               word += String.valueOf(ch);
                               addToken(word, "OPERATOR");
                               word = "";
                               ch = read();
                               break;
                           }else if(ch == ' ' && checkComment){
                               fristWord.append(ch);
                               word += String.valueOf(ch);
                               ch = read();
                               if(isDigit(ch)||isLetter(ch)){
                                   fristWord.append(ch);
                                   word += String.valueOf(ch);
                                   ch = read();
                                      while(ch != ' ' && ch != END_SOURCE){
                                       fristWord.append(ch);
                                       word  += String.valueOf(ch);                                   
                                       ch = read();
                                      }
                                      
                                      if(ch == ' '){
                                           fristWord.append(ch);
                                           fristWord.reverse();
                                           fristWord.setCharAt(fristWord.length()-1, '>');
                                           word  += String.valueOf(ch);                                   
                                           ch = read();                                       
                                           while(!fristWord.toString().equals(lastWord.toString()) && ch != END_SOURCE){
                                               lastWord = new StringBuffer();
                                               word  += String.valueOf(ch);
                                               for (int i = 0; i < fristWord.length(); i++) {
                                                   if(ch == fristWord.charAt(i)){
                                                       lastWord.append(ch);
                                                       ch = read();
                                                   }else{
                                                       index = index-i;
                                                       break;
                                                   }
                                               }
                                               if(!fristWord.toString().equals(lastWord.toString())){
                                                   ch = read();
                                               }                     
                                           }
                                           if(fristWord.toString().equals(lastWord.toString())){
                                               lastWord.deleteCharAt(0);
                                               word += String.valueOf(lastWord);
//                                               addToken(word, "COMMENT");
                                               System.out.println(word + " : " + "COMMENT");
                                               word = "";
                                               break;
                                           }else{
//                                               System.out.println(word);
//                                               word = "";
//                                               System.out.println("Comment error");
//                                               break;
                                           }
                                      }
                               }else{
                                   addToken(word, "OPERATOR");
                                   word = "";
                                   break;
                               }
                               
                           }else{
                               checkComment = true;
                               addToken(word, "OPERATOR");
                               word = "";
                               break;
                           }
                           if(ch == END_SOURCE){      
                               checkComment = false;
                               index = tempIndex-1;
                               ch= read();      
                           }
                           word = "";
                           break;
                    
                case '=' : word += String.valueOf(ch);
                           ch = read();
                           if(ch == '='){
                               word += String.valueOf(ch);
                               addToken(word, "OPERATOR");
                               ch = read();
                           }else{
                               addToken(word, "OPERATOR");
                           }
                           word = "";
                           break;
                default:
                    if(isDigit(ch)){
                            word += String.valueOf(ch);
                            ch = read();
                            while(isDigit(ch)){
                                word += String.valueOf(ch);
                                ch = read();
                            }
                            if(ch == '.'){
                                word += String.valueOf(ch);
                                ch = read();
                                while(isDigit(ch)){
                                    word += String.valueOf(ch);
                                    ch = read();
                                }
                                addToken(word, "float_const");
                                word = "";
                                break;
                            }else{
                                addToken(word, "int_const");
                                word = "";
                                break;
                            }
                    }else if(isLetter(ch)){
                        while(isLetter(ch)||isDigit(ch)){
                            word += String.valueOf(ch);
                            ch = read();
                        }
                        if(isResvWord(word)){
                            addToken(word, "RESERVED WORD");
                        }else{
                            addToken(word, "IDENTIFIER");
                        }
                        word = "";
                        break;
                    }
            }
        } 
       
    }
    
    private boolean isDigit(char ch){
        return ch >= '0' && ch <= '9';
    }
    
    
    private boolean isLetter(char ch){
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }
    
    private boolean isResvWord(String checkRs){
        return "while".equals(checkRs)||"do".equals(checkRs)
                ||"if".equals(checkRs)||"else".equals(checkRs)
                ||"int".equals(checkRs)||"float".equals(checkRs)
                ||"string".equals(checkRs)||"var".equals(checkRs)
                ||"char".equals(checkRs)||"main".equals(checkRs)
                ||"void".equals(checkRs)||"return".equals(checkRs)
                ||"fun".equals(checkRs);                            
    }
}
