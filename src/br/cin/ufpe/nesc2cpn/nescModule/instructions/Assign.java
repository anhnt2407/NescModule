package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Assign extends Instruction
{
    private String variableType;
    private String variableName;
    private Instruction value;

    public Assign()
    {
        setType("assign");
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        if( variableType != null ) variableType = variableType.trim();
        this.variableType = variableType;
    }

    public Instruction getValue() {
        return value;
    }

    public void setValue(Instruction value) {
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        if( variableName != null ) variableName = variableName.trim();
        this.variableName = variableName;
    }

    public boolean isPointer()
    {
        return this.variableName.indexOf( "*" ) >= 0;
    }

    public boolean isArray()
    {
        return this.variableName.indexOf( "[" ) >= 0;
    }

    @Override
    public String toString()
    {
        String type = value == null ? null : value.getType();
        return variableName + " = ["+ type +"] " + value;
    }
}
