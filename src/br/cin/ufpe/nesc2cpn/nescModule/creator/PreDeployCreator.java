package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.PreDeploy;

/**
 *
 * @author avld
 */
public class PreDeployCreator extends Creator
{

    public boolean identify(String line)
    {
        if( line.trim().charAt(0) == '#' )
        {
            return true;
        }

        return false;
    }

    public Instruction convertTo(String line)
    {
        return new PreDeploy();
    }

    public String getType()
    {
        return "preDeploy";
    }

}
