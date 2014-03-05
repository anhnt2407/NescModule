package br.cin.ufpe.nesc2cpn.nescModule.instructions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author avld
 */
public class IfElse extends ComposedInstruction {
    private Instruction condition;
    private double probability;
    private Map<Long,IfElse> elses;

    public IfElse()
    {
        elses = new LinkedHashMap<Long, IfElse>();
        setType("IF_ELSE");
    }

    public IfElse(String text)
    {
        elses = new LinkedHashMap<Long, IfElse>();
        setText( text );
        setType("IF_ELSE");
    }

    public Instruction getCondition()
    {
        return condition;
    }

    public void setCondition(Instruction condition)
    {
        this.condition = condition;
    }

    public Map<Long, IfElse> getElses() {
        return elses;
    }

    public void setElses(Map<Long, IfElse> elses) {
        this.elses = elses;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    private void addElseIfNotExist()
    {
        boolean hasElse = false;
        
        for( IfElse ifElse : getElses().values() )
        {
            if( ifElse.getCondition() == null )
            {
                hasElse = true;
            }
        }

        if( hasElse )
        {
            return ;
        }

        IfElse elseInst = new IfElse();
        elseInst.setProbability( -1 );

        getElses().put( (long) getElses().size() , elseInst );
    }

    public void calculeProbability()
    {
        addElseIfNotExist();

        double total = 0;
        int eleWithoutProb = 0;

        if( getProbability() == -1 )
        {
            eleWithoutProb++;
        }
        else
        {
            total = getProbability();
        }

        for( IfElse inst : getElses().values() )
        {
            if( inst.getProbability() != -1 )
            {
                total += inst.getProbability();
            }
            else
            {
                eleWithoutProb++;
            }
        }

        if( eleWithoutProb == 0 ) //Case not exist element without probability
        {
            return ;              //so finalize this method
        }

        // ----------------------------- Set probility in instruction without this value
        double rest = 1 - total;                //calcule the rest probability
        double prob = rest / eleWithoutProb;    //division the rest with element without

        if( getProbability() == -1 )            //this element have a probability ?!
        {
            setProbability( prob );             //If not, set!
        }

        for( IfElse inst : getElses().values() )//Check other IF or ELSE
        {
            if( inst.getProbability() == -1 )
            {
                inst.setProbability( prob );
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        if( getCondition() != null )
        {
            builder.append( "if(" );
            builder.append( getCondition() );
            builder.append( ")" );
        }

        builder.append( "{\n" );

        for( Instruction instruction : getInstructions() )
        {
            builder.append("[").append( instruction.getType() ).append( "] ");
            builder.append( instruction.toString() );

            if( !(instruction instanceof ComposedInstruction) )
            {
                builder.append( ";" );
            }

            builder.append( "\n" );
        }

        builder.append( "}" );

        for( Instruction instruction : getElses().values() )
        {
            builder.append( "\nelse " );
            builder.append( instruction.toString() );
        }

        return builder.toString();
    }
}


// condicao: A == B && A != B || A >= B ... !A