package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.ProjectDate;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Value;

/**
 *
 * @author avld
 */
public class ValueCreator extends Creator
{

    public ValueCreator()
    {
        
    }

    public boolean identify(String line)
    {
        if( line.charAt(0) == '"' || line.charAt(0) == '\'' )   //"nome"
        {
            return true;
        }
        else if( Character.isDigit( line.charAt(0) ) )          //nome
        {
            return true;
        }
        else if( Character.isLetter( line.charAt(0) ) )         //12345
        {
            return true;
        }
        else if( line.charAt(0) == '-'
                && Character.isDigit( line.charAt(1) ) )        //-12345
        {
            return true;
        }
        else if( line.charAt(0) == '&' )                        //&packet
        {
            return true;
        }
        else if( line.charAt(0) == '*' )                        //*value
        {
            return true;
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        Value value = new Value();
        value.setValue( line );

        if( value.isVariable() )
        {
            String type = ProjectDate.getInstance().getVariableType( line );
            value.setValueType( type );
        }
        else
        {
            value.setValueType( value.defineValueType() );
        }
        
        return value;
    }

    public String getType()
    {
        return "value";
    }

}
