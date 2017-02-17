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
        for (G g : t) {
            g.print();
        }
    }

    @Feature(Feature.constraints)

    public boolean extra() {  // subclasses override this method if something special needs to be done
        // TO DO
        return true;
    }

    public boolean allInputsUsed() {
        // TO DO
        return true;
    }

    public boolean allOutputsUsed() {
        // TO DO
        return true;
    }

    public static <G extends Gate> boolean verify(String label, LinkedList<G> table) {
	// TO DO
	// evaluate the following constraints
	// 1. every gate of type G has a unique name
	// 2. every gate of type G has all of its inputs used (see above)
	// 3. every gate of type G has all of its outputs used (see above)
	// 4. any constraint you might think that is particular to
	//    gates of type G, evaluate it see extra() above

        return true;
    }

    public static boolean verify() {
        // TO DO
        return false;
    }

    @Feature(Feature.eval)

    public abstract Value getValue();  // evaluate gate(inputs)
}
