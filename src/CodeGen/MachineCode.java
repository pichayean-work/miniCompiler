package CodeGen;

public class MachineCode {
    String Op;
    String Opr1;
    String Opr2;

    public MachineCode(String Op, String Opr1, String Opr2) {
        this.Op = Op;
        this.Opr1 = Opr1;
        this.Opr2 = Opr2;
    }

    public MachineCode() {
    }

    public String getOp() {
        return Op;
    }

    public void setOp(String Op) {
        this.Op = Op;
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

    @Override
    public String toString() {
        return Op + " " + Opr1 + ", " + Opr2;
    }
}
