package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.ComposedInstruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public abstract class Creator
{
    public abstract boolean identify(String line);
    public abstract Instruction convertTo(String line);

    public Instruction addInstruction(ComposedInstruction parent, String line)
    {
        Instruction inst = convertTo( line );
        parent.getInstructions().add( inst );

        return inst;
    }

    protected static int finishParentese(String line)
    {
        int counter = 0;
        boolean value = false;

        for( int i = 0; i < line.length(); i++ )
        {
            char c = line.charAt( i );

            if( c == '"' || c == '\'' )
            {
                value = !value;
            }

            if( c == '(' && !value )
            {
                counter++;
            }

            if( c == ')' && !value )
            {
                counter--;

                if( counter == 0 )
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public abstract String getType();
}
