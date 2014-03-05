package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.ProjectDate;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Assign;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class AssignCreator extends Creator
{

    public AssignCreator()
    {

    }

    public boolean identify(String line)
    {
        int counter = 0;
        boolean space = false;

        for( int i = 0; i < line.length(); i++ )
        {
            char c = line.charAt( i );

            if( counter > 0 && c != ']' )
            {
                continue;
            }
            else if(c == '[')
            {
                counter++;
            }
            else if( c == ']' )
            {
                counter--;
            }
            else if(c == '-' 
                    && line.charAt( i +1 ) == '>' )
            {
                return true;
            }
            else if( c == '.' )
            {
                continue;
            }
            else if(c == '+' || c == '-')
            {
                return true;
            }
            else if( c == ' ' )
            {
                space = true;
                continue;
            }
            else if( space && c != '='
                    && c != '^'
                    && c != '<'
                    && c != '>'
                    && c != '*'
                    && c != '\\'
                    && c != '+'
                    && c != '-'
                    && c != '|'
                    && c != '%' )
            {
                return false;
            }
            else if( space && ( c == '='
                    || c == '^'
                    || c == '<'
                    || c == '>'
                    || c == '*'
                    || c == '\\'
                    || c == '+'
                    || c == '-'
                    || c == '|'
                    || c == '%' ) )
            {
                return true;
            }
            else if( !Character.isLetterOrDigit( c )
                    && c != ' ' && c != '_' && c != '*' )
            {
                return false;
            }
        }

        return false;
    }

    public Instruction convertTo( String line )
    {
        Assign inst = new Assign();
        inst.setText( line );

        if( line.indexOf("=") != -1 )
        {
            String[] partes = line.trim().split("=");
            inst.setVariableName( partes[0] );
            inst.setValue( CreatorFactory.getInstance().convertToSimple( partes[1] ) );
        }
        else
        {
            inst.setVariableName( line );
            inst.setValue( null );
        }

        String type = ProjectDate.getInstance().getVariableType( inst.getVariableName() );
        inst.setVariableType( type );

        return inst;
    }

    public String getType()
    {
        return "assign";
    }

}
