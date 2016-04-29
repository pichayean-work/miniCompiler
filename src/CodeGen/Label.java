
package CodeGen;

public class Label {
    String Label;
    int Value;

    public Label(String Label, int Value) {
        this.Label = Label;
        this.Value = Value;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String Label) {
        this.Label = Label;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int Value) {
        this.Value = Value;
    }

    @Override
    public String toString() {
        return "Label{" + "Label=" + Label + ", Value=" + Value + '}';
    }
    
    
}
