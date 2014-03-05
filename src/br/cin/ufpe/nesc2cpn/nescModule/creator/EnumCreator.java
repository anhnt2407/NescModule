package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Enum;

/**
 *
 * @author avld
 */
public class EnumCreator extends Creator
{
    public final static String ENUM = "enum";

    public EnumCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.length() <= ENUM.length() )
        {
            return false;
        }

        if( ENUM.equals( line.substring( 0 , ENUM.length() ) ) )
        {
            for( int i = ENUM.length(); i < line.length(); i++ )
            {
                char c = line.charAt( i );

                if( c == ' ' )
                {
                    continue ;
                }
                else if( c == '{' )
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Enum enumInst = new Enum();

        int start = line.indexOf("{");
        int end   = line.indexOf("}");

        String atts = line.substring( start + 1 , end );

        for( String att : atts.split(",") )
        {
            att = att.trim();

            if( att == null ? true : att.isEmpty() )
            {
                continue ;
            }

            String[] partes = att.split("=");
            enumInst.getAttributes().put( partes[0] , partes[1] );
        }

        return enumInst;
    }

    public String getType()
    {
        return "enum";
    }

}
