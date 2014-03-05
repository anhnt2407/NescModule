package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Cast;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class CastCreator extends Creator
{
    
    public CastCreator()
    {
        
    }

    public boolean identify(String line)
    {
        int finish = finishParentese( line );

        if( line.charAt(0) == '('
                && line.charAt( line.length() - 1 ) == ')' )
        {
            if( line.length() - 1 != finish && finish != -1 )
            {
                return true;
            }
        }
        else if( line.charAt(0) == '('
                && finish > 0 )
        {
            String sub = line.substring( 1 , finish );

            OperationProcess process = new OperationProcess( sub );
            if( !process.haveOperation() )
            {
                sub = line.substring( finish + 1 );
                return !nextCharIsOperation( sub );
            }
        }
        
        return false;
    }

    public boolean nextCharIsOperation(String line)
    {
        for( char c : line.trim().toCharArray() )
        {
            if( c == ' ' )
            {
                continue ;
            }
            else if( Character.isLetter( c )
                    || Character.isDigit( c ) )
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Cast cast = new Cast();

        int start = line.indexOf( "(" );
        int end   = finishParentese( line );

        cast.setCastType( line.substring( start + 1 , end ) );

        String rest = line.substring( end + 1 );
        cast.setInst( CreatorFactory.getInstance().convertToSimple( rest ) );

        return cast;
    }

    public String getType()
    {
        return "cast";
    }

}
