package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Invoke;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;

/**
 *
 * @author avld
 */
public class InvokeCreator extends Creator
{
    public final static String COMMAND  = "call";
    public final static String EVENT    = "signal";
    public final static String TASK     = "post";
    public final static String FUNCTION = "";
    
    public boolean identify(String line)
    {
        String[] partes = line.trim().split(" ");
        
        if( isInvokeSpecialWord( partes[0] ) )  // partes[0] = call, signal, post
        {
            // do nothing
        }
        else if( isMethodName( line ) )         // line = methodName()
        {
            // do nothing
        }
        else
        {
            return false;
        }

        int finish = finishParentese( line );
        String txtAfter = line.substring( finish + 1 );

        if( txtAfter.isEmpty() )
        {
            return true;
        }
        else if( txtAfter.length() == 1 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isInvokeSpecialWord(String text)
    {
        return COMMAND.equals( text )
            || EVENT.equals( text )
            || TASK.equals( text );
    }

    public static boolean isMethodName(String line)
    {
        line = line.trim();

        if( Character.isDigit( line.charAt(0) ) )
        {
            return false;
        }
        else if( line.indexOf("(") > 0 )
        {
            if( line.indexOf("=") == -1 )
            {
                return true;
            }
            else if( line.indexOf("=") > 0
                    && line.indexOf("=") > line.indexOf("(") )
            {
                return true;
            }
        }
        else if( line.equals( IfElseCreator.IF ) )
        {
            return false;
        }
        else if( line.equals( IfElseCreator.ELSE ) )
        {
            return false;
        }
        else if( line.equals( ForCreator.FOR ) )
        {
            return false;
        }
        else if( line.equals( WhileCreator.WHILE ) )
        {
            return false;
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Invoke invoke = new Invoke();

        invoke.setMethod( new Function() );
        int start = line.indexOf( "(" );
        int end   = finishParentese( line );

        //System.out.println( "\t" + line );

        tratarPartFirst( invoke , line.substring(     0      , start ) );
        tratarPartSecond( invoke , line.substring( start + 1 , end   ) );

        return invoke;
    }

    private void tratarPartFirst(Invoke invoke, String first)
    {
        first = first.trim();

        if( first.indexOf( " " ) < 0 )
        {
            invoke.setInvokeType( FUNCTION );

            invoke.getMethod().setInterfaceName( null );
            invoke.getMethod().setFunctionName( first );

            return ;
        }

        // ----------------

        String[] parts = first.split( " " );

        if( COMMAND.equals( parts[0] ) )
        {
            invoke.getMethod().setFunctionType( Function.COMMAND );
            invoke.setInvokeType( COMMAND );
        }
        else if( EVENT.equals( parts[0] ) )
        {
            invoke.getMethod().setFunctionType( Function.EVENT );
            invoke.setInvokeType( EVENT );
        }
        else if( TASK.equals( parts[0] ) )
        {
            invoke.getMethod().setFunctionType( Function.TASK );
            invoke.setInvokeType( TASK );
        }

        // ----------------

        String[] names = new String[2];

        if( parts[1].indexOf(".") > 0 )
        {
            names[0] = parts[1].substring( 0 , parts[1].indexOf(".") );
            names[1] = parts[1].substring( parts[1].indexOf(".") + 1 );
        }
        else
        {
            names[0] = null;
            names[1] = parts[1];
        }

        // ----------------

        invoke.getMethod().setInterfaceName( names[0] );
        invoke.getMethod().setInterfaceNick( names[0] );
        invoke.getMethod().setFunctionName( names[1] );
    }

    private void tratarPartSecond(Invoke invoke, String params)
    {
        for( String parm : params.split( "," ) )
        {
            parm = parm.trim();
            
            Instruction inst = CreatorFactory.getInstance().convertToSimple( parm );
            invoke.getParam().add( inst );
        }
    }

    public String getType()
    {
        return "invoke";
    }

}
