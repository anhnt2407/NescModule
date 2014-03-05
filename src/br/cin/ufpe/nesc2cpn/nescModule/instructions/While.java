package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class While extends ComposedInstruction {
    private Instruction condition;
    private double probability = 0.5;

    public While()
    {
        setType("while");
    }

    public Instruction getCondition()
    {
        return condition;
    }

    public void setCondition(Instruction condition)
    {
        this.condition = condition;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("while(");
        builder.append( getCondition() );
        builder.append("){\n");

        builder.append( super.toString() );

        builder.append("}\n");

        return builder.toString();
    }
}
