package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Switch extends ComposedInstruction {
    private String value;
    
    public Switch()
    {
        setType("switch");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("switch( ");
        builder.append( getValue() );
        builder.append(" ){\n");

        builder.append( super.toString() );

        builder.append("}\n");

        return builder.toString();
    }
    
}
