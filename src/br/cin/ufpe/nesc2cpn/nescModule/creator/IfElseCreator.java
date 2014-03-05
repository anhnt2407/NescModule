package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.ComposedInstruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.IfElse;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class IfElseCreator extends ComposedCreator
{
    public final static String IF       = "if";
    public final static String ELSE_IF  = "else if";
    public final static String ELSE     = "else";

    public final static int RETURN_IF = 0;
    public final static int RETURN_ELSE_IF = 1;
    public final static int RETURN_ELSE = 2;

    @Override
    public boolean identify(String line)
    {
        return isIfElse( line ) >= 0;
    }

    public int isIfElse(String line)
    {
        if( line.length() < IF.length() ) return -1;

        if( IF.equals( line.substring( 0 , IF.length() ) ) )
        {
            return RETURN_IF;
        }

        if( line.length() > ELSE_IF.length() )
        {
            if( ELSE_IF.equals( line.substring( 0 , ELSE_IF.length() ) ) )
            {
                return RETURN_ELSE_IF;
            }
        }

        //if( ELSE.equals( line.substring( 0 , ELSE.length() ) ) )
        if( line.startsWith( ELSE ) )
        {
            return RETURN_ELSE;
        }

        return -1;
    }

    @Override
    public Instruction convertTo(String line)
    {
        return null;
    }

    @Override
    public Instruction addInstruction(ComposedInstruction parent, String line)
    {
        int result = isIfElse( line );

        if( result == RETURN_IF )
        {
            IfElse ifInstruction = new IfElse( line );
            ifInstruction.setCondition( getConditionInstruction( line ) );
            ifInstruction.setProbability( foundAndGetProbability( line ) );
            ifInstruction.setInstructions( getInstructionChildren( line ) );

            parent.getInstructions().add( ifInstruction );
        }
        else
        {
            int lastInstruction = parent.getInstructions().size();

            //System.out.println( parent.getInstructions().get( lastInstruction - 1 ).toString() );
            //System.out.println( parent.toString() );

            IfElse ifInstruction = (IfElse) parent.getInstructions().get( lastInstruction - 1 );

            IfElse elseInstruction = new IfElse( line );
            elseInstruction.setInstructions( getInstructionChildren( line ) );

            if( result == RETURN_ELSE_IF )
            {
                elseInstruction.setCondition( getConditionInstruction( line ) );
                elseInstruction.setProbability( foundAndGetProbability( line ) );
            }

            long elseNumber = ifInstruction.getElses().size();
            ifInstruction.getElses().put( elseNumber , elseInstruction );
        }

        int lastInstruction = parent.getInstructions().size();
        return (IfElse) parent.getInstructions().get( lastInstruction - 1 );
    }

    @Override
    public String getType()
    {
        return "ifEslse";
    }

}
