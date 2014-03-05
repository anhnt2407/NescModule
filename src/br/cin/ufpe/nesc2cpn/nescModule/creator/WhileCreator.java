package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.While;

/**
 *
 * @author avld
 */
public class WhileCreator extends ComposedCreator
{
    public final static String WHILE = "while";

    public WhileCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.length() < WHILE.length() )
        {
            return false;
        }
        
        return WHILE.equals( line.substring( 0 , WHILE.length() ) );
    }

    public Instruction convertTo(String line)
    {
        While whileInst = new While();

        whileInst.setCondition( getConditionInstruction( line ) );
        whileInst.setInstructions( getInstructionChildren( line ) );
        whileInst.setProbability( foundAndGetProbability( line ) );

        return whileInst;
    }

    public String getType()
    {
        return "while";
    }
    
}
