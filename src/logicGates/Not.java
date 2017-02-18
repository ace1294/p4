package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.LinkedList;

public class Not extends Gate implements Printable {
    InputPin i1;
    OutputPin o;

    public Not(String name) {
        super(name);
        this.i1 = new InputPin("i1", this);
        this.o = new OutputPin("o", this);
        
        this.inputs.put("i1", this.i1);
        this.outputs.put("o", this.o);
        
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<Not> table;
    
    public static void resetTable() {
        table = new LinkedList<>();
    }
    
    public static LinkedList<Not> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(not,[name,\"input\",\"output\"]).");
    }
    
    public void print() {
        System.out.printf("not(%s,'%s','%s').\n", name, i1, o);
    }
    
    @Feature(Feature.eval)   /* for logic diagram evaluation */
    
    public Value getValue() {
        if (this.i1.getValue() == Value.TRUE) {
            return Value.FALSE;
        }
        else {
            return Value.TRUE;
        }
    }
    
}
