package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.*;

public class And extends Gate implements Printable {
    InputPin i1, i2;
    OutputPin o;

    public And(String name) {
        super(name);
        this.i1 = new InputPin("i1", this);
        this.i2 = new InputPin("i2", this);
        this.o = new OutputPin("o", this);
        
        this.inputs.put("i1", this.i1);
        this.inputs.put("i2", this.i2);
        this.outputs.put("o", this.o);
        
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<And> table;
    
    public static void resetTable() {
        table = new LinkedList<>();
    }
    
    public static LinkedList<And> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(and,[name,\"input1\",\"input2\",\"output\"]).");
    }
    
    public void print() {
        System.out.printf("and(%s,'%s','%s','%s').\n", name, i1, i2, o);
    }
        
    @Feature(Feature.eval)    /* for evaluation */
    
    public Value getValue() { 
        Value v1 = i1.getValue();
        Value v2 = i2.getValue();
        if (v1==Value.TRUE && v2==Value.TRUE)
            return Value.TRUE;
        else
            return Value.FALSE;
    }
    
}
