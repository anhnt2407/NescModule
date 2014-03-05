package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Parentese;

/**
 *
 * @author avld
 */
public class ParenteseCreator extends Creator {

    public ParenteseCreator()
    {
        
    }

    @Override
    public boolean identify(String line)
    {
        if( line.charAt(0) == '('
                && line.charAt( line.length() - 1 ) == ')' )
        {
            if( line.length() - 1 == finishParentese( line ) )
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public Instruction convertTo(String line)
    {
        line = line.substring( 1 , line.length() - 1 );

        Parentese inst = new Parentese();
        inst.setInstruction( CreatorFactory.getInstance().convertToSimple( line ) );

        return inst;
    }

    @Override
    public String getType()
    {
        return "parentese";
    }
}
