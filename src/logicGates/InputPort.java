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
        System.out.println("Psych!");
    }
    
    public OutputPin getOutput() {
        return this.o;
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<InputPort> table;
    
    public static void resetTable() {
        // TO DO
    }
    
    public static LinkedList<InputPort> getTable() { 
        // TO DO
        return null;
    }
    
    public void printTableHeader() {
        System.out.println("table(inputPort,[name,\"outputPin\"]).");
    }
    
    public void print() {
        // TO DO
    }
    
    @Feature(Feature.eval)   /* for evaluation */
            
    Value value = Value.UNKNOWN;
        
    public void setValue(Value v) {
        // TO DO
    }
    
    public Value getValue() {
        // TO DO
        return null;
    }
}
