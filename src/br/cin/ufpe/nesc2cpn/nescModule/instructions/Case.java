package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Case extends ComposedInstruction {
    private String value;
    private double probability;

    public Case()
    {
        setType("case");
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
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

        if( getValue() == null ? true : getValue().isEmpty() )
        {
            builder.append("default :\n");
        }
        else
        {
            builder.append("case ");
            builder.append( getValue() );
            builder.append(" :\n");
        }

        builder.append( super.toString() );

        return builder.toString();
    }
}
