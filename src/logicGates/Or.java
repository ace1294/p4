package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.LinkedList;

public class Or extends Gate implements Printable {
    InputPin i1, i2;
    OutputPin o;

    public Or(String name) {
        super(name);
        this.i1 = new InputPin("i1",this);
        this.i2 = new InputPin("i2", this);
        this.inputs.put("i1",this.i1);
        this.inputs.put("i2",this.i2);
        this.o = new OutputPin("o",this);
        this.outputs.put("o", this.o);
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<Or> table;
    
    public static void resetTable() {
        table = new LinkedList<>();
    }
    
    public static LinkedList<Or> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(or,[name,\"input1\",\"input2\",\"output\"]).");
    }
    
    public void print() {
        System.out.printf("or(%s,'%s','%s','%s').\n", name, i1, i2, o);
    }
    
    @Feature(Feature.eval)
            
    public Value getValue() { 
        Value v1 = i1.getValue();
        Value v2 = i2.getValue();
        if (v1==Value.TRUE || v2==Value.TRUE)
            return Value.TRUE;
        else
            return Value.FALSE;
    }
}
