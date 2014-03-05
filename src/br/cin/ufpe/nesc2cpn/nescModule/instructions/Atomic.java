package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Atomic extends ComposedInstruction {

    public Atomic()
    {
        setType("atomic");
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("atomic {\n");
        builder.append( super.toString() );
        builder.append(" } ");
        
        return builder.toString();
    }
}