package br.cin.ufpe.nesc2cpn.nescModule;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avld
 */
public class Interface implements Nesc
{
    private String name;
    private List<String> parameter;
    private List<Function> methods;
    private int lineNumber;

    public Interface()
    {
        parameter = new ArrayList<String>();
        methods   = new ArrayList<Function>();
    }

    public List<Function> getMethods() {
        return methods;
    }

    public void setMethods(List<Function> methods) {
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParameter() {
        return parameter;
    }

    public void setParameter(List<String> parameter) {
        this.parameter = parameter;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
}
