package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.*;

public class OutputPort extends Gate implements Printable {
    InputPin i1;

    public OutputPort(String name) {
        super(name);
        this.i1 = new InputPin("i1", this);
        
        this.inputs.put("i1", this.i1);
        
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    public InputPin getInput() { 
        return this.i1;
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<OutputPort> table;
    
    public static void resetTable() {
        // TO DO
    }
    
    public static LinkedList<OutputPort> getTable() { 
        // TO DO
        return null;
    }
    
    public void printTableHeader() {
        System.out.println("table(outputPort,[name,\"inputPin\"]).");
    }
    
    public void print() {
        // TO DO
    }
    
    @Feature(Feature.eval)
    
    public Value getValue() {
        // TO DO
        return Value.UNKNOWN;
    }
}
