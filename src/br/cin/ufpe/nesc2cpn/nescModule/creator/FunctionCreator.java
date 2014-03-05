package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.ProjectDate;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import java.util.regex.Pattern;

/**
 *
 * @author avld
 */
public class FunctionCreator extends ComposedCreator
{
    public final static String[] PRIMITIVE_TYPE = new String[]{ "int8_t" , "int16_t"
                                                              , "int32_t" , "int64_t"
                                                              , "bool" , "char"
                                                              , "float" , "double" };
    
    public boolean identify(String line)
    {
        String[] partes = line.trim().split(" ");

        if( isMethodSpecialWord( partes[0].trim() ) )
        {
            return true;
        }
        else if( isPrimitiveType( line , true ) )
        {
            return true;
        }

        return false;
    }

    public static boolean isMethodSpecialWord(String text)
    {
        return "command".equals( text )
            || "event".equals( text )
            || "task".equals( text )
            || "async".equals( text );
    }

    public static boolean isPrimitiveType(String text, boolean useVoid)
    {
        for( String type : PRIMITIVE_TYPE )
        {
            if( type.equals( text ) )
            {
                return true;
            }
        }

        if( useVoid && "void".equals(text) )
        {
            return true;
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Function method = new Function();

//        System.out.println( "//------------------------------------ //" );
//        System.out.println( "//------------------------------------ //" );
//        System.out.println( "//------------------------------------ //" );

//        System.out.println( texto );

        //int block_start = texto.indexOf("(");
        //tratarPrimeiraParte( texto.substring( 0 , block_start ) );

        int first = line.indexOf("(");

        if( first < 0 )
        {
            System.err.println( "\t" + line );
        }

        tratarPrimeiraParte( method, line.substring( 0 , first ) );
        
        int second = line.indexOf(")");

        try
        {
            tratarSegundaParte( method, line.substring( first + 1 , second ) );
        }
        catch(Exception err)
        {
            System.err.println( "value: " + line );
            err.printStackTrace();
        }

        ProjectDate.getInstance().setLastFunction( method.getFunctionName() );

        int terceiro = line.indexOf("{");
        theatBody( method , line.substring( terceiro + 1 ) );

        return method;
    }

    private void tratarPrimeiraParte(Function method, String line)
    {
        // command void Timer.fired()
        // event void Timer.fired()
        // task void fired()
        // void fired()

        int i = 0;
        String[] partes = line.trim().split( " " );

        if( "async".equals( partes[i] ) )
        {
            method.setAsync( true );
            i++;
        }

        if( FunctionCreator.isMethodSpecialWord( partes[i] ) )
        {
            method.setFunctionType( partes[i++] );
        }

        method.setReturnType( partes[i++] );

        if( partes[i].indexOf(".") > 0 )
        {
            String[] name = partes[i++].split( Pattern.quote(".") );

            method.setInterfaceName( name[0] );
            method.setInterfaceNick( name[0] );
            method.setFunctionName( name[1] );
        }
        else
        {
            method.setFunctionName( partes[i++] );
        }
    }

    private void tratarSegundaParte(Function method, String line)
    {
        if( line == null ? true : line.isEmpty() )
        {
            return ;
        }

        String[] partes = line.trim().split(",");

        for(int i = 0; i < partes.length; i++)
        {
            String[] subPartes = partes[i].trim().split(" ");

            String type = subPartes[ 0 ];
            String name = subPartes[ 1 ];

            method.getArguments().put( name , type );
        }
    }

    public String getType()
    {
        return "method";
    }
    
}
