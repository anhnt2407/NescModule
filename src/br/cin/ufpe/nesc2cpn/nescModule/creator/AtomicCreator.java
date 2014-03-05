package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Atomic;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class AtomicCreator extends ComposedCreator
{
    public final static String ATOMIC   = "atomic";
    
    public AtomicCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.length() <= ATOMIC.length() )
        {
            return false;
        }

        return ATOMIC.equals( line.substring( 0 , ATOMIC.length() ) );
    }

    public Instruction convertTo(String line)
    {
        Atomic atomic = new Atomic();

        String subText = line.substring( ATOMIC.length() + 1 );
        atomic.setInstructions( getInstructionChildren( subText , -1 ) );

        return atomic;
    }

    public String getType()
    {
        return "atomic";
    }

}
