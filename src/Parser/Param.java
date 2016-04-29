package Parser;

public class Param {
    String paramName;
    String parmType;
    String typeDefault;
    String fName;
    int count;

    public Param() {
    }
    
    

    public Param(String paramName, String parmType, String typeDefault, String fName, int count) {
        this.paramName = paramName;
        this.parmType = parmType;
        this.typeDefault = typeDefault;
        this.fName = fName;
        this.count = count;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParmType() {
        return parmType;
    }

    public void setParmType(String parmType) {
        this.parmType = parmType;
    }

    public String getTypeDefault() {
        return typeDefault;
    }

    public void setTypeDefault(String typeDefault) {
        this.typeDefault = typeDefault;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return fName + " : " + paramName + " : " + parmType + " : " + typeDefault + " : " + count;
    }
    
    
}
