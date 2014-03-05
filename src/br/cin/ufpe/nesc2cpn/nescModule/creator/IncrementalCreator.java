package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.ProjectDate;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Incremental;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;

/**
 *
 * @author avld
 */
public class IncrementalCreator extends Creator
{
    public IncrementalCreator()
    {
        // do nothing
    }

    @Override
    public boolean identify(String line)
    {
        return line.startsWith("++")
                || line.endsWith("++");
    }

    @Override
    public Instruction convertTo(String line)
    {
        boolean posfix = false;
        String name = "";

        if( line.startsWith( "++" ) )
        {
            posfix = false;
            name = line.substring( 2 );
        }
        else
        {
            posfix = true;
            name = line.substring( 0 , line.length() - 2 );
        }

        Incremental inc = new Incremental();
        inc.setPosfix( posfix );
        inc.setVariableName( name );

        String type = ProjectDate.getInstance().getVariableType( name );
        inc.setType( type );

        return inc;
    }

    @Override
    public String getType()
    {
        return "incremental";
    }
}
