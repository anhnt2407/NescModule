package br.cin.ufpe.nesc2cpn.nescModule.instructions;

/**
 *
 * @author avld
 */
public class Variable extends Assign {
    
    public Variable()
    {
        setType("variable");
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( getVariableType() );
        builder.append( " " );
        builder.append( getVariableName() );
        
        if( getValue() != null )
        {
            builder.append( " = " );
            builder.append( getValue() );
        }

        builder.append( ";" );

        return builder.toString();
    }
}
