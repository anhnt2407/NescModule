package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class TypeDef extends Instruction
{
    private Instruction instruction;
    private String name;

    public TypeDef()
    {
        setType( "typedef" );
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "typedef "+ instruction +" " + name;
    }
}
