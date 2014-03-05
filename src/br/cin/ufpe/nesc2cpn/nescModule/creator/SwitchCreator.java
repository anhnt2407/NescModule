package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Case;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.ComposedInstruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Switch;
import java.util.List;

/**
 *
 * @author avld
 */
public class SwitchCreator extends ComposedCreator
{
    public final static String SWITCH = "switch";

    public SwitchCreator()
    {
        
    }


    public boolean identify(String line)
    {
        if( line.length() < SWITCH.length() )
        {
            return false;
        }

        return SWITCH.equals( line.substring( 0 , SWITCH.length() ) );
    }

    public Instruction convertTo(String line)
    {
        Switch switchInst = new Switch();

        switchInst.setValue( getCondition( line ) );
        switchInst.setInstructions( getCases( line ) );
        calculeProbability( switchInst );

        return switchInst;
    }

    public List<Instruction> getCases( String text )
    {
        ComposedInstruction parent = new ComposedInstruction();

        String cases[] = text.split(" case ");

        for( int i = 1 ; i < cases.length ; i++ )
        {
            String caseInstruction = cases[i];

            int defaultStartPosition = -1;
            String defaultInstruction = "";

            if( text.indexOf(" default ") >= 0 )
            {
                defaultStartPosition = caseInstruction.indexOf(" default ");
            }
            else if( text.indexOf(" default:") >= 0 )
            {
                defaultStartPosition = caseInstruction.indexOf(" default:");
            }

            if( defaultStartPosition >= 0 )
            {
                defaultInstruction = caseInstruction.substring( defaultStartPosition );
                caseInstruction = caseInstruction.substring( 0 , defaultStartPosition );
            }

            CreatorFactory.getInstance().addInstruction( parent , "case " + caseInstruction );

            if( defaultStartPosition >= 0 )
            {
                CreatorFactory.getInstance().addInstruction( parent , defaultInstruction.trim() );
            }
        }

        return parent.getInstructions();
    }

    public void calculeProbability(Switch switchInst)
    {
        double prob = 1 / switchInst.getInstructions().size();

        for( Instruction inst : switchInst.getInstructions() )
        {
            ((Case) inst).setProbability( prob );
        }
    }

    public String getType()
    {
        return "switch";
    }

}
