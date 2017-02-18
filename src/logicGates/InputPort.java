package logicGates;

import Errors.NoValueSet;
import Pins.*;
import GatesApp.*;
import java.util.*;

public class InputPort extends Gate implements Printable {
    OutputPin o;

    public InputPort(String name) {
        super(name);
        this.o = new OutputPin("o", this);
        this.outputs.put("o", this.o);
        
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    public OutputPin getOutput() {
        return this.o;
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<InputPort> table;
    
    public static void resetTable() {
        table = new LinkedList<>();
    }
    
    public static LinkedList<InputPort> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(inputPort,[name,\"outputPin\"]).");
    }
    
    public void print() {
        System.out.printf("inputPort(%s,'%s').\n", name, o);
    }
    
    @Feature(Feature.eval)   /* for evaluation */
            
    Value value = Value.UNKNOWN;
        
    public void setValue(Value v) {
        this.value = v;
    }
    
    public Value getValue() {
        return this.value;
    }
}
