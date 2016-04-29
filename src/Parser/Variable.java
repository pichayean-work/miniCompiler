package Parser;

public class Variable {
    String varDefault;
    String varName;
    String vartype;
    int scope;

    public Variable(String varDefault, String varName, String vartype, int scope) {
        this.varDefault = varDefault;
        this.varName = varName;
        this.vartype = vartype;
        this.scope = scope;
    }

    public String getVarDefault() {
        return varDefault;
    }

    public void setVarDefault(String varDefault) {
        this.varDefault = varDefault;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVartype() {
        return vartype;
    }

    public void setVartype(String vartype) {
        this.vartype = vartype;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return  scope + " : " + varName + " : " + vartype + " : " + varDefault;
    }
    
    
}
