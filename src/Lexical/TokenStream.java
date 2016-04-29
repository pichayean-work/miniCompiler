package Lexical;

import java.util.ArrayList;

public class TokenStream {
   ArrayList<Token> token = new ArrayList<Token>();
   
   public void add(Token newToken){
       token.add(newToken);
   }
   
   public Token get(int index){
       return token.get(index);
   }
   
   public int size(){
       return token.size();
   }
   
   public void disPlay(){
       for (Token token1 : token) {
           System.out.println(token1.toString());
       }
   }
}
