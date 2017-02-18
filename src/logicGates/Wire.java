package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.LinkedList;

public class Wire implements Printable {
    public InputPin i;
    public OutputPin o;
    
    public Wire( OutputPin o, InputPin i ) {
        this.o = o;
        this.i = i;
        
        this.i.addWire(this);
        this.o.addWire(this);
        
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    public Wire( InputPort o, Gate i, String name) {
        this(o.getOutput(), i.getInput(name));
    }
    
    public Wire( Gate from, String frompin, Gate to, String topin ) {
        this(from.getOutput(frompin),to.getInput(topin));
    }
    
    public Wire( Gate from, Gate to ,String topin ) {
        this(from, "o", to, topin);
    }
    
    public Wire( Gate from, OutputPort port) {
        this(from.getOutput("o"),port.getInput());
    }
    
    
    @Feature(Feature.tables)
    
    static LinkedList<Wire> table;
    
    public static void resetTable() {
        table = new LinkedList<>();
    }
    
    public static LinkedList<Wire> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(wire,[\"from\",\"to\"]).");
    }
    
    public void print() {
        System.out.printf("wire('%s','%s').\n", o, i);
    }
    
    @Feature(Feature.constraints)
    
    public boolean isUsed() {
        return this.o.isUsed() && this.i.isUsed();
    }
    
    @Feature(Feature.eval)
    
    public Value getValue() {
        return this.o.getValue();
    }
}
