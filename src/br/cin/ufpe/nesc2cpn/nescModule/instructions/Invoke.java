package br.cin.ufpe.nesc2cpn.nescModule.instructions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * @author avld
 */
public class Invoke extends Instruction
{
    private String invokeType;
    private Function method;
    private List<Instruction> param;

    public Invoke()
    {
        param = new LinkedList<Instruction>();
        setType("invoke");
    }
    
    public String getInvokeType()
    {
        return invokeType;
    }

    public void setInvokeType(String invokeType)
    {
        this.invokeType = invokeType;
    }

    public Function getMethod()
    {
        return method;
    }

    public void setMethod(Function method)
    {
        this.method = method;
    }

    public List<Instruction> getParam()
    {
        return param;
    }

    public void setParam(List<Instruction> param)
    {
        this.param = param;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( getInvokeType() ).append( " " );

        if( getMethod().getInterfaceNick() != null )
        {
            builder.append( getMethod().getInterfaceNick() ).append( "." );
        }
        
        builder.append( getMethod().getFunctionName() ).append( "(" );

        Iterator<Instruction> it = getParam().iterator();
        while( it.hasNext() )
        {
            Instruction inst = it.next();

            if( inst == null )
            {
                continue ;
            }

            builder.append( " " );
            builder.append( inst.toString() );
            builder.append( " " );
            
            if( it.hasNext() )
            {
                builder.append( "," );
            }
        }

        builder.append( ")" );

        return builder.toString().trim();
    }
}