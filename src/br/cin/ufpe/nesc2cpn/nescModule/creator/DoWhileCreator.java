package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.DoWhile;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class DoWhileCreator extends ComposedCreator
{
    public final static String DO_WHILE = "dowhile";

    public DoWhileCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.length() < DO_WHILE.length() )
        {
            return false;
        }
        
        return DO_WHILE.equals( line.substring( 0 , DO_WHILE.length() ) );
    }

    public Instruction convertTo(String line)
    {
        DoWhile doWhile = new DoWhile();

        int first = line.indexOf("{");
        int last = line.lastIndexOf("}");

        String whileCondition = line.substring( last + 1 );
        doWhile.setCondition( getConditionInstruction( whileCondition ) );

        String instructions = line.substring( first , last );
        doWhile.setInstructions( getInstructionChildren( instructions ) );
        doWhile.setProbability( getProbability( line.substring( "do".length() ) ) );

        return doWhile;
    }

    public String getType()
    {
        return "dowhile";
    }

}
