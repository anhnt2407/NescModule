package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Struct;

/**
 *
 * @author avld
 */
public class StructCreator extends Creator
{
    public static String STRUCT = "struct";
    public static String NX_STRUCT = "nx_struct";

    public StructCreator()
    {
        
    }

    public boolean identify(String line)
    {
        String[] partes = line.split(" ");
        int pos = 0;

        if( "typedef".equals( partes[ pos ] )  )
        {
            pos++;
        }

        if( "struct".equals( partes[ pos ] ) )
        {
            return true;
        }

        if( "nx_struct".equals( partes[ pos ] ) )
        {
            return true;
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Struct struct = new Struct();

        int start = line.indexOf("{");
        int end   = line.indexOf("}");

        part01( struct , line.substring( 0 , start ) );
        part02( struct , line.substring( start + 1 , end ) );

        return struct;
    }

    private void part01( Struct struct , String text)
    {
        text += " ";

        String partes[] = text.split( " " );
        struct.setIsNxType( partes[0].equals("nx_struct") );

        if( partes.length > 1 )
        {
            if( partes[1] != null )
            {
                struct.setName( partes[1] );
            }
        }
    }

    private void part02( Struct struct , String atts )
    {
        for( String att : atts.split(";") )
        {
            att = att.trim();

            if( att == null ? true : att.isEmpty() )
            {
                continue ;
            }

            String[] partes = att.split(" ");
            struct.getAttributes().put( partes[1] , partes[0] );
        }
    }

    public String getType()
    {
        return "struct";
    }
    
}
