package br.cin.ufpe.nesc2cpn.nescModule.creator;

import br.cin.ufpe.nesc2cpn.nescModule.instructions.Instruction;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public class OperationCreator extends Creator
{
    public final static String[] OPERATION_LOGICO = new String[]{ "&&" , "||" , "!" };
    public final static String[] OPERATION_RELACIONAL = new String[]{ "==" , ">=" , "<=" , "!=" , ">" , "<" };
    public final static String[] OPERATION_MATH = new String[]{ "+" , "-" , "/" , "*" , "%" };
    public final static String[] OPERATION_BIT = new String[]{ "<<" , ">>" , "&" , "|" , "^" };

    private List<String> operationList;

    public OperationCreator()
    {
        operationList = new ArrayList<String>();
        operationList.addAll( Arrays.asList( OPERATION_LOGICO ) );
        operationList.addAll( Arrays.asList( OPERATION_RELACIONAL ) );
        operationList.addAll( Arrays.asList( OPERATION_MATH ) );
        operationList.addAll( Arrays.asList( OPERATION_BIT ) );
    }

    public boolean identify(String line)
    {
        //CreatorFactory factory = CreatorFactory.getInstance();

//        if( factory.get( "parentese" ).identify( line ) )
//        {
//            return false;
//        }
//        else if( factory.get( "cast" ).identify( line ) )
//        {
//            System.out.println( "[operation] false" );
//            return false;
//        }
//        else if( factory.get( "invoke" ).identify( line ) )
//        {
//            return false;
//        }

        OperationProcess process = new OperationProcess( line );
        return process.haveOperation();
    }

    public Instruction convertTo(String line)
    {
        OperationProcess process = new OperationProcess( line );

        Operation operation = new Operation();
        operation.setOperation( process.getOperation() );

        CreatorFactory creator = CreatorFactory.getInstance();
        operation.setLeft ( creator.convertToSimple( process.getLeft() ) );
        operation.setRight( creator.convertToSimple( process.getRigth() ) );

        return operation;
    }

    public String getType()
    {
        return "operation";
    }

}
