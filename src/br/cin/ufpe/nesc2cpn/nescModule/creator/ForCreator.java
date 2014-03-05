package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.For;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class ForCreator extends ComposedCreator
{
    public final static String FOR = "for";

    public ForCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.length() < FOR.length() )
        {
            return false;
        }
        
        return FOR.equals( line.substring( 0 , FOR.length() ) );
    }

    public Instruction convertTo(String line)
    {
        For forInst = new For();

        String condition = getCondition( line );
        
        String partes[] = condition.split( ";" );
        forInst.setPart01( CreatorFactory.getInstance().convertToSimple( partes[0] ) );
        forInst.setPart02( CreatorFactory.getInstance().convertToSimple( partes[1] ) );
        forInst.setPart03( CreatorFactory.getInstance().convertToSimple( partes[2] ) );

        System.out.print( "FOR(" + forInst.getPart01().getType() );
        System.out.print( "; " + forInst.getPart02().getType() );
        System.out.print( "; " + forInst.getPart03().getType() );
        System.out.println( "){}" );

        int number = (int) foundAndGetProbability( line );
        forInst.setInterationNumber( number );

        forInst.setInstructions( getInstructionChildren( line ) );

        return forInst;
    }

    public String getType()
    {
        return "for";
    }
    
}
