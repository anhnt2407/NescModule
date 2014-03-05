package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Case;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class CaseCreator extends ComposedCreator
{
    public static String CASE = "case";
    
    public CaseCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.length() < CASE.length() )
        {
            return false;
        }
        
        return CASE.equals( line.substring( 0 , CASE.length() ) );
    }

    public Instruction convertTo(String line)
    {
        Case caseInst = new Case();

        int divisor = line.indexOf( ":" );

        caseInst.setValue( getCaseValue( line.substring( 0 , divisor ) ) );
        caseInst.setInstructions( getInstructionChildren( line ) );
        caseInst.setProbability( getProbability( line ) );

        return caseInst;
    }

    public String getCaseValue( String text )
    {
        DefaultCreator defaultCreator = new DefaultCreator();
        if( defaultCreator.identify( text ) )
        {
            return null;
        }

        String partes[] = text.split(" ");

        String result = "";

        for( int i = 1; i < partes.length; i++ )
        {
            result += partes[i];

            if( result.indexOf(":") > 0 )
            {
                break;
            }
            else
            {
                result += " ";
            }
        }

        result.replace( ':' , ' ' );

        return result.trim();
    }

    public String getType()
    {
        return "case";
    }
    
}
