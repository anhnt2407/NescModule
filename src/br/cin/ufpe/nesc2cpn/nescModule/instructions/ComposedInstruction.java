package br.cin.ufpe.nesc2cpn.nescModule.instructions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avld
 */
public class ComposedInstruction extends Instruction {
    private List<Instruction> instructions;

    public ComposedInstruction()
    {
        instructions = new ArrayList<Instruction>();
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        for( Instruction instruction : getInstructions() )
        {
            builder.append( instruction.toString() );
            builder.append( ";\n" );
        }

        return builder.toString();
    }
}
