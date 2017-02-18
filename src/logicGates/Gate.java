package logicGates;

import Pins.*;
import Errors.*;
import GatesApp.*;
import java.util.*;


public abstract class Gate {

    public String name;
    HashMap<String, InputPin> inputs;
    HashMap<String, OutputPin> outputs;

    public Gate(String name) {
        this.name = name;
        this.inputs = new HashMap<>();
        this.outputs = new HashMap<>();
    }

    public InputPin getInput(String name) {
        return inputs.get(name);
    }

    public OutputPin getOutput(String name) {
        return outputs.get(name);
    }

    @Feature(Feature.tables)

    public static void resetDB() {
        And.resetTable();
        InputPort.resetTable();
        Not.resetTable();
        Or.resetTable();
        OutputPort.resetTable();
        Wire.resetTable();  
    }

    public static void printDB() {
        printTable(And.getTable());
        printTable(Or.getTable());
        printTable(Not.getTable());
        printTable(Wire.getTable());
        printTable(InputPort.getTable());
        printTable(OutputPort.getTable());
    }

    public static <G extends Printable> void printTable(LinkedList<G> t) {
        System.out.println();
        if (t.isEmpty())
            return;
        t.getFirst().printTableHeader();
        t.forEach((g) -> {
            g.print();
        });
    }

    @Feature(Feature.constraints)

    public boolean extra() {  // subclasses override this method if something special needs to be done
        // TO DO
        return true;
    }

    public boolean allInputsUsed() {
        // Do Not need to implement this
        return true;
    }

    public boolean allOutputsUsed() {
        // Do not need to use this
        return true;
    }

    public static <G extends Gate> boolean verify(String label, LinkedList<G> table) {
        // 1. every gate of type G has a unique name
        table.stream().filter(x->{
            return table.stream().filter(y -> y.name.equals(x.name)).count()>1;
        }).forEach(t-> new ValueAlreadySet(label));
        
        // 2. every gate of type G has all of its inputs used (see above)
        boolean allInputsUsedBool = true;
        LinkedList<Wire> wireTable = Wire.getTable();        
        HashMap<InputPin, Boolean> inputPins = new HashMap<>();
        for (Gate gate: table) {
            inputPins.clear();
            gate.inputs.values().forEach((input) -> {
                inputPins.put(input, false);
            });
            
            wireTable.forEach((wire) -> {
                inputPins.keySet().forEach((input) -> {
                    if (input.equals(wire.i)) {
                        inputPins.replace(input, true);
                    }
                });
            });
            
            for(Boolean inputHasWire: inputPins.values()) {
                if (!inputHasWire) {
                    allInputsUsedBool = false;
                }
            }
        }
        if (!allInputsUsedBool) return false;
        
        // 3. every gate of type G has all of its outputs used (see above)
        boolean allOutputsUsedBool = true;       
        HashMap<OutputPin, Boolean> outputPins = new HashMap<>();
        for (Gate gate: table) {
            outputPins.clear();
            gate.outputs.values().forEach((output) -> {
                outputPins.put(output, false);
            });
            
            wireTable.forEach((wire) -> {
                outputPins.keySet().forEach((output) -> {
                    if (output.equals(wire.o)) {
                        outputPins.replace(output, true);
                    }
                });
            });
            
            for(Boolean outputHasWire: outputPins.values()) {
                if (!outputHasWire) {
                    allOutputsUsedBool = false;
                }
            }
        }
        
        return allOutputsUsedBool;
    }
    
    public static <G extends Gate> boolean verifyInputsConnectToOutputs(LinkedList<G> table) {
        // 5. Every InputPin of every Gate must be connected via a wire to an OutputPin.
        // Remember: every InputPort is a Gate with precisely 1 OutputPin.
        
        LinkedList<Wire> wireTable = Wire.getTable();
        for (Gate g1: table) {
            // If input port continue because it doesn't have any inputs
            if (g1.inputs.values().isEmpty()) continue;
            for (InputPin input: g1.inputs.values()) {
                boolean inputConnectedToOutput = false;
                for (Wire wire: wireTable) {
                    if (input.equals(wire.i)) {
                        for (Gate g2: table) {
                            for (OutputPin output: g2.outputs.values()) {
                                if (output.equals(wire.o)) {
                                    inputConnectedToOutput = true;
                                }
                            }
                        }
                    }
                }
                if (!inputConnectedToOutput) return false;
            }
        }
        
        return true;
    }
    
    public static <G extends Gate> boolean verifyOutputsConnectToInputs(LinkedList<G> table) {
        // Every OutputPin is connected to an InputPin of a Gate.  
        // Remember: every OutputPort is a Gate with precisely one InputPin.
        
        LinkedList<Wire> wireTable = Wire.getTable();
        for (Gate g1: table) {
            // If output port continue because it doesn't have any outputs
            if (g1.outputs.values().isEmpty()) continue;
            for (OutputPin output: g1.outputs.values()) {
                boolean outputConnectedToOutput = false;
                for (Wire wire: wireTable) {
                    if (output.equals(wire.o)) {
                        for (Gate g2: table) {
                            for (InputPin input: g2.inputs.values()) {
                                if (input.equals(wire.i)) {
                                    outputConnectedToOutput = true;
                                }
                            }
                        }
                    }
                }
                if (!outputConnectedToOutput) return false;
            }
        }
        
        return true;
    }

    public static boolean verify() {
        boolean andTableVerified = verify("and",And.getTable());
        boolean orTableVerified = verify("or",Or.getTable());
        boolean notTableVerified = verify("not",Not.getTable());
        boolean inputTableVerified = verify("inputPort",InputPort.getTable());
        boolean outputTableVerified = verify("outputPort",OutputPort.getTable());
        
        LinkedList<Gate> allTables = new LinkedList<>();
        allTables.addAll(And.getTable());
        allTables.addAll(Or.getTable());
        allTables.addAll(Not.getTable());
        allTables.addAll(InputPort.getTable());
        allTables.addAll(OutputPort.getTable());
        
        boolean inputsConnectedToOuputsVerified = verifyInputsConnectToOutputs(allTables);
        boolean outputsConnectedToInputsVerified = verifyOutputsConnectToInputs(allTables);
        
        return (andTableVerified && orTableVerified && notTableVerified && inputTableVerified && outputTableVerified && inputsConnectedToOuputsVerified && outputsConnectedToInputsVerified);
    }

    @Feature(Feature.eval)

    public abstract Value getValue();  // evaluate gate(inputs)
}
