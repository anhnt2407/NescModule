package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.ProjectDate;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Decremental;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class DecrementalCreator extends Creator
{
    public DecrementalCreator()
    {

    }

    @Override
    public boolean identify(String line)
    {
        return line.startsWith("--")
                || line.endsWith("--");
    }

    @Override
    public Instruction convertTo(String line)
    {
        boolean posfix = false;
        String name = "";

        if( line.startsWith( "--" ) )
        {
            posfix = false;
            name = line.substring( 2 );
        }
        else
        {
            posfix = true;
            name = line.substring( 0 , line.length() - 2 );
        }

        Decremental dec = new Decremental();
        dec.setPosfix( posfix );
        dec.setVariableName( name );

        String type = ProjectDate.getInstance().getVariableType( name );
        dec.setType( type );

        return dec;
    }

    @Override
    public String getType()
    {
        return "decremental";
    }
}
