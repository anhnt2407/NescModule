package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Parentese extends Instruction {
    private Instruction instruction;

    public Parentese()
    {
        setType( "parentese" );
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString()
    {
        return "(" + instruction.toString() + ")";
    }
}
