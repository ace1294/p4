package Pins;

import java.util.*;
import logicGates.*;
import GatesApp.*;
import logicGates.Wire;

public class OutputPin {
    Value value;
    public String name;
    Gate outputOf;
    AbstractList<Wire> wiresFrom;
    
    public OutputPin(String name, Gate parent) {
        this.name = name;
        this.outputOf = parent;
        this.wiresFrom = new ArrayList<>();
    }
    
    public void addWire(Wire w) {
        this.wiresFrom.add(w);
    }
    
    public String toString() {
        return outputOf.name+ "->" + name;
    }
    
    public String nameOfGate() {
        return this.name;
    }
    
    @Feature(Feature.constraints)
    
    public boolean isUsed() {        
        return !this.wiresFrom.isEmpty();
    }
    
    @Feature(Feature.eval)
    
    public Value getValue() {
        return this.outputOf.getValue();
    }
}
