package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public class Module implements Nesc {
    private String name;
    private Map<String,Interface> interfaceUses;
    private Map<String,Interface> interfaceProviders;

    private Map<String,Instruction> variables;
    private Map<String,Instruction> typeDefined;

    private List<Function> functionList;
    
    private boolean safe;

    public Module(){
        interfaceUses = new LinkedHashMap<String, Interface>();
        interfaceProviders = new LinkedHashMap<String, Interface>();

        variables = new LinkedHashMap<String, Instruction>();
        typeDefined = new LinkedHashMap<String, Instruction>();

        functionList = new ArrayList<Function>();
    }

    public Map<String, Interface> getInterfaceProviders() {
        return interfaceProviders;
    }

    public void setInterfaceProviders(Map<String, Interface> interfaceProviders) {
        this.interfaceProviders = interfaceProviders;
    }

    public Map<String, Interface> getInterfaceUses() {
        return interfaceUses;
    }

    public void setInterfaceUses(Map<String, Interface> interfaceUses) {
        this.interfaceUses = interfaceUses;
    }

    public List<Function> getFunctions() {
        return functionList;
    }

    public void setFuctions(List<Function> methods) {
        this.functionList = methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Instruction> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Instruction> variables) {
        this.variables = variables;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public Map<String, Instruction> getTypeDefined() {
        return typeDefined;
    }

    public void setTypeDefined(Map<String, Instruction> typeDefined) {
        this.typeDefined = typeDefined;
    }
    
}
