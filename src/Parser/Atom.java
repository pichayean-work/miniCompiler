package Parser;

public class Atom {
    private String Opr;
    private String Opr1;
    private String Opr2;
    private String Result;

    public Atom() {
    }

    public Atom(String Opr, String Opr1, String Opr2, String Result) {
        this.Opr = Opr;
        this.Opr1 = Opr1;
        this.Opr2 = Opr2;
        this.Result = Result;
    }
    
    public String getOpr() {
        return Opr;
    }

    public void setOpr(String Opr) {
        this.Opr = Opr;
    }

    public String getOpr1() {
        return Opr1;
    }

    public void setOpr1(String Opr1) {
        this.Opr1 = Opr1;
    }

    public String getOpr2() {
        return Opr2;
    }

    public void setOpr2(String Opr2) {
        this.Opr2 = Opr2;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    @Override
    public String toString() {
        return Opr + "  " + Opr1 + ", " + Opr2 + ", " + Result;
    }
    
    
}
