package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.ProjectDate;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Variable;

/**
 *
 * @author avld
 */
public class VariableCreator extends Creator
{
    private SpecialWordCreator specialWord;
    private AssignCreator assign;
    private StructCreator struct;

    public VariableCreator()
    {
        specialWord = new SpecialWordCreator();
        assign = new AssignCreator();
        struct = new StructCreator();
    }

    public boolean identify(String line)
    {
        String[] partes = line.trim().split(" ");

        if( partes.length <= 1 )
        {
            return false;
        }
        else if( "void".equals( partes[0].trim() )
                 || InvokeCreator.isInvokeSpecialWord( partes[0].trim() )
                 || FunctionCreator.isMethodSpecialWord( partes[0].trim() )
                 || specialWord.identify( partes[0].trim() )
                 || struct.identify( line )
                 || line.startsWith( "#" )
               )
        {
            return false;
        }

        if( line.indexOf("[") > line.indexOf("(") )
        {
            if( line.indexOf("(") == -1 )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if( line.indexOf("[") < line.indexOf("(")
                && line.indexOf("=") < line.indexOf("(") )
        {
            if( line.indexOf("[") == -1 && line.indexOf("=") == -1 )
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        if( partes.length == 2
                && partes[1].indexOf(";") > 0
                && line.indexOf("=") == -1 )
        {
            return true;
        }
        else if( partes.length == 3
                && partes[2].indexOf(";") >= 0
                && line.indexOf("=") == -1)
        {
            return true;
        }
        else if( "norace".equals( partes[0] ) )
        {
            return true;
        }

        if( !assign.identify( line )
                && assign.identify( line.substring( partes[0].length() + 1 ) ) )
        {
            return true;
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Variable variable = new Variable();

        line = line.replace(';', ' ');
        String[] partes = line.trim().split( " " );

        variable.setVariableType( partes[0] );
        variable.setVariableName( partes[1] );

        StringBuilder builder = new StringBuilder();
        int start = 2;

        if( start < partes.length )
        {
            if( partes[ start ] == null ? true : "=".equals( partes[ start ] ) )
            {
                start++;
            }
        }

        for( int i = start; i < partes.length; i++ )
        {
            builder.append( partes[i] );

            if( i + 1 < partes.length )
            {
                builder.append( " " );
            }
        }

        if( !builder.toString().isEmpty() )
        {
            CreatorFactory creator = CreatorFactory.getInstance();
            variable.setValue( creator.convertToSimple( builder.toString() ) );
        }

        ProjectDate.getInstance().putVariableType( variable.getVariableName()
                                                 , variable.getVariableType() );
        return variable;
    }

    public String getType()
    {
        return "variable";
    }







    public static void main(String arg[]) throws Exception
    {
        String value = "int8_t res = sizeof(int8_t) + 2;";

        VariableCreator creator = new VariableCreator();

        System.out.println( "é uma variavel? " + creator.identify( value ) );
        System.out.println( "é uma variavel? " + creator.convertTo( value ) );
    }
}
